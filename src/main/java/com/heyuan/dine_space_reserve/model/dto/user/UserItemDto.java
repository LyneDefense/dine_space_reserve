package com.heyuan.dine_space_reserve.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserItemDto {

    private String id;

    @NotNull(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;
}
