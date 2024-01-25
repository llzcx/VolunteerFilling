package com.social.demo.manager.security.config;

import cn.hutool.core.text.AntPathMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatternConfig {
    @Bean
    public AntPathMatcher myBean() {
        return new AntPathMatcher();
    }
}
