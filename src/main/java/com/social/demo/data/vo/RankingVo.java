package com.social.demo.data.vo;


import lombok.Data;

import java.util.List;

@Data
public class RankingVo {
    /**
     * 学生id
     */
    private Long userId;
    /**
     *学生成绩
     */
    private Integer score;
    /**
     * 学生各科
     */
    private String grades;
    /**
     * 学生排名
     */
    private Integer ranking;
}
