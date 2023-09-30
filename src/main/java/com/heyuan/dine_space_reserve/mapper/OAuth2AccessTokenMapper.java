package com.heyuan.dine_space_reserve.mapper;


import com.heyuan.dine_space_reserve.model.pojo.AccessTokenDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuth2AccessTokenMapper extends BaseMapperX<AccessTokenDO> {

  default AccessTokenDO selectByAccessToken(String accessToken) {
    return selectOne(AccessTokenDO::getAccessToken, accessToken);
  }

  default AccessTokenDO selectByUserId(String userId) {
    return selectOne(AccessTokenDO::getUserId, userId);
  }
}
