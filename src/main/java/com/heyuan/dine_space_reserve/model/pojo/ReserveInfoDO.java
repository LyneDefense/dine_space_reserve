package com.heyuan.dine_space_reserve.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import lombok.*;

@TableName(value = "public.reserve_info", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveInfoDO {

  @TableId(type = IdType.ASSIGN_UUID)
  private String id;

  @ApiModelProperty(value = "预定人")
  private String booker;

  @ApiModelProperty(value = "预定手机号")
  private String bookerPhoneNumber;

  @ApiModelProperty(value = "预定日期")
  private Date reserveDate;

  @ApiModelProperty(value = "时间段")
  @TableField(typeHandler = JacksonTypeHandler.class)
  private List<String> timeSlots;

  @ApiModelProperty(value = "房间标签")
  @TableField(typeHandler = JacksonTypeHandler.class)
  private List<String> tags;

  @ApiModelProperty(value = "房间名")
  private String roomName;

  @ApiModelProperty(value = "用餐标准")
  private Double pricePerPerson;

  @ApiModelProperty(value = "用餐人数")
  private Integer dinerCount;

  @ApiModelProperty(value = "服务员人数")
  private Integer waitstaffCount;

  @ApiModelProperty(value = "备注")
  private String comment;

  @ApiModelProperty(value = "附加信息")
  @TableField(typeHandler = JacksonTypeHandler.class)
  private List<String> extraDetails;
}
