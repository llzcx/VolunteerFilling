package com.social.demo.constant;

import com.social.demo.util.OsUtils;
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

    private Boolean isLinux;

    public Boolean isLinux() {
        return isLinux;
    }

    public String getFileSplit() {
        return isLinux ? OsUtils.LINUX_FILE_SPLIT : OsUtils.WINDOWS_FILE_SPLIT;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String env = null;
        if ("dev".equals(active)) {
            env = "开发";
        } else if ("prod".equals(active)) {
            env = "生产";
        } else {
            throw new RuntimeException("请在参数spring.profiles.active中说明是生成环境还是开发环境");
        }
        if (OsUtils.isWindows() || OsUtils.isLinux()) {
            isLinux = OsUtils.isLinux();
        } else {
            throw new RuntimeException("未知操作系统");
        }

        log.info("当前处于{}环境当中，系统为：{}。", env, isLinux() ? "linux" : "windows");
    }
}
