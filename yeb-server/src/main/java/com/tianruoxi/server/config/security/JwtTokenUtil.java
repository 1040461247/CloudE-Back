package com.tianruoxi.server.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：Tianruoxi
 * @date：2021/2/5 16:34
 */

@Component
public class JwtTokenUtil {

  private static final String CLAIM_KEY_USERNAME = "sub";
  private static final String CLAIM_KEY_CREATED = "created";
  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private Long expiration;

  // 根据用户信息生成token
  public String generateToken(UserDetails userDetails) {
    // 生成荷载
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
    claims.put(CLAIM_KEY_CREATED, new Date());

    return generateToken(claims);
  }

  // 从token中获取用户名
  public String getUsernameFromToken(String token) {
    String username;
    try {
      Claims claims = getClaimsFormToken(token);
      username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  // 验证token是否有效
  public boolean validateToken(String token, UserDetails userDetails) {
    String username = getUsernameFromToken(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  // 刷新token(只更改过期时间)
  public String refreshToken(String token) {
    Claims claims = getClaimsFormToken(token);
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }

  // 根据荷载生成Token
  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
            .setClaims(claims)
            .setExpiration(generateExpirationDate())
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
  }

  // 把秒转换为毫秒，用系统时间加上过期时间
  private Date generateExpirationDate() {
    return new Date(System.currentTimeMillis() + expiration * 1000);
  }

  // 解析token载荷
  private Claims getClaimsFormToken(String token) {
    Claims claims = null;
    try {
      claims = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return claims;
  }


  // 判断token是否失效(判断是否在当前时间之前)
  private boolean isTokenExpired(String token) {
    return getExpireDateFromToken(token).before(new Date());
  }

  // 获取token中的过期时间
  private Date getExpireDateFromToken(String token) {
    return getClaimsFormToken(token).getExpiration();
  }

  // 判断token是否可以被刷新（过期了可以被刷新）
  private boolean canRefresh(String token) {
    return !isTokenExpired(token);
  }

}



