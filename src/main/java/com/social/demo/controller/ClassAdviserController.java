package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IClassAdviserService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 班主任接口
 *
 * @author 杨世博
 * @date 2023/12/7 21:18
 * @description ClassAdviserController
 */
@RestController
@RequestMapping("/adviser")
@Validated
public class ClassAdviserController {
    @Autowired
    IClassAdviserService classAdviserService;

    @Autowired
    IUserService userService;

    /**
     * 老师获取学生个人信息
     * @param number 学生学号
     * @return 学生个人信息
     */
    @GetMapping("/student")
    public ApiResp<StudentVo> getStudent(@RequestParam("number")Long number){
        StudentVo user = userService.getStudent(number);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }
}
