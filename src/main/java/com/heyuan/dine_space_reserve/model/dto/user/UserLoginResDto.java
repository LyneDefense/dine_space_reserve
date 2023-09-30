package com.heyuan.dine_space_reserve.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResDto {

  private String id;

  @ApiModelProperty(value = "手机号")
  private String phoneNumber;

  private String accessToken;
}
