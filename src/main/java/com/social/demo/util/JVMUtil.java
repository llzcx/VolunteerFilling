package com.social.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

@Slf4j
public class JVMUtil {
    public static void printJVM(){
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> jvmArgs = runtimeMxBean.getInputArguments();

        log.info("JVM参数:");
        for (String arg : jvmArgs) {
            System.out.println(arg);
        }
    }
}
