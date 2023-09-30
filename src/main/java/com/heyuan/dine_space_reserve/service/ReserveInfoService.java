package com.heyuan.dine_space_reserve.service;

import com.heyuan.dine_space_reserve.model.dto.ReserveInfoDto;

import java.util.List;

public interface ReserveInfoService {

  ReserveInfoDto create(ReserveInfoDto reserveInfoDto);

  void update(ReserveInfoDto reserveInfoDto);

  void delete(String id);

  ReserveInfoDto get(String id);

  List<ReserveInfoDto> getList();
}
