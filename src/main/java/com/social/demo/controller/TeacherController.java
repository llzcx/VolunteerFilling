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
     * 老师查看个人信息
     * @param request
     * @return 老师的个人信息
     */
    @GetMapping("/information")
    public ApiResp<TeacherVo> getInformation(HttpServletRequest request){
        TeacherVo user = userService.getInformationOfTeacher(request);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 老师修改电话号码
     * @param phone 电话号码
     * @return
     */
    @PutMapping("/phone")
    public ApiResp<String> modifyPhone(HttpServletRequest request,
                                                @RequestBody String phone){
        Boolean teacher = userService.modifyPhone(request, phone);
        return ApiResp.judge(teacher, "修改成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }
}
