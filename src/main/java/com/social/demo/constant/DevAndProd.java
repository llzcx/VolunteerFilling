package com.social.demo.constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DevAndProd implements InitializingBean {


    private String active;

    @Value("${spring.profiles.active}")
    private void set(String active) {
        this.active = active;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String env = null;
        if("dev".equals(active)){
            env = "开发";
        }else if("prod".equals(active)){
            env = "生产";
        }else {
            throw new RuntimeException("请在参数spring.profiles.active中说明是生成环境还是开发环境");
        }
        log.info("当前处于{}环境当中。",env);
    }
}
