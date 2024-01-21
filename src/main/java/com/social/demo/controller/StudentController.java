package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.vo.AppealVo;
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

    @Autowired
    IAppealService appealService;

    /**
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
     * @return 是否修改成功
     */
    @PutMapping("/information")
    public ApiResp<Boolean> modifyInformation(HttpServletRequest request,
                                                @RequestBody UserDtoByStudent userDtoByStudent){
        Boolean b = userService.modifyInformation(request, userDtoByStudent);
        return ApiResp.judge(b, "修改成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 学生修改密码
     * @param request
     * @param password 密码
     * @return
     */
    @PutMapping("/password")
    public ApiResp<String> modifyPassword(HttpServletRequest request,
                                          @RequestBody String password){
        Boolean b = userService.modifyPassword(request, password);
        return ApiResp.judge(b, "修改成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 学生获取申诉
     * @param request
     * @param state 状态 0-待处理 1-已处理 2-已取消
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/appeal")
    public ApiResp<IPage<AppealVo>> getAppeal(HttpServletRequest request,
                                              @RequestParam(value = "state", required = false)Integer state,
                                              @RequestParam("current")Integer current,
                                              @RequestParam("size")Integer size){
        IPage<AppealVo> appeals = appealService.getAppealsToStudent(request, state, current, size);
        return ApiResp.success(appeals);
    }

    /**
     * 学生申诉
     * @param request
     * @param appeal 申述内容
     * @return
     */
    @PostMapping("/appeal")
    public ApiResp<String> submitAppeal(HttpServletRequest request,
                                        @RequestBody String appeal){
        appealService.submitAppeal(request, appeal);
        return ApiResp.success("上传成功");
    }

    /**
     * 撤销申诉
     * @param request
     * @param appealId 申诉id
     * @return
     */
    @PutMapping("/appeal")
    public ApiResp<String> quashAppeal(HttpServletRequest request,
                                       @RequestBody Long appealId){
        Boolean aBoolean = appealService.quashAppeal(request, appealId);
        return ApiResp.judge(aBoolean, "撤销成功", ResultCode.USER_NOT_MATCH_DATA);
    }
}
