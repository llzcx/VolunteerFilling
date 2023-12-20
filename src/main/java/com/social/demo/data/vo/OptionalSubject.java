package com.social.demo.data.vo;

import lombok.Data;

import java.util.List;

 /**
  * @author  周威宇
  */
 @Data
public class OptionalSubject {
    /**
     * 科目数量
     */
    private int  subjectNumber;
    /**
     * 任选科目范围
     */
    private List<String> optionalSubjectScope;
}
