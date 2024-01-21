package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.dao.repository.IClassAdviserService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.IdentityDto;
import com.social.demo.data.dto.UserDtoByTeacher;
import com.social.demo.data.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    IAppraisalService appraisalService;

    @Autowired
    IAppealService appealService;

    /**
     * 班级成员列表
     * @param userNumber 学号
     * @param username 姓名
     * @param role 班级职称
     * @param rank 是否根据综测排名排序 -1-倒叙 0-不排序 1-排序
     * @param current 当前页数
     * @param size 每页大小
     * @return 班级成员列表
     */
    @GetMapping("/students")
    public ApiResp<IPage<ClassUserVo>> getStudents(HttpServletRequest request,
                                                     @RequestParam(value = "userNumber", required = false)String userNumber,
                                                     @RequestParam(value = "username", required = false)String username,
                                                     @RequestParam(value = "role", required = false)String role,
                                                     @RequestParam(value = "rank")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size){
        IPage<ClassUserVo> classMemberVoIPage = classAdviserService.getStudents(request, userNumber, username, role, rank, current, size);
        return ApiResp.success(classMemberVoIPage);
    }

    /**
     * 班主任获取学生个人信息
     * @param number 学生学号
     * @return 学生个人信息
     */
    @GetMapping("/student")
    public ApiResp<StudentVo> getStudent(@RequestParam("number")String number){
        StudentVo user = userService.getStudent(number);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 班主任修改学生个人信息
     * @param userDtoByTeacher 修改的学生信息
     * @return 学生个人信息
     */
    @PutMapping("/student")
    public ApiResp<String> modifyStudent(@RequestBody UserDtoByTeacher userDtoByTeacher){
        Boolean b = userService.modifyStudent(userDtoByTeacher);
        return ApiResp.judge(b, "修改成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 重置学生密码
     * @param userNumbers 学号
     * @return
     */
    @PutMapping("/reset")
    public ApiResp<String> reset(@RequestBody String[] userNumbers){
        userService.reset(userNumbers);
        return ApiResp.success("操作成功");
    }

    /**
     * 获取分页获取学生综测信息
     * @param name 学生姓名
     * @param userNumber 学号
     * @param month 月份
     * @param rank 是否根据综测成绩排序 0不排序 -1从小到大 1从大到小
     * @param current 当前页码
     * @param size 每页大小
     * @return
     */
    @GetMapping("/appraisal")
    public ApiResp<IPage<AppraisalToOtherVo>> getAppraisals(HttpServletRequest request,
                                                            @RequestParam(value = "name", required = false)String name,
                                                            @RequestParam(value = "userNumber", required = false)String userNumber,
                                                            @RequestParam("month")Integer month,
                                                            @RequestParam("identity")Integer rank,
                                                            @RequestParam("current")Integer current,
                                                            @RequestParam("size")Integer size){
        IPage<AppraisalToOtherVo> appraisals = appraisalService.getAppraisalsToTeacher(request, name, userNumber, month, rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 修改班级成员身份
     * @param identityDto
     * @return
     */
    @PutMapping("/modify-identity")
    public ApiResp<String> modifyIdentity(@RequestBody IdentityDto[] identityDto){
        classAdviserService.modifyIdentity(identityDto);
        return ApiResp.success("修改成功");
    }

    /**
     * 获取班级内的申诉
     * @param current 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/appeals")
    public ApiResp<IPage<AppealVo>> getAppeals(HttpServletRequest request,
                                               @RequestParam("current")Integer current,
                                               @RequestParam("size")Integer size){
        IPage<AppealVo> appealVoIPage = appealService.getAppeals(request, current, size);
        return ApiResp.success(appealVoIPage);
    }

    /**
     * 处理申诉
     * @param appealId 申述id
     * @return
     */
    @PutMapping("/appeal")
    public ApiResp<String> disposeAppeal(HttpServletRequest request,
                                         @RequestBody Long appealId){
        Boolean aBoolean = appealService.disposeAppeal(request, appealId);
        return ApiResp.judge(aBoolean, "操作成功", ResultCode.CLASS_NOT_MATCH_DATA);
    }
}
