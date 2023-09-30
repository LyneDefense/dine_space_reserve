package com.heyuan.dine_space_reserve.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heyuan.dine_space_reserve.common.BusinessException;
import com.heyuan.dine_space_reserve.util.HttpClientUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WechatHttpClient {
  public static final String appSecret = "6c083a24395740f3bf4415a98ac2dd4f";

  public static final String appId = "wxea728795cf5694c2";

  public String getWechatAccessToken() {
    // 创建客户端
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    String url =
        String.format(
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
            appId, appSecret);
    // 创建Get请求
    HttpGet httpGet = new HttpGet(url);
    // 响应模型
    CloseableHttpResponse response = null;
    try {
      // 执行Get请求
      response = httpClient.execute(httpGet);
      // 从响应模型中获取响应实体
      HttpEntity responseEntity = response.getEntity();
      // 编码
      final String s = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readTree(s).get("access_token").asText();
    } catch (Exception e) {
      throw new BusinessException("wechat access token get failed");
    } finally {
      try {
        // 释放资源
        if (Objects.nonNull(httpClient)) {
          httpClient.close();
        }
        if (Objects.nonNull(response)) {
          response.close();
        }
      } catch (IOException e) {
        throw new BusinessException("wechat access token get failed");
      }
    }
  }

  public String getAccessToken() {
    String url =
        String.format(
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
            appId, appSecret);
    // 发送请求
    String result = HttpClientUtils.doGet(url);
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = null;
    try {
      jsonNode = objectMapper.readTree(result);
    } catch (JsonProcessingException e) {
      throw new BusinessException("json parse failed");
    }
    log.info("access_token------>" + jsonNode.get("access_token").toString());
    return jsonNode.get("access_token").toString();
  }

  public byte[] getQRCode(String path, double width) {
    // 获取AccessToken
    String accessToken = getWechatAccessToken();
    String url =
        String.format("https://api.weixin.qq.com/wxa/getwxacode?access_token=%s", accessToken);
    // 组装参数
    Map<String, Object> paraMap = new HashMap<>();
    // 二维码携带参数 不超过32位 参数类型必须是字符串
    paraMap.put("path", path);
    // 二维码的宽度
    paraMap.put("width", width);
    // 执行post 获取数据流
    return HttpClientUtils.doImgPost(url, paraMap);
  }
}
