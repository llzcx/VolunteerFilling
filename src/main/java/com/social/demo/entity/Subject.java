package com.social.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.social.demo.constant.RegConstant;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author 周威宇
 */
@Data
public class Subject {
    /**
     * 科目id
     */
    @TableId(value = "subject_id", type = IdType.AUTO)
    private Long subjectId;
    /**
     * 科目名称
     */
    private String subjectName;
}
