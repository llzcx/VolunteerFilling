package com.social.demo.manager.security.config;


import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈翔
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class PathConfig implements InitializingBean {
    private String[] releasablePath;

    public static String[] RELEASABLE_PATH;


    @Override
    public void afterPropertiesSet() throws Exception {
        RELEASABLE_PATH = releasablePath;
    }
}
