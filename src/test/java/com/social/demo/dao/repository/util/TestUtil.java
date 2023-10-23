package com.social.demo.dao.repository.util;

import com.social.demo.common.JsonUtil;

public class TestUtil {
    /**
     * 打印测试结果
     * @param label 描述
     * @param object 对象
     */
    public static <T> void printInfo(String label,T object) {
        System.out.println(label+":"+ JsonUtil.object2StringSlice(object));
    }
}
