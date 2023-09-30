package com.heyuan.dine_space_reserve.service;

import com.heyuan.dine_space_reserve.model.dto.user.*;

public interface UserService {

  UserLoginResDto login(UserLoginReqDto user);

  UserItemDto create(UserCreateDto user);

  void delete(String id);

  UserItemDto update(UserUpdateDto user);

    void logout(String token);
}
