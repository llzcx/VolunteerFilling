package com.social.demo.demo;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author 周威宇
 */
@Data
public class School {
    /**
     * 学校id
     */
    Long schoolId;
    /**
     * 学校名
     */
    String  schoolName;
    /**
     * 招生人数
     */
    Integer enrollmentNumber;
    /**
     * 学生录取列表
     */
    ArrayList<String> addressList;
    /**
     * 录取状态
     */
    Boolean admissionStatus;
}
