package com.social.demo.data.vo;

import java.io.Serializable;

/**
 * @author 杨世博
 */
public class TeacherVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 工号
     */
    private String userNumber;
    /**
     * 姓名
     */
    private String username;
    /**
     * 家庭地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 班级id
     */
    private Boolean classId;
}
