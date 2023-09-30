package com.heyuan.dine_space_reserve.config;

import com.heyuan.dine_space_reserve.common.BusinessException;
import com.heyuan.dine_space_reserve.common.Resp;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一处理异常
 *
 * @author ouzhenxiong
 * @since 2021/7/6
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(BusinessException.class)
  public Resp<Void> error(BusinessException ex) {
    return Resp.error(ex.getCode(), ex.getMessage());
  }
}
