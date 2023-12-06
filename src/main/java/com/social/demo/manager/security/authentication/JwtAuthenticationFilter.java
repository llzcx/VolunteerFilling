package com.social.demo.manager.security.authentication;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.RequestConstant;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.manager.security.exception.CustomAccessDeniedException;
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
        final String token = request.getHeader(jwtUtil.getTokenHeader());
        log.debug("token：\nToken:{}",token);
        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 没有jwt相当于匿名访问，若有一些接口是需要权限的，则不能访问这些接口
        if (StrUtil.isBlankOrUndefined(token)) {
            log.debug("匿名请求，放行");
            chain.doFilter(request, response);
            return;
        }
        log.debug("非匿名请求");
        //检查token
        final DecodedJWT tokenDecode = jwtUtil.getClaimsByToken(token);
        if (tokenDecode == null) {
            //token无效直接返回token错误
            throw new CustomAuthenticationException(ResultCode.TOKEN_EXCEPTION);
        }
        final boolean accessTokenExpired = jwtUtil.isTokenExpired(tokenDecode);

        if(accessTokenExpired){
            //token过期
            throw new SystemException(ResultCode.TOKEN_TIME_OUT);
        }else{
            //accessToken没有过期则检查是否加入黑名单
            if (jwtUtil.checkBlacklist(token)){
                throw new CustomAuthenticationException(ResultCode.TOKEN_EXCEPTION);
            }
        }
        // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken
                        (jwtUtil.getSubject(request), null,
                                userDetailService.getUserAuthority(jwtUtil.getSubject(request)));
        SecurityContextHolder.getContext().setAuthentication(user);
        chain.doFilter(request, response);
    }
}

