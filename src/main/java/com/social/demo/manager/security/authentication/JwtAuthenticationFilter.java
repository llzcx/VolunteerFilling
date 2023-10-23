package com.social.demo.manager.security.authentication;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.RedisConstant;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.manager.security.exception.CustomAuthenticationException;
import com.social.demo.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author 陈翔
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String accessToken = request.getHeader(jwtUtil.getAccessTokenHeader());
        final String refreshToken = request.getHeader(jwtUtil.getRefreshTokenHeader());
        log.debug("token：\nAccessToken:{}\nRefreshToken:{}",accessToken,refreshToken);
        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 没有jwt相当于匿名访问，若有一些接口是需要权限的，则不能访问这些接口
        if (StrUtil.isBlankOrUndefined(accessToken) && StrUtil.isBlankOrUndefined(refreshToken)) {
            log.debug("匿名请求，放行");
            chain.doFilter(request, response);
            return;
        }
        log.debug("非匿名请求");
        //先检查accessToken
        final DecodedJWT accessTokenDecode = jwtUtil.getClaimsByToken(accessToken);
        final DecodedJWT refreshTokenDecode = jwtUtil.getClaimsByToken(refreshToken);
        if (accessTokenDecode == null || refreshTokenDecode == null) {
            //只要其中一个token无效直接返回token错误
            throw new CustomAuthenticationException(ResultCode.TOKEN_EXCEPTION);
        }
        final boolean accessTokenExpired = jwtUtil.isTokenExpired(accessTokenDecode);
        final boolean refreshTokenExpired = jwtUtil.isTokenExpired(refreshTokenDecode);
        log.debug("过期情况：accessTokenExpired:{}，refreshTokenExpired:{}",accessTokenExpired,refreshTokenExpired);
        if(!refreshTokenExpired){
            //refreshToken没有过期
            final String usernameFromRefreshToken = refreshTokenDecode.getSubject();
            final TokenPair tokenPair = redisUtil.getObject(RedisConstant.SOCIAL + RedisConstant.JWT_TOKEN + usernameFromRefreshToken, TokenPair.class);
            if(!refreshToken.equals(tokenPair.getRefreshToken())){
                throw new SystemException(ResultCode.TOKEN_EXCEPTION);
            }
            if(accessTokenExpired){
                //accessToken过期，将刷新的token返回给前端
                final TokenPair token = jwtUtil.createTokenAndSaveToKy(usernameFromRefreshToken);
                response.setHeader(jwtUtil.getAccessTokenHeader(), token.getAccessToken());
                response.setHeader(jwtUtil.getRefreshTokenHeader(), token.getRefreshToken());
            }else{
                //accessToken没有过期则检查是否合理
                if(!accessToken.equals(tokenPair.getAccessToken())){
                    throw new SystemException(ResultCode.TOKEN_EXCEPTION);
                }
            }
            // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
            UsernamePasswordAuthenticationToken user =
                    new UsernamePasswordAuthenticationToken
                            (usernameFromRefreshToken, null,
                                    userDetailService.getUserAuthority(usernameFromRefreshToken));
            SecurityContextHolder.getContext().setAuthentication(user);
            chain.doFilter(request, response);
        }else{
            //refreshToken过期直接抛异常
            throw new CustomAuthenticationException(ResultCode.TOKEN_TIME_OUT);
        }
    }
}

