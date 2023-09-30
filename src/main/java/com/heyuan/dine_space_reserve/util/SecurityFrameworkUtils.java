package com.heyuan.dine_space_reserve.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 安全服务工具类
 *
 * @author xkool源码
 */
public class SecurityFrameworkUtils {
  private SecurityFrameworkUtils() {}

  /**
   * 从请求中，获得认证 Token
   *
   * @return 认证 Token
   */
  public static String obtainAuthorization() {
    // 获取当前请求对象
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    // 获取名为"Auth"的请求头内容
    String authHeader = request.getHeader("Auth");
    return authHeader;
  }
}
