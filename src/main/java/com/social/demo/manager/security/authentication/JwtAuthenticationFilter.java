package com.social.demo.manager.security.authentication;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.RedisConstant;
import com.social.demo.dao.mapper.UserMapper;
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

        log.debug("token：\nAccessToken:{}",accessToken);
        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 没有jwt相当于匿名访问，若有一些接口是需要权限的，则不能访问这些接口
        if (StrUtil.isBlankOrUndefined(accessToken)) {
            log.debug("匿名请求，放行");
            chain.doFilter(request, response);
            return;
        }
        log.debug("非匿名请求");
        //检查accessToken
        final DecodedJWT accessTokenDecode = jwtUtil.getClaimsByToken(accessToken);
        if (accessTokenDecode == null) {
            //token无效直接返回token错误
            throw new CustomAuthenticationException(ResultCode.TOKEN_EXCEPTION);
        }
        final boolean accessTokenExpired = jwtUtil.isTokenExpired(accessTokenDecode);
        log.debug("过期情况：accessTokenExpired:{}",accessTokenExpired);

        //从redis中获取refreshToken
        //先从accessToken中获取用户信息
        String userNumber = jwtUtil.getSubject(request);
        String hKey = RedisConstant.SOCIAL+RedisConstant.JWT_TOKEN+ userNumber;
        String refreshToken = redisUtil.get(hKey);
        if (refreshToken == null){
            throw new CustomAuthenticationException(ResultCode.TOKEN_EXCEPTION);
        }
        final DecodedJWT refreshTokenDecode = jwtUtil.getClaimsByToken(refreshToken);
        final boolean refreshTokenExpired = jwtUtil.isTokenExpired(refreshTokenDecode);

        if(!refreshTokenExpired){
            //refreshToken没有过期
            final String userNumberFromRefreshToken = refreshTokenDecode.getSubject();

            if(accessTokenExpired){
                //accessToken过期，将刷新的token返回给前端
                response.setHeader(jwtUtil.getAccessTokenHeader(), jwtUtil.generateAccessToken(userNumberFromRefreshToken));
            }
            // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
            UsernamePasswordAuthenticationToken user =
                    new UsernamePasswordAuthenticationToken
                            (userNumberFromRefreshToken, null,
                                    userDetailService.getUserAuthority(userNumberFromRefreshToken));
            SecurityContextHolder.getContext().setAuthentication(user);
            chain.doFilter(request, response);
        }else{
            //refreshToken过期直接抛异常
            throw new CustomAuthenticationException(ResultCode.TOKEN_TIME_OUT);
        }
    }
}

