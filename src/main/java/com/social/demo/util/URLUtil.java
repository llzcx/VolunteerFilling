package com.social.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * @author 杨世博
 * @date 2024/4/7 21:00
 * @description URLUtil
 */
@Component
@Slf4j
public class URLUtil implements InitializingBean {

    private String ip;

    @Value("${server.address}")
    public void setAddress(String address) {
        ip = address;
    }

    private String port;
    @Value("${server.port}")
    public void setPort(String port) {
        this.port = port;
    }

    private String URL_PRF;

    public String getUrl(String url) throws UnknownHostException {
        return URL_PRF + url;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (ip != null && ip.equals("0.0.0.0")){
            URL_PRF =  "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port;
        }else {
            URL_PRF =  "http://" + ip + ":" + port;
        }
        log.info("URL_PRF load success :{}.", URL_PRF);
    }
}

