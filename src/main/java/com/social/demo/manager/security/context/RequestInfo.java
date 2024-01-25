package com.social.demo.manager.security.context;

import com.social.demo.constant.IdentityEnum;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestInfo {
    private Long userId;
    private IdentityEnum identity;

    public RequestInfo(Long userId, IdentityEnum identity) {
        this.userId = userId;
        this.identity = identity;
    }
}
