package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.vo.StudentVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 学生接口
 *
 * @author 杨世博
 */
@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

    @Autowired
    IUserService userService;

    /**
     * ps 暂时无法从Token中获取用户id
     *
     * 学生获取个人信息
     * @param request
     * @return 学生个人信息
     */
    @GetMapping("/information")
    public ApiResp<StudentVo> getInformation(HttpServletRequest request){
        StudentVo student = userService.getInformationOfStudent(request);
        return ApiResp.judge(student != null, student, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 学生修改个人信息（收件信息）
     * @param request
     * @param userDtoByStudent 学生提交的修改信息
     * @return 学生个人信息
     */
    @PutMapping("/information")
    public ApiResp<StudentVo> modifyInformation(HttpServletRequest request,
                                                @RequestBody UserDtoByStudent userDtoByStudent){
        StudentVo student = userService.modifyInformation(request, userDtoByStudent);
        return ApiResp.judge(student != null, student, ResultCode.DATABASE_DATA_EXCEPTION);
    }
}
