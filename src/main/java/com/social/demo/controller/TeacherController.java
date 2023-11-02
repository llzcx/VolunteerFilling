package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.dto.UserDtoByTeacher;
import com.social.demo.data.vo.TeacherVo;
import com.social.demo.data.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;
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
     * 老师查看个人信息
     * @param request
     * @return 老师的个人信息
     */
    @GetMapping("/information")
    public ApiResp<UserVo> getInformation(HttpServletRequest request){
        UserVo user = userService.getInformation(request);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 老师修改个人信息
     * @param userDtoByStudent
     * @return 老师的个人信息
     */
    @PutMapping("/information")
    public ApiResp<UserVo> modifyInformation(@RequestBody UserDtoByStudent userDtoByStudent){
        UserVo user = userService.modifyInformation(userDtoByStudent);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 老师获取学生个人信息
     * @param
     * @return 学生个人信息
     */
    @GetMapping("/student/{id}")
    public ApiResp<UserVo> getStudent(@PathVariable(value = "id")Long id){
        UserVo user = userService.getStudent(id);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 老师修改学生个人信息
     * @param userDtoByTeacher 老师修改的个人信息
     * @return 学生个人信息
     */
    @PutMapping("/student")
    public ApiResp<UserVo> modifyStudent(@RequestBody UserDtoByTeacher userDtoByTeacher){
        UserVo user = userService.modifyStudent(userDtoByTeacher);
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
