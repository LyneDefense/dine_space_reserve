package com.heyuan.dine_space_reserve.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heyuan.dine_space_reserve.common.BusinessException;
import com.heyuan.dine_space_reserve.mapper.UserMapper;
import com.heyuan.dine_space_reserve.model.dto.user.*;
import com.heyuan.dine_space_reserve.model.pojo.AccessTokenDO;
import com.heyuan.dine_space_reserve.model.pojo.UserDO;
import com.heyuan.dine_space_reserve.service.TokenService;
import com.heyuan.dine_space_reserve.service.UserService;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Resource private UserMapper userMapper;

  @Resource private TokenService tokenService;

  public UserLoginResDto login(UserLoginReqDto user) {
    UserDO userDO =
        userMapper.selectOne(
            new LambdaQueryWrapper<UserDO>().eq(UserDO::getPhoneNumber, user.getPhoneNumber()));
    if (Objects.isNull(userDO)) {
      throw new BusinessException("用户不存在");
    }
    if (!this.matches(user.getPassword(), userDO.getPassword())) {
      throw new BusinessException("用户名或密码错误");
    }
    // 创建token
    AccessTokenDO accessTokenDO = tokenService.getAccessTokenByUser(userDO.getId());
    accessTokenDO = accessTokenDO == null ? tokenService.createAccessToken(userDO) : accessTokenDO;
    return UserLoginResDto.builder()
        .id(userDO.getId())
        .phoneNumber(userDO.getPhoneNumber())
        .accessToken(accessTokenDO.getAccessToken())
        .build();
  }

  public UserItemDto create(UserCreateDto user) {
    UserDO userDO =
        userMapper.selectOne(
            new LambdaQueryWrapper<UserDO>().eq(UserDO::getPhoneNumber, user.getPhoneNumber()));
    if (Objects.nonNull(userDO)) {
      throw new BusinessException("手机号已注册");
    }
    int i =
        userMapper.insert(
            UserDO.builder()
                .phoneNumber(user.getPhoneNumber())
                .password(passwordMd5(user.getPassword()))
                .build());
    if (i != 1) {
      throw new BusinessException("注册用户失败");
    }
    userDO =
        userMapper.selectOne(
            new LambdaQueryWrapper<UserDO>().eq(UserDO::getPhoneNumber, user.getPhoneNumber()));
    return UserItemDto.builder().id(userDO.getId()).phoneNumber(userDO.getPhoneNumber()).build();
  }

  @Override
  public void delete(String id) {
    tokenService.checkAuth();
    UserDO userDO = userMapper.selectById(id);
    if (userDO == null) {
      throw new BusinessException("用户不存在");
    }
    int i = userMapper.deleteById(id);
    if (i != 1) {
      throw new BusinessException("删除用户失败");
    }
  }

  @Override
  @Transactional
  public UserItemDto update(UserUpdateDto user) {
    tokenService.checkAuth();
    UserDO userDO = userMapper.selectById(user.getId());
    if (userDO == null) {
      throw new BusinessException("用户不存在");
    }
    int i =
        userMapper.updateById(
            UserDO.builder()
                .id(userDO.getId())
                .phoneNumber(user.getPhoneNumber())
                .password(passwordMd5(user.getPassword()))
                .build());
    if (i != 1) {
      throw new BusinessException("更新用户失败");
    }
    return UserItemDto.builder().id(userDO.getId()).phoneNumber(userDO.getPhoneNumber()).build();
  }

  @Override
  public void logout(String token) {
    // 删除访问令牌
    tokenService.removeAccessToken(token);
  }

  private String passwordMd5(String pwd) {
    return SaSecureUtil.md5(pwd);
  }

  private boolean matches(String rawPassword, String encodedPassword) {
    return passwordMd5(rawPassword).equals(encodedPassword);
  }
}
