package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.repository.*;
import com.social.demo.data.dto.AppraisalTeamDto;
import com.social.demo.data.dto.SignatureDto;
import com.social.demo.data.dto.UserDtoByTeacher;
import com.social.demo.data.vo.*;
import com.social.demo.manager.security.identity.Identity;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Autowired
    IAppraisalTeamService appraisalTeamService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    IAppraisalSignatureService appraisalSignatureService;

    /**
     * 班级成员列表
     * @param keyword 学号或者姓名 模糊查找
     * @param role 班级职称
     * @param rank 是否根据综测排名排序 -1-倒叙 0-不排序 1-排序
     * @param current 当前页数
     * @param size 每页大小
     * @return 班级成员列表
     */
    @GetMapping("/students")
    public ApiResp<IPage<ClassUserVo>> getStudents(HttpServletRequest request,
                                                     @RequestParam(value = "keyword", required = false)String keyword,
                                                     @RequestParam(value = "role", required = false)String role,
                                                     @RequestParam(value = "rank")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size){
        IPage<ClassUserVo> classMemberVoIPage = classAdviserService.getStudents(request, keyword, role, rank, current, size);
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
    public ApiResp<String> modifyStudent(@RequestBody UserDtoByTeacher userDtoByTeacher) throws IllegalAccessException {
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
     * @param keyword 关键词 学号或名字 模糊查找
     * @param month 月份 0表示本月
     * @param rank 是否根据综测成绩排序 0不排序 -1从小到大 1从大到小
     * @param current 当前页码
     * @param size 每页大小
     * @return
     */
    @GetMapping("/appraisal")
    public ApiResp<IPage<AppraisalVo>> getAppraisals(HttpServletRequest request,
                                                            @RequestParam(value = "keyword", required = false)String keyword,
                                                            @RequestParam(value = "month", required = false)Integer month,
                                                            @RequestParam("identity")Integer rank,
                                                            @RequestParam("current")Integer current,
                                                            @RequestParam("size")Integer size){
        IPage<AppraisalVo> appraisals = appraisalService.getAppraisalsToTeacher(request, keyword, month, rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 获取分页获取学生本月综测信息
     * @param keyword 学号或学生姓名 模糊查找
     * @param rank 是否根据综测成绩排序 0不排序 -1从小到大 1从大到小
     * @param current 当前页码
     * @param size 每页大小
     * @return
     */
    @GetMapping("/appraisal/this")
    public ApiResp<IPage<AppraisalVo>> getAppraisalsThis(HttpServletRequest request,
                                                     @RequestParam(value = "keyword", required = false)String keyword,
                                                     @RequestParam("identity")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size){
        IPage<AppraisalVo> appraisals = appraisalService.getAppraisalsToTeacher(request, keyword, TimeUtil.now().getMonthValue(), rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 获取班级内的申诉
     * @return
     */
    @GetMapping("/appeals")
    public ApiResp<List<AppealVo>> getAppealsFinished(HttpServletRequest request){
        List<AppealVo> appealVos = appealService.getAppealByTeacher(request);
        return ApiResp.success(appealVos);
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

    /**
     * 删除已完成申述
     * @param request
     * @param appealId
     * @return
     */
    @DeleteMapping("/appeal")
    public ApiResp<String> deleteAppeals(HttpServletRequest request,
                                         @RequestBody Long[] appealId){
        Boolean b = appealService.deleteAppealsByTeacher(request, appealId);
        return ApiResp.judge(b, "删除成功", ResultCode.NOT_DELETE_UNFINISHED);
    }

    /**
     * 添加学生至综测小组
     * @param userNumbers 学号
     * @return
     */
    @PostMapping("/appraisal/team")
    public ApiResp<String> addAppraisalTeam(HttpServletRequest request, @RequestBody String[] userNumbers){
        Boolean b = appraisalTeamService.addAppraisalTeam(request, userNumbers);
        return ApiResp.judge(b, "添加成功", ResultCode.USER_IS_TEAM);
    }

    /**
     * 班主任获取本班综测小组成员
     * @param request
     * @return
     */
    @GetMapping("/appraisal/team")
    public ApiResp<List<AppraisalTeamVo>> getAppraisalTeam(HttpServletRequest request){
        List<AppraisalTeamVo> list = appraisalTeamService.getAppraisalTeam(request);
        return ApiResp.success(list);
    }

    /**
     * 重置综测小组成员密码
     * @param userNumber 被重置学生学号
     * @return
     */
    @PutMapping("/appraisal/team")
    public ApiResp<String> resetAppraisalTeamPwd(@RequestParam("userNumber")String userNumber){
        appraisalTeamService.resetAppraisalTeamPwd(userNumber);
        return ApiResp.success("重置成功");
    }

    /**
     * 平均分配班级成员
     * @param request
     * @return
     */
    @PostMapping("/appraisal/class/average")
    public ApiResp<String> averageClassMember(HttpServletRequest request){
        Boolean b = appraisalTeamService.averageClassMember(request);
        return ApiResp.judge(b, "操作成功", ResultCode.GROUP_ALREADY_EXISTS);
    }

    /**
     * 撤销分配班级成员
     * @param request
     * @return
     */
    @DeleteMapping("/appraisal/class/revocation")
    public ApiResp<String> revocationMember(HttpServletRequest request){
        appraisalTeamService.revocationMember(request);
        return ApiResp.success("重置成功");
    }

    /**
     * 撤销小组成员身份
     * @param userNumbers 被撤销的小组成员账号
     * @return
     */
    @DeleteMapping("/appraisal/team/revocation")
    public ApiResp<String> revocationTeam(HttpServletRequest request,
                                          @RequestBody String[] userNumbers){
        Boolean b = appraisalTeamService.revocationTeam(request, userNumbers);
        return ApiResp.judge(b, "操作成功", ResultCode.REMOVE_MEMBER_FIRST);
    }

    /**
     * 手动分配班级成员
     * @param appraisalTeamDto
     * @return
     */
    @PostMapping("/appraisal/class/allocation")
    public ApiResp<String> allocationClassMember(@RequestBody AppraisalTeamDto appraisalTeamDto){
        appraisalTeamService.allocationClassMember(appraisalTeamDto);
        return ApiResp.success("操作成功");
    }

    /**
     * 修改某月综测进度
     * @param month 月份
     * @param isEnd 是否开启
     * @return
     */
    @PutMapping("/end")
    public ApiResp<String> setIsEnd(HttpServletRequest request,
                                    @RequestParam("month") Integer month,
                                    @RequestParam("end") Boolean isEnd){
        classAdviserService.setIsEnd(request, month, isEnd);
        return ApiResp.success("修改成功");
    }

    /**
     * 获取班级某月综测状态 true-开启 false-关闭 null-暂无
     * @param request
     * @param month 0-表示本月
     * @return
     */
    @GetMapping("/appraisal/state")
    public ApiResp<Boolean> getClassAppraisalState(HttpServletRequest request,
                                                   @RequestParam("month") Integer month){
        Boolean b = classAdviserService.getClassAppraisalState(request, month);
        return ApiResp.success(b);
    }

    /**
     * 获取综测可查询月份
     * @param request
     * @return
     */
    @GetMapping("/appraisal/month")
    @Identity({IdentityEnum.CLASS_ADVISER,IdentityEnum.APPRAISAL_TEAM,IdentityEnum.STUDENT})
    public ApiResp<List<Integer>> getMonth(HttpServletRequest request){
        List<Integer> list = appraisalService.getMonthToTeacher(request);
        return ApiResp.success(list);
    }

    /**
     * 上传综测签名
     * @param file
     * @param month
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/appraisal/signature")
    public ApiResp<String> uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception{
        String fileName = classAdviserService.uploadSignature(file, month, request);
        return ApiResp.success(fileName);
    }

    /**
     * 获取签名
     * @param request
     * @param month
     * @return
     */
    @GetMapping("/appraisal/signature")
    public ApiResp<String> getSignature(HttpServletRequest request, Integer month){
        String url = appraisalSignatureService.getSignature(request, month);
        return ApiResp.success(url);
    }
}
