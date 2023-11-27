package com.social.demo.data.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 杨世博
 * @date 2023/11/20 15:38
 * @description 承接老师表格信息信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeacherBo {

    @ExcelProperty("工号")
    private String jobNumber;

    @ExcelProperty("姓名")
    private String username;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("政治面貌")
    private String politicsStatus;

    @ExcelProperty("民族")
    private String nation;

    @ExcelProperty("联系电话")
    private String phone;
}
