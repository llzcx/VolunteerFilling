package com.social.demo.manager.security.authorize;

import com.social.demo.common.ResultCode;
import com.social.demo.manager.security.exception.CustomAccessDeniedException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 在此方法的decide之前已经执行了CustomSecurityMetadataSource（获取访问当前路径需要什么身份）类和DynamicSecurityFilter(调用decide)
 * @author 陈翔
 */
public class MyAccessDecisionManager implements AccessDecisionManager {
 
    /**
     * @param authentication 保存了当前登录用户的信息（已有哪些角色）
     * @param o
     * @param collection     需要哪些角色，和authentication对比判断
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //遍历collection(需要的角色)
        for (ConfigAttribute attribute : collection) {
            //已经存在的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                //如果存在需要的角色，直接return
                if (authority.getAuthority().equals(attribute.getAttribute())) {
                    return;
                }

                //杨世博----跳过角色权限验证
                return;
            }
        }
        //抛出异常无权访问
        throw new CustomAccessDeniedException(ResultCode.UNAUTHORIZED_ACCESS);
    }
 
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }
 
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}