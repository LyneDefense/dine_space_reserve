package com.heyuan.dine_space_reserve.common;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

  private int code = 400;
  private String message;
  private String internalMessage;
  private List<Object> args;

  public BusinessException(int code, String message, String internalMessage) {
    super();
    this.code = code;
    this.message = message;
    this.internalMessage = internalMessage;
  }

  public BusinessException(String internalMessage) {
    super();
    this.message = internalMessage;
    this.internalMessage = internalMessage;
  }

  public BusinessException(String internalMessage, Throwable e) {
    super(internalMessage, e);
    this.internalMessage = internalMessage;
  }

  public BusinessException(BusinessException e) {
    super();
    this.code = e.getCode();
    this.message = e.getMessage();
    this.internalMessage = e.getInternalMessage();
    this.args = e.getArgs();
  }

  public BusinessException(BusinessException e, List<Object> args) {
    super();
    this.code = e.getCode();
    this.message = e.getMessage();
    this.internalMessage = e.getInternalMessage();
    this.args = args;
  }

  public BusinessException(BusinessException commonException, Object... args) {
    super();
    this.code = commonException.getCode();
    this.message = commonException.getMessage();
    this.internalMessage = String.format(commonException.getInternalMessage(), args);
  }
}
