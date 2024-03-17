package com.social.demo.manager.ratelimit.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
public class APIRateLimiterAspect {
    private static final ConcurrentMap<String, com.google.common.util.concurrent.RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    public boolean check(Method method){
        // 通过 AnnotationUtils.findAnnotation 获取 RateLimiter 注解
        APIRateLimiter APIRateLimiter = method.getAnnotation(APIRateLimiter.class);
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
                return false;
            }
        }
        return true;
    }
 
}