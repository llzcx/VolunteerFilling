package com.social.demo.manager.ratelimit.api;

import com.social.demo.manager.ratelimit.api.APIRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Aspect
@Component
public class APIRateLimiterAspect {
    private static final ConcurrentMap<String, com.google.common.util.concurrent.RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();
 
 
    @Pointcut("@annotation(com.social.demo.controller.*)")
    public void rateLimit() {
 
    }
 
    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 通过 AnnotationUtils.findAnnotation 获取 RateLimiter 注解
        APIRateLimiter APIRateLimiter = AnnotationUtils.findAnnotation(method, APIRateLimiter.class);
        if (APIRateLimiter != null && APIRateLimiter.qps() > APIRateLimiter.NOT_LIMITED) {
            double qps = APIRateLimiter.qps();
            if (RATE_LIMITER_CACHE.get(method.getName()) == null) {
                // 初始化 QPS
                RATE_LIMITER_CACHE.put(method.getName(), com.google.common.util.concurrent.RateLimiter.create(qps));
            }
            log.debug("【{}】的QPS设置为: {}", method.getName(), RATE_LIMITER_CACHE.get(method.getName()).getRate());
            // 尝试获取令牌
            if (RATE_LIMITER_CACHE.get(method.getName()) != null && !RATE_LIMITER_CACHE.get(method.getName()).tryAcquire(APIRateLimiter.timeout(), APIRateLimiter.timeUnit())) {
               log.warn("!!!!!!!!!被限流啦");
                return "达到峰值了 被限流了~稍后重试~~~";
              //  throw new RuntimeException("请求频繁，请稍后再试~");
            }
        }
        return point.proceed();
    }
 
}