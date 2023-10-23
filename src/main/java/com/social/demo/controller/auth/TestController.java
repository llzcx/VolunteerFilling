package com.social.demo.controller.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于测试RBAC权限
 * @author 陈翔
 */
@RestController
public class TestController {
    @GetMapping("/admin/hello")
    public String admin() {
        return "hello admin";
    }
    @GetMapping("/user/hello")
    public String user() {
        return "hello user";
    }
    @GetMapping("/guest/hello")
    public String guest() {
        return "hello guest";
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/test/1")
    public String test() {
        return "hello";
    }
}