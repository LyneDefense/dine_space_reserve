package com.heyuan.dine_space_reserve.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

  @ApiModelProperty(value = "id")
  @NotNull(message = "用户id不能为空")
  private String id;

  @ApiModelProperty(value = "手机号")
  private String phoneNumber;

  @ApiModelProperty(value = "密码")
  private String password;
}
