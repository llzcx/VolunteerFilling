package com.social.demo.data.dto;

import com.social.demo.constant.RegConstant;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨世博
 * @date 2023/12/4 15:54
 * @description TeacherDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {
    /**
     * 工号
     */
    @Pattern(regexp= RegConstant.USER_NUMBER)
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
}
