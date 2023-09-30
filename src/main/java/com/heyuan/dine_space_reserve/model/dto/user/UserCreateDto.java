package com.heyuan.dine_space_reserve.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    @NotNull(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    @NotNull(message = "密码不能数为空")
    @ApiModelProperty(value = "密码")
    private String password;
}
