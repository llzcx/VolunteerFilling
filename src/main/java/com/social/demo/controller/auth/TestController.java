package com.social.demo.controller.auth;

import com.social.demo.manager.security.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于测试RBAC权限(验权功能开启需要联系后端)
 * @author 陈翔
 */
@RestController
public class TestController {
    @GetMapping("/test/super")
    public String super1() {
        return "hello super"+ SecurityContext.get();
    }
    @GetMapping("/test/teacher")
    public String teacher() {
        return "hello teacher"+ SecurityContext.get();
    }
    @GetMapping("/test/student")
    public String student() {
        return "hello student"+ SecurityContext.get();
    }

    @GetMapping("/test/other")
    public String other() {
        return "hello student"+ SecurityContext.get();
    }
}