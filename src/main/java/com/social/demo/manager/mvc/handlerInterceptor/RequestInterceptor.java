package com.social.demo.manager.mvc.handlerInterceptor;

import cn.hutool.core.text.AntPathMatcher;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.social.demo.manager.security.identity.Release;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.SysApiMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.manager.ratelimit.api.APIRateLimiterAspect;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.manager.security.config.PathConfig;
import com.social.demo.manager.security.context.RequestInfo;
import com.social.demo.manager.security.context.SecurityContext;

import com.social.demo.manager.security.identity.IdentityAuthentication;
import com.social.demo.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.Objects;


/**
 * 拦截器类
 *
 * @author 陈翔
 */
@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SysApiMapper apiMapper;

    @Autowired
    AntPathMatcher antPathMatcher;


    @Autowired
    IdentityAuthentication identityAuthentication;

    @Autowired
    APIRateLimiterAspect apiRateLimiterAspect;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            //options请求.放行
            return true;
        }

        //本次请求的路径
        String requestURL = request.getRequestURI();
        log.info("requestURL:{}", requestURL);


        if (!(handler instanceof HandlerMethod)) {
            //不是映射到方法直接通过
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //限流
        if(!apiRateLimiterAspect.check(method)){
            throw new SystemException(ResultCode.REQ_LIMIT);
        }


        //放行路径
        for (String path : PathConfig.RELEASABLE_PATH) {
            if (antPathMatcher.match(path, requestURL)) {
                return true;
            }
        }
        Release annotation = method.getAnnotation(Release.class);
        if (annotation != null) return true;
        //匿名请求 不允许进入
        final String token = request.getHeader(jwtUtil.getTokenHeader());
        if (token == null) {
            throw new SystemException(ResultCode.PROHIBIT_ANONYMOUS_REQUESTS);
        }
        //非匿名请求 处理
        //解码token
        final DecodedJWT tokenDecode = jwtUtil.getClaimsByToken(token);
        if (tokenDecode == null) {
            //token无效直接返回token解码失败
            throw new SystemException(ResultCode.TOKEN_DECODE_ERROR);
        }
        Long userId = jwtUtil.getUserId(tokenDecode);
        TokenPair tokenForRedis = jwtUtil.getTokenForRedis(userId);
        if (!Objects.equals(tokenForRedis.getAccessToken(), token)) {
            throw new SystemException(ResultCode.OLD_TOKEN);
        }
        Integer identityCode = userMapper.selectIdentityByUserId(userId);
        RequestInfo requestInfo = new RequestInfo(userId, IdentityEnum.searchByCode(identityCode));
        log.info("用户为：{}，本次访问的身份是：{}", userId,requestInfo.getIdentity().getMessage());
        //保存到当前线程上下文
        SecurityContext.set(requestInfo);
        if (PropertiesConstant.AUTHORIZATION_CLOSE) {
            return true;
        }
        //权限验证
        return identityAuthentication.check(requestURL, requestInfo.getIdentity(), method);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContext.clear();
    }
}
