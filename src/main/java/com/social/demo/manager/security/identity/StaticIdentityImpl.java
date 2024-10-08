package com.social.demo.manager.security.identity;

import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.IdentityEnum;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 静态RBAC
 */
@Component
public class StaticIdentityImpl implements IdentityAuthentication {
    @Override
    public boolean check(String requestURL, IdentityEnum identity, Method method) {
        Identity annotation = method.getAnnotation(Identity.class);
        if (identity != null) {
            //需要身份认证的接口
            IdentityEnum[] value = annotation.value();
            for (IdentityEnum ide : value)
                if (ide.equals(identity)) return true;
            return true;
        } else {
            throw new SystemException(ResultCode.ENCODING_ANOMALY);
        }
    }
}
