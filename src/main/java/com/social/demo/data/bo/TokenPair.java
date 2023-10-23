package com.social.demo.data.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 陈翔
 */
@Data
public class TokenPair implements Serializable {
    private String accessToken;
    private String refreshToken;

    public TokenPair() {
    }

    public TokenPair(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
