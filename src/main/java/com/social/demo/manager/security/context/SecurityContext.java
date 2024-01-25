package com.social.demo.manager.security.context;

import lombok.Data;

public class SecurityContext {
    private static final ThreadLocal<RequestInfo> context = new ThreadLocal<>();

    public static RequestInfo get() {
        return context.get();
    }

    public static void set(RequestInfo requestInfo) {
        context.set(requestInfo);
    }

}
