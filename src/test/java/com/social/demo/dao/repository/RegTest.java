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
        String password = "02102    8@Ysb";
        if (password.matches(RegConstant.PASSWORD)) {
            System.out.println("格式正确");
        } else {
            System.out.println("格式不正确");
        }
    }

    /**
     * 学号
     * @throws Exception
     */
    @Test
    void studentNumber() throws Exception{
        String username = "2021401534";
        if (username.matches(RegConstant.USER_NUMBER)) {
            System.out.println("格式正确");
        } else {
            System.out.println("格式不正确");
        }
    }

    /**
     * 学号
     * @throws Exception
     */
    @Test
    void IdCard() throws Exception{
        String IdCard = "43092320021028329X";
        if (IdCard.matches(RegConstant.IDCard)) {
            System.out.println("格式正确");
        } else {
            System.out.println("格式不正确");
        }
    }
}
