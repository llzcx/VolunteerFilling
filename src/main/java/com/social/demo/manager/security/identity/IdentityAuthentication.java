package com.social.demo.manager.security.identity;

import com.social.demo.constant.IdentityEnum;

import java.lang.reflect.Method;


public interface IdentityAuthentication {
    boolean check(String requestURL, IdentityEnum identity, Method method);
}
