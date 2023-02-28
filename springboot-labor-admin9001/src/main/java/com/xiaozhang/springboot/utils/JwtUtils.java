package com.xiaozhang.springboot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: xiaozhangtx
 * @ClassName: JwtUtils
 * @Description: TODO JWT工具类
 * @date: 2023/2/27 19:51
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "xiaozhang.jwt")
public class JwtUtils {

    private long expire;
    private String secret;
    private String header;

    /**
     * 生成JWT
     *
     * @param username 用户名
     * @return JWT令牌
     */
    public String generateToken(String username) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT令牌
     * @return 解析出的信息
     */
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证JWT是否过期
     *
     * @param claims 时间
     * @return 是否过期
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}
