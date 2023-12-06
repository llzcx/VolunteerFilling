package com.social.demo.manager.security.authorize;

import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import java.io.IOException;

/**
 * 动态权限过滤器，用于实现基于路径的动态权限过滤
 * @author 陈翔
 */
@Slf4j
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    CustomSecurityMetadataSource customSecurityMetadataSource;

    @Resource
    public void setAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        if(true){
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }
        log.info("------------------进入权限过滤器------------------");
        /**
         * 仿照OncePerRequestFilter，解决Filter执行两次的问题
         * 执行两次原因：SecurityConfig中，@Bean和addFilter相当于向容器注入了两次
         * 解决办法：1是去掉@Bean，但Filter中若有引用注入容器的其它资源，则会报错
         *         2就是request中保存一个Attribute来判断该请求是否已执行过
         */
        String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName();
        boolean hasAlreadyFilteredAttribute = request.getAttribute(alreadyFilteredAttributeName) != null;

        if (hasAlreadyFilteredAttribute) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }
        request.setAttribute(alreadyFilteredAttributeName, Boolean.TRUE);
        //OPTIONS请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            log.info("OPTIONS请求直接放行");
            return;
        }
        //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }


    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return customSecurityMetadataSource;
    }


    protected String getAlreadyFilteredAttributeName() {
        return this.getClass().getName() + ".FILTERED";
    }
}

