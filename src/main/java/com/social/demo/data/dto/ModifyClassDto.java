package com.social.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2024/3/2 16:31
 * @description ModifyClassDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyClassDto {
    /**
     * 修改的学生
     */
    private Long[] userId;
    /**
     * 修改到的班级id
     */
    private Long classId;
}
