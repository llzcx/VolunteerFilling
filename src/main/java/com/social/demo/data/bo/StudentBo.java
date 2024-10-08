package com.social.demo.data.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.social.demo.constant.RegConstant;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 杨世博
 * @date 2023/11/20 14:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentBo {
    /**
     * 学号
     */
    private Long userId;
    /**
     * 姓名
     */
    private String username;
    /**
     * 学号
     */
    private String userNumber;
}
