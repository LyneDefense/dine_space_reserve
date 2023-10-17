package com.heyuan.dine_space_reserve.service.impl;

import com.heyuan.dine_space_reserve.common.BusinessException;
import com.heyuan.dine_space_reserve.mapper.ReserveInfoMapper;
import com.heyuan.dine_space_reserve.mapper.query.LambdaQueryWrapperX;
import com.heyuan.dine_space_reserve.model.dto.ReserveInfoDto;
import com.heyuan.dine_space_reserve.model.pojo.ReserveInfoDO;
import com.heyuan.dine_space_reserve.service.ReserveInfoService;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;

import com.heyuan.dine_space_reserve.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class ReserveInfoServiceImpl implements ReserveInfoService {

  @Resource ReserveInfoMapper reserveInfoMapper;

  @Resource private TokenService tokenService;

  public ReserveInfoDto create(ReserveInfoDto reserveInfoDto) {
    tokenService.checkAuth();
    ReserveInfoDO reserveInfoDO =
        ReserveInfoDO.builder()
            .booker(reserveInfoDto.getBooker())
            .bookerPhoneNumber(reserveInfoDto.getBookerPhoneNumber())
            .reserveDate(reserveInfoDto.getReserveDate())
            .timeSlots(ReserveInfoDto.toString(reserveInfoDto.getTimeSlots()))
            .tags(reserveInfoDto.getTags())
            .roomName(reserveInfoDto.getRoomName())
            .pricePerPerson(reserveInfoDto.getPricePerPerson())
            .dinerCount(reserveInfoDto.getDinerCount())
            .waitstaffCount(reserveInfoDto.getWaitstaffCount())
            .comment(reserveInfoDto.getComment())
            .extraDetails(reserveInfoDto.getExtraDetails())
            .build();
    int i = reserveInfoMapper.insert(reserveInfoDO);
    if (i != 1) {
      throw new BusinessException("预定房间失败");
    }
    return ReserveInfoDto.of(reserveInfoDO);
  }

  public void update(ReserveInfoDto reserveInfoDto) {
    tokenService.checkAuth();
    ReserveInfoDO reserveInfoDO = reserveInfoMapper.selectById(reserveInfoDto.getId());
    if (reserveInfoDO == null) {
      throw new BusinessException("预定房间不存在");
    }
    int i =
        reserveInfoMapper.updateById(
            ReserveInfoDO.builder()
                .id(reserveInfoDto.getId())
                .booker(reserveInfoDto.getBooker())
                .bookerPhoneNumber(reserveInfoDto.getBookerPhoneNumber())
                .reserveDate(reserveInfoDto.getReserveDate())
                .timeSlots(ReserveInfoDto.toString(reserveInfoDto.getTimeSlots()))
                .tags(reserveInfoDto.getTags())
                .roomName(reserveInfoDto.getRoomName())
                .pricePerPerson(reserveInfoDto.getPricePerPerson())
                .dinerCount(reserveInfoDto.getDinerCount())
                .waitstaffCount(reserveInfoDto.getWaitstaffCount())
                .comment(reserveInfoDto.getComment())
                .extraDetails(reserveInfoDto.getExtraDetails())
                .build());
    if (i != 1) {
      throw new BusinessException("更新预定房间信息失败");
    }
  }

  public void delete(String id) {
    tokenService.checkAuth();
    ReserveInfoDO reserveInfoDO = reserveInfoMapper.selectById(id);
    if (reserveInfoDO == null) {
      throw new BusinessException("预定房间不存在");
    }
    int i = reserveInfoMapper.deleteById(id);
    if (i != 1) {
      throw new BusinessException("取消预定房间失败");
    }
  }

  @Override
  public ReserveInfoDto get(String id) {
    ReserveInfoDO reserveInfoDO = reserveInfoMapper.selectById(id);
    if (reserveInfoDO == null) {
      throw new BusinessException("预定房间不存在");
    }
    return ReserveInfoDto.builder()
        .id(reserveInfoDO.getId())
        .booker(reserveInfoDO.getBooker())
        .bookerPhoneNumber(reserveInfoDO.getBookerPhoneNumber())
        .reserveDate(reserveInfoDO.getReserveDate())
        .timeSlots(ReserveInfoDto.toDouble(reserveInfoDO.getTimeSlots()))
        .tags(reserveInfoDO.getTags())
        .roomName(reserveInfoDO.getRoomName())
        .pricePerPerson(reserveInfoDO.getPricePerPerson())
        .dinerCount(reserveInfoDO.getDinerCount())
        .waitstaffCount(reserveInfoDO.getWaitstaffCount())
        .comment(reserveInfoDO.getComment())
        .extraDetails(reserveInfoDO.getExtraDetails())
        .build();
  }

  public List<ReserveInfoDto> getList() {
    tokenService.checkAuth();
    // 获取当前日期
    LocalDate today = LocalDate.now();
    // 计算30天后的日期
    LocalDate thirtyDaysLater = today.plusDays(30);
    LambdaQueryWrapperX<ReserveInfoDO> wrapperX = new LambdaQueryWrapperX<>();
    wrapperX
        .ge(ReserveInfoDO::getReserveDate, today)
        .le(ReserveInfoDO::getReserveDate, thirtyDaysLater);
    List<ReserveInfoDO> reserveInfoDOList =
        reserveInfoMapper.selectList(wrapperX.orderByAsc(ReserveInfoDO::getReserveDate));
    return ReserveInfoDto.ofList(reserveInfoDOList);
  }
}
