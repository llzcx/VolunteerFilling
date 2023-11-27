package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.UserDtoByTeacher;
import com.social.demo.data.vo.TeacherVo;
import com.social.demo.data.vo.StudentVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 老师接口
 *
 * @author 杨世博
 */
@RestController
@RequestMapping("/teacher")
@Validated
public class TeacherController {
    @Autowired
    IUserService userService;

    /**
     * ps 暂时无法从Token中获取用户id
     *
     * 老师查看个人信息
     * @param request
     * @return 老师的个人信息
     */
    @GetMapping("/information")
    public ApiResp<StudentVo> getInformation(HttpServletRequest request){
        StudentVo user = userService.getInformationOfTeacher(request);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 老师修改电话号码
     * @param phone 电话号码
     * @return 老师的个人信息
     */
    @PutMapping("/phone")
    public ApiResp<TeacherVo> modifyPhone(HttpServletRequest request,
                                                @RequestBody String phone){
        TeacherVo teacher = userService.modifyPhone(request, phone);
        return ApiResp.judge(teacher != null, teacher, ResultCode.DATABASE_DATA_EXCEPTION);
    }

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

    /**
     * 老师修改学生个人信息
     * @param userDtoByTeacher 修改的学生信息
     * @return 学生个人信息
     */
    @PutMapping("/student")
    public ApiResp<StudentVo> modifyStudent(@RequestBody UserDtoByTeacher userDtoByTeacher){
        StudentVo user = userService.modifyStudent(userDtoByTeacher);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 重置学生密码
     * @param id 学生id
     * @return 是否操作成功
     */
    @PatchMapping("/student/{id}")
    public ApiResp<Boolean> reset(@PathVariable(value = "id")Long id){
        Boolean reset = userService.reset(id);
        return ApiResp.success(reset);
    }
}
