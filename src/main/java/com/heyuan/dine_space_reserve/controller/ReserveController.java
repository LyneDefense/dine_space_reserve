package com.heyuan.dine_space_reserve.controller;

import com.heyuan.dine_space_reserve.common.Resp;
import com.heyuan.dine_space_reserve.model.dto.ReserveInfoDto;
import com.heyuan.dine_space_reserve.service.ReserveInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@Api(value = "ReserveController", tags = "房间预定管理")
public class ReserveController {

  @Resource ReserveInfoService reserveInfoService;

  @ApiOperation(value = "预订房间")
  @PostMapping("/create")
  Resp<ReserveInfoDto> create(@RequestBody @Valid ReserveInfoDto reserveInfoDto) {
    return Resp.data(reserveInfoService.create(reserveInfoDto));
  }

  @ApiOperation(value = "修改预订信息")
  @PostMapping("/update")
  Resp<Void> update(@RequestBody @Valid ReserveInfoDto reserveInfoDto) {
    reserveInfoService.update(reserveInfoDto);
    return Resp.ok();
  }

  @ApiOperation(value = "取消预订")
  @DeleteMapping("/delete/{id}")
  Resp<Void> delete(@PathVariable String id) {
    reserveInfoService.delete(id);
    return Resp.ok();
  }

  @ApiOperation(value = "获取房间信息")
  @GetMapping("/get/{id}")
  ReserveInfoDto get(@PathVariable String id) {
    return reserveInfoService.get(id);
  }

  @ApiOperation(value = "获取最近200条的房间预定信息")
  @GetMapping("/list")
  List<ReserveInfoDto> getList() {
    return reserveInfoService.getList();
  }
}
