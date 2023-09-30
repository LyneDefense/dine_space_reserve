package com.heyuan.dine_space_reserve.controller;

import com.heyuan.dine_space_reserve.common.Resp;
import com.heyuan.dine_space_reserve.model.dto.user.*;
import com.heyuan.dine_space_reserve.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = "用户管理")
public class UserController {

  @Resource UserService userService;

  @ApiOperation(value = "用户登录")
  @PostMapping("/login")
  public Resp<UserLoginResDto> login(@RequestBody @Valid UserLoginReqDto user) {
    return Resp.data(userService.login(user));
  }

  @ApiOperation(value = "创建用户")
  @PostMapping("/create")
  public Resp<UserItemDto> create(@RequestBody @Valid UserCreateDto user) {
    return Resp.data(userService.create(user));
  }

  @ApiOperation(value = "删除用户")
  @DeleteMapping("/delete/{id}")
  public Resp<Void> delete(@PathVariable String id) {
    userService.delete(id);
    return Resp.ok();
  }

  @ApiOperation(value = "更新用户")
  @PostMapping("/update")
  public Resp<UserItemDto> update(@RequestBody @Valid UserUpdateDto user) {
    return Resp.data(userService.update(user));
  }
}
