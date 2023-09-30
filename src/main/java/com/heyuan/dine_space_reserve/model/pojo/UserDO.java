package com.heyuan.dine_space_reserve.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "public.user", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {

  @TableId(type = IdType.ASSIGN_UUID)
  private String id;

  @ApiModelProperty(value = "手机号")
  private String phoneNumber;

  @ApiModelProperty(value = "密码")
  private String password;
}
