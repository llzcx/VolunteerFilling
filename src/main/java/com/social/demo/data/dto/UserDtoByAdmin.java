package com.social.demo.data.dto;

import com.social.demo.data.bo.ConsigneeBo;
import com.social.demo.entity.Consignee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员修改学生信息
 *
 * @author 杨世博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoByAdmin {
    /**
     * userId
     */
    private Long userId;
    /**
     * 修改后的学生信息
     */
    private StudentDtoByAdmin studentDto;
    /**
     * 班级
     */
    private Long classId;
    /**
     * 收件信息
     */
    private ConsigneeBo consignee;
}
