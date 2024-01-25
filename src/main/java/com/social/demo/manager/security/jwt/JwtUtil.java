package com.social.demo.manager.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.RedisConstant;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈翔
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil implements InitializingBean {


    public static String USER_NUMBER = "USER_NUMBER";
    public static Map<String, Object> header = new HashMap<>();
    public static Algorithm algorithm;
    static {
        header.put("type","Jwt");
        header.put("alg","HS256");
    }

    @Autowired
    RedisUtil redisUtil;

    //accessToken头
    private String tokenHeader;
    @Value("${jwt.expire.accessToken}")
    private Long accessTokenExpire;
    @Value("${jwt.expire.refreshToken}")
    private Long refreshTokenExpire;
    private String secret;

    @Override
    public void afterPropertiesSet() throws Exception {
        algorithm = Algorithm.HMAC256(secret);
    }
    /**
     * 生成AccessTokenJWT
     * @param userId
     * @return
     */
    public String generateAccessToken(Long userId) {
        Date nowDate = new Date();
        //设置token过期时间
        Date expireDate = new Date(nowDate.getTime() + 1000 * accessTokenExpire);
        //秘钥是密码则省略
        return JWT.create()
                .withHeader(header)
                .withClaim("userId", userId)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * 生成refreshTokenJWT
     * @param userId
     * @return
     */
    public String generateRefreshToken(Long userId) {
        Date nowDate = new Date();
        //设置token过期时间
        Date expireDate = new Date(nowDate.getTime() + 1000 * refreshTokenExpire);
        //秘钥是密码则省略
        return JWT.create()
                .withHeader(header)
                .withClaim("userId", userId)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    // 解析JWT
    public DecodedJWT getClaimsByToken(String token) {
        if (token==null || "".equals(token.trim())){
            throw new SystemException(ResultCode.TOKEN_DECODE_ERROR);
        }
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    // 判断JWT是否过期
    public boolean isTokenExpired(DecodedJWT decodedJWT) {
        final Date currentTime = new Date();
        final Date expirationTime = decodedJWT.getExpiresAt();
        // 比较当前时间与过期时间
        if (expirationTime != null && expirationTime.before(currentTime)) {
            // Token 已过期
            return true;
        } else {
            // Token 未过期
            return false;
        }
    }

    public Date getExpirationData(String token){
        return getClaimsByToken(token).getExpiresAt();
    }

    /**
     * 根据 刷新令牌 获取 访问令牌
     *
     * @param refreshToken
     */
    public String getAccessTokenByRefresh(String refreshToken) {
        Object value = redisUtil.get(refreshToken);
        return value == null ? null : String.valueOf(value);
    }


    /**
     * 创建token并存入redis
     * @param userId
     * @return
     */
    public TokenPair createTokenAndSaveToKy(Long userId){
        final String accessToken = generateAccessToken(userId);
        final String refreshToken = generateRefreshToken(userId);
        Date refreshException = getClaimsByToken(refreshToken).getExpiresAt();
        Long time = (refreshException.getTime() - System.currentTimeMillis()) / 1000 + 100;
        redisUtil.set(RedisConstant.WISH+RedisConstant.JWT_TOKEN + refreshToken, accessToken, time);
        return new TokenPair(accessToken, refreshToken);
    }

    /**
     * 获取userId()
     * @param request
     * @return
     */
    public Long getUserId(HttpServletRequest request){
        return getClaimsByToken(request.getHeader(getTokenHeader())).getClaim("userId").asLong();
    }
    public Long getUserId(DecodedJWT decodedJWT){
        return decodedJWT.getClaim("userId").asLong();
    }

    /**
     * 从Redis中获取refreshToken
     * @param userNumber
     * @return
     */
    public TokenPair getTokenForRedis(String userNumber){
        TokenPair tokenPair = new TokenPair();
        tokenPair.setAccessToken(redisUtil.get(RedisConstant.WISH+RedisConstant.ACCESS_TOKEN+ userNumber));
        tokenPair.setRefreshToken(redisUtil.get(RedisConstant.WISH+RedisConstant.REFRESH_TOKEN+ userNumber));
        return tokenPair;
    }

    /**
     * 删除Redis中的refreshToken
     * @param refreshToken
     * @return
     */
    public Boolean delTokenForRedis(String refreshToken){
        return redisUtil.del(RedisConstant.WISH+RedisConstant.JWT_TOKEN+ refreshToken);
    }


    /**
     * 添加至黑名单
     *
     * @param token
     * @param expireTime
     */
    public void addBlacklist(String token, Date expireTime) {
        Long expireTimeLong = (expireTime.getTime() - System.currentTimeMillis()) / 1000 + 100;
        redisUtil.set(getBlacklistPrefix(token), "1", expireTimeLong);
    }

    /**
     * 校验是否存在黑名单
     *
     * @param token
     * @return true 存在 false不存在
     */
    public Boolean checkBlacklist(String token) {
        String exists = redisUtil.get(getBlacklistPrefix(token));
        return exists != null;
    }

    /**
     * 获取黑名单前缀
     *
     * @param token
     * @return
     */
    public String getBlacklistPrefix(String token) {
        return RedisConstant.TOKEN_BLACKLIST_PREFIX + token;
    }
}
