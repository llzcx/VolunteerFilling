package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.dao.repository.IStudentService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.AppealDto;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.vo.*;
import com.social.demo.entity.Student;

import com.social.demo.manager.security.context.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Autowired
    IAppraisalService appraisalService;
    @Autowired
    IStudentService studentService;
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
     * @return
     */
    @GetMapping("/appeal")
    public ApiResp<List<AppealVo>> getAppeal(HttpServletRequest request){
        List<AppealVo> appeals = appealService.getAppealsToStudent(request);
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
                                        @RequestBody AppealDto appeal){
        Boolean b = appealService.submitAppeal(request, appeal);
        return ApiResp.judge(b,"上传成功",ResultCode.DATABASE_DATA_EXCEPTION);
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

    /**
     * 删除已完成申述
     * @param request
     * @param appealId
     * @return
     */
    @DeleteMapping("/appeal")
    public ApiResp<String> deleteAppeals(HttpServletRequest request,
                                        @RequestBody Long[] appealId){
        Boolean b = appealService.deleteAppeals(request, appealId);
        return ApiResp.judge(b, "删除成功", ResultCode.NOT_DELETE_UNFINISHED);
    }

    /**
     * 获取本月学生个人的综测情况
     * @param request
     * @return
     */
    @GetMapping("/this")
    public ApiResp<AppraisalVo> getAppraisalThisMonth(HttpServletRequest request){
        AppraisalVo appraisal = appraisalService.getAppraisalThisMonth(request);
        return ApiResp.judge(appraisal != null, appraisal, ResultCode.APPRAISAL_NOT_EXISTS);
    }

    /**
     * 获取学生个人的综测情况
     * @param request
     * @param month 月份
     * @return
     */
    @GetMapping
    public ApiResp<AppraisalVo> getAppraisal(HttpServletRequest request,
                                             Integer month){
        AppraisalVo appraisal = appraisalService.getAppraisal(request, month);
        return ApiResp.judge(appraisal != null, appraisal, ResultCode.APPRAISAL_NOT_EXISTS);
    }

    /**
     * 上传电子签名
     * @param file 上传的签名文件
     * @param month 月份
     * @param request
     * @return
     */
    @PostMapping("/signature")
    public ApiResp<String> uploadSignature(MultipartFile file,
                                           Integer month,
                                           HttpServletRequest request) throws Exception {
        String signature = appraisalService.uploadSignature(file, month, request);
        return ApiResp.success(signature);
    }

    /**
     * 获取学生排名
     * @param type
     * @return
     */
    @GetMapping("/getStudentRanking")
    public ApiResp<RankingVo1> getStudentRanking(@RequestParam("type") Integer type){
        Long userId = SecurityContext.get().getUserId();
        Student student = studentService.getStudent(userId);
        List<RankingVo> rankingVos  = studentService.getRanking(type,student);
        RankingVo1 rank = new RankingVo1();
        for(RankingVo rankingVo:rankingVos){
            if(rankingVo.getUserId().equals(userId)){
                rank.setRanking(rankingVo.getRanking());
                rank.setRankings(rankingVo.getRankings());
            }
        }
         return  ApiResp.success(rank);
    }
}
