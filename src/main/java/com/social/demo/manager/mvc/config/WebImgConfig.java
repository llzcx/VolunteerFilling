package com.social.demo.manager.mvc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebImgConfig implements WebMvcConfigurer, InitializingBean {

    @Value("${file-picture.address.path}")
    private String path;

    /**
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + path);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("path is {}",path);
    }
}