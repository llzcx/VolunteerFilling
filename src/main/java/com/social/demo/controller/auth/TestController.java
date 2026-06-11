package com.social.demo.controller.auth;

import com.social.demo.common.ApiResp;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.manager.security.identity.Identity;
import com.social.demo.manager.security.identity.Release;
import com.social.demo.manager.ratelimit.api.APIRateLimiter;
import com.social.demo.manager.security.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 用于测试RBAC权限(验权功能开启需要联系后端)
 * @author 陈翔
 */
@RestController
public class TestController {
    @GetMapping("/test/super")
    @Identity(IdentityEnum.SUPER)
    public String super1() {
        return "hello super"+ SecurityContext.get();
    }
    @GetMapping("/test/teacher")
    @Identity(IdentityEnum.SUPER)
    public String teacher() {
        return "hello teacher"+ SecurityContext.get();
    }
    @GetMapping("/test/student")
    @Identity(IdentityEnum.SUPER)
    public String student() {
        return "hello student"+ SecurityContext.get();
    }

    @GetMapping("/test/other/{name}")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<String> other(@PathVariable String name){
       return ApiResp.success("1");
    }


    @GetMapping("/test/limit")
    @APIRateLimiter(qps = 0.1,timeout = 100)
    @Release
    public ApiResp<String> testLimit(){
        return ApiResp.success(String.valueOf(new Random().nextDouble()));
    }
}