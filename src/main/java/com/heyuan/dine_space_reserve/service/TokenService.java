package com.heyuan.dine_space_reserve.service;

import com.heyuan.dine_space_reserve.model.pojo.AccessTokenDO;
import com.heyuan.dine_space_reserve.model.pojo.UserDO;
import org.springframework.transaction.annotation.Transactional;

public interface TokenService {
    @Transactional
    AccessTokenDO createAccessToken(UserDO userDO);

    AccessTokenDO getAccessTokenByToken(String accessToken);

    AccessTokenDO getAccessTokenByUser(String userId);

    AccessTokenDO checkAccessToken(String accessToken);

    AccessTokenDO removeAccessToken(String accessToken);

    void checkAuth();
}
