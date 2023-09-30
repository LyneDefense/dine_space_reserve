package com.heyuan.dine_space_reserve.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.*;

/**
 * OAuth2 访问令牌 DO
 *
 * <p>如下字段，暂时未使用，暂时不支持： user_name、authentication（用户信息）
 *
 * @author xkool
 */
@TableName(value = "public.access_token", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDO {

  /** 编号，数据库递增 */
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;

  /** 访问令牌 */
  private String accessToken;

  /** 用户编号 */
  private String userId;
}
