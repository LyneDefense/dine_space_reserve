package com.heyuan.dine_space_reserve.util;

import cn.hutool.json.JSONObject;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.Map;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

@Slf4j
public class HttpClientUtils {
  private static RequestConfig requestConfig = null;
  private static CloseableHttpClient httpClient = null;

  static {
    ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
    LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
    Registry<ConnectionSocketFactory> registry =
        RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", plainsf)
            .register("https", sslsf)
            .build();
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
    // 最大连接数
    cm.setMaxTotal(30);
    // 每个路由基础的连接
    cm.setDefaultMaxPerRoute(10);
    // 请求重试处理
    // 如果已经重试了5次，就放弃
    // 如果服务器丢掉了连接，那么就重试
    // 不要重试SSL握手异常
    // 超时
    // 目标服务器不可达
    // ssl握手异常
    // 如果请求是幂等的，就再次尝试
    HttpRequestRetryHandler httpRequestRetryHandler =
        (exception, executionCount, context) -> {
          if (executionCount >= 5) { // 如果已经重试了5次，就放弃
            return false;
          }
          if (exception instanceof NoHttpResponseException) { // 如果服务器丢掉了连接，那么就重试
            return true;
          }
          if (exception instanceof SSLHandshakeException) { // 不要重试SSL握手异常
            return false;
          }
          if (exception instanceof InterruptedIOException) { // 超时
            return false;
          }
          if (exception instanceof UnknownHostException) { // 目标服务器不可达
            return false;
          }
          if (exception instanceof SSLException) { // ssl握手异常
            return false;
          }
          HttpClientContext clientContext = HttpClientContext.adapt(context);
          HttpRequest request = clientContext.getRequest();
          // 如果请求是幂等的，就再次尝试
          return !(request instanceof HttpEntityEnclosingRequest);
        };
    requestConfig =
        RequestConfig.custom()
            .setConnectionRequestTimeout(5000)
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .build();
    httpClient =
        HttpClients.custom()
            .setConnectionManager(cm)
            .setRetryHandler(httpRequestRetryHandler)
            .build();
  }

  /**
   * get请求
   *
   * @param url 访问ur
   * @return
   */
  public static String doGet(String url) {
    String result = null;
    CloseableHttpResponse response = null;
    HttpGet httpget = new HttpGet(url);
    try {
      String resultEnc = "UTF-8";
      httpget.setConfig(requestConfig);
      response = httpClient.execute(httpget, HttpClientContext.create());
      result = EntityUtils.toString(response.getEntity(), resultEnc);
    } catch (Exception e) {
      return result;
    } finally {
      if (null != response) {
        try {
          response.close();
        } catch (IOException e) {
          log.error("get请求失败");
        }
      }
      httpget.abort();
    }
    return result;
  }

  /**
   * 获取数据流
   *
   * @param url
   * @param paraMap
   * @return
   */
  public static byte[] doImgPost(String url, Map<String, Object> paraMap) {
    byte[] result = null;
    HttpPost httpPost = new HttpPost(url);
    httpPost.addHeader("Content-Type", "application/json");
    try {
      // 设置请求的参数
      JSONObject postData = new JSONObject();
      postData.putAll(paraMap);
      httpPost.setEntity(new StringEntity(postData.toString(), "UTF-8"));
      HttpClient httpClient = HttpClientBuilder.create().build();
      HttpResponse response = httpClient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toByteArray(entity);
    } catch (Exception e) {
      log.error("get请求失败");
    } finally {
      httpPost.releaseConnection();
    }
    return result;
  }
}
