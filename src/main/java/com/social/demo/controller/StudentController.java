package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.vo.UserVo;
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
     * 学生获取个人信息
     * @param request
     * @return 学生个人信息
     */
    @GetMapping("/information")
    public ApiResp<UserVo> getInformation(HttpServletRequest request){
        UserVo user = userService.getInformation(request);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 学生修改个人信息
     * @param userDtoByStudent 用户上传的个人信息
     * @return 学生个人信息
     */
    @PutMapping("/information")
    public ApiResp<UserVo> modifyInformation(@RequestBody UserDtoByStudent userDtoByStudent){
        UserVo user = userService.modifyInformation(userDtoByStudent);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }
}
