package com.heyuan.dine_space_reserve.service.impl;

import cn.hutool.core.util.IdUtil;
import com.heyuan.dine_space_reserve.common.BusinessException;
import com.heyuan.dine_space_reserve.mapper.OAuth2AccessTokenMapper;
import com.heyuan.dine_space_reserve.model.pojo.AccessTokenDO;
import com.heyuan.dine_space_reserve.model.pojo.UserDO;
import com.heyuan.dine_space_reserve.service.TokenService;
import com.heyuan.dine_space_reserve.util.SecurityFrameworkUtils;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OAuth2.0 Token Service 实现类
 *
 * @author xkool源码
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

  @Resource private OAuth2AccessTokenMapper oauth2AccessTokenMapper;

  @Override
  @Transactional
  public AccessTokenDO createAccessToken(UserDO userDO) {
    AccessTokenDO accessTokenDO =
        AccessTokenDO.builder().accessToken(generateAccessToken()).userId(userDO.getId()).build();
    oauth2AccessTokenMapper.insert(accessTokenDO);
    return accessTokenDO;
  }

  @Override
  public AccessTokenDO getAccessTokenByToken(String accessToken) {
    AccessTokenDO accessTokenDO = oauth2AccessTokenMapper.selectByAccessToken(accessToken);
    return accessTokenDO;
  }

  @Override
  public AccessTokenDO getAccessTokenByUser(String userId) {
    AccessTokenDO accessTokenDO = oauth2AccessTokenMapper.selectByUserId(userId);
    return accessTokenDO;
  }

  @Override
  public AccessTokenDO checkAccessToken(String accessToken) {
    AccessTokenDO accessTokenDO = getAccessTokenByToken(accessToken);
    if (accessTokenDO == null) {
      throw new BusinessException("当前用户token不存在");
    }
    return accessTokenDO;
  }

  @Override
  public AccessTokenDO removeAccessToken(String accessToken) {
    // 删除访问令牌
    AccessTokenDO accessTokenDO = oauth2AccessTokenMapper.selectByAccessToken(accessToken);
    if (accessTokenDO == null) {
      return null;
    }
    oauth2AccessTokenMapper.deleteById(accessTokenDO.getId());
    return accessTokenDO;
  }

  @Override
  public void checkAuth() {
    String token = SecurityFrameworkUtils.obtainAuthorization();
    checkAccessToken(token);
  }

  private static String generateAccessToken() {
    return IdUtil.fastSimpleUUID();
  }
}
