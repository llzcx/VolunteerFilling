package com.social.demo.dao.repository;

import com.social.demo.constant.RegConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 正则测试
 */
@SpringBootTest
@Slf4j
public class RegTest {

    /**
     * 用户名
     * @throws Exception
     */
    @Test
    void username() throws Exception{
        String username = "test_user01";
        if (username.matches(RegConstant.USERNAME)) {
            System.out.println("格式正确");
        } else {
            System.out.println("格式不正确");
        }
    }

    /**
     * 密码
     * @throws Exception
     */
    @Test
    void password() throws Exception{
        String password = "Password12@";
        if (password.matches(RegConstant.PASSWORD)) {
            System.out.println("格式正确");
        } else {
            System.out.println("格式不正确");
        }
    }

}
