package com.social.demo.data.vo;

import com.social.demo.entity.Major;
import lombok.Data;

import java.util.List;

@Data
public class StudentMajorVo{
    /**
     * 学院
     */
    private String college;
    /**
     * 专业集合
     */
    private List<MajorVo1> majors;
}
