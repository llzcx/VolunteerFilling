package com.social.demo.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
     * 性别
     */
    private String sex;
    /**
     * 政治面貌
     */
    private String politicsStatus;
    /**
     * 民族
     */
    private String nation;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 班级名称
     * 当其为空时，表示为普通老师
     */
    private String className;
}
