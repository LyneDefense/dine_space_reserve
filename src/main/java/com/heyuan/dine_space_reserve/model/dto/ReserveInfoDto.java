package com.heyuan.dine_space_reserve.model.dto;

import com.heyuan.dine_space_reserve.model.pojo.ReserveInfoDO;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveInfoDto {

  private String id;

  @NotNull(message = "预定人不能为空")
  @ApiModelProperty(value = "预定人")
  private String booker;

  @NotNull(message = "预定手机号不能为空")
  @ApiModelProperty(value = "预定手机号")
  private String bookerPhoneNumber;

  @NotNull(message = "预定日期不能为空")
  @ApiModelProperty(value = "预定日期")
  private Date reserveDate;

  @NotNull(message = "预定时间段不能为空")
  @ApiModelProperty(value = "时间段")
  private List<Integer> timeSlots;

  @ApiModelProperty(value = "房间标签")
  private List<String> tags;

  @NotNull(message = "预定房间名不能为空")
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
  private List<String> extraDetails;

  public static ReserveInfoDto of(ReserveInfoDO reserveInfoDO) {
    return ReserveInfoDto.builder()
        .id(reserveInfoDO.getId())
        .booker(reserveInfoDO.getBooker())
        .bookerPhoneNumber(reserveInfoDO.getBookerPhoneNumber())
        .reserveDate(reserveInfoDO.getReserveDate())
        .timeSlots(toInt(reserveInfoDO.getTimeSlots()))
        .tags(reserveInfoDO.getTags())
        .roomName(reserveInfoDO.getRoomName())
        .pricePerPerson(reserveInfoDO.getPricePerPerson())
        .dinerCount(reserveInfoDO.getDinerCount())
        .waitstaffCount(reserveInfoDO.getWaitstaffCount())
        .comment(reserveInfoDO.getComment())
        .extraDetails(reserveInfoDO.getExtraDetails())
        .build();
  }

  public static List<ReserveInfoDto> ofList(List<ReserveInfoDO> reserveInfoList) {
    return reserveInfoList.stream().map(ReserveInfoDto::of).collect(Collectors.toList());
  }

  public static List<Integer> toInt(List<String> timeSlots) {
    return timeSlots.stream().map(Integer::parseInt).collect(Collectors.toList());
  }

  public static List<String> toString(List<Integer> timeSlots) {
    return timeSlots.stream().map(String::valueOf).collect(Collectors.toList());
  }
}
