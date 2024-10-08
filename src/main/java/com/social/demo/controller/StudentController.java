package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.constant.RegConstant;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.dao.repository.IStudentService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.AppealDto;
import com.social.demo.data.dto.PasswordDto;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.vo.*;
import com.social.demo.entity.Student;

import com.social.demo.manager.security.context.SecurityContext;
import com.social.demo.manager.security.identity.Identity;
import com.social.demo.util.URLUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
import java.util.ArrayList;
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

    @Autowired
    URLUtil urlUtil;

    /**
     * 学生获取个人信息
     *
     * @param request
     * @return 学生个人信息
     */
    @GetMapping("/information")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<StudentVo> getInformation(HttpServletRequest request) throws UnknownHostException {
        StudentVo student = userService.getInformationOfStudent(request);
        return ApiResp.judge(student != null, student, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 学生修改个人信息（收件信息）
     *
     * @param request
     * @param userDtoByStudent 学生提交的修改信息
     * @return 是否修改成功
     */
    @PutMapping("/information")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<Boolean> modifyInformation(HttpServletRequest request,
                                              @RequestBody UserDtoByStudent userDtoByStudent) {
        Boolean b = userService.modifyInformation(request, userDtoByStudent);
        return ApiResp.judge(b, "修改成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 学生修改密码
     *
     * @param request
     * @param password 密码
     * @return
     */
    @PutMapping("/password")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<String> modifyPassword(HttpServletRequest request,
                                          @RequestBody PasswordDto password){
        Boolean b = userService.modifyPassword(request, password.getPassword());
        return ApiResp.judge(b, "修改成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 学生获取申诉
     *
     * @param request
     * @return
     */
    @GetMapping("/appeal")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<List<AppealVo>> getAppeal(HttpServletRequest request) {
        List<AppealVo> appeals = appealService.getAppealsToStudent(request);
        return ApiResp.success(appeals);
    }

    /**
     * 学生申诉
     *
     * @param request
     * @param appeal  申述内容
     * @return
     */
    @PostMapping("/appeal")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<String> submitAppeal(HttpServletRequest request,
                                        @RequestBody AppealDto appeal) {
        Boolean b = appealService.submitAppeal(request, appeal);
        return ApiResp.judge(b, "上传成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 撤销申诉
     *
     * @param request
     * @param appealId 申诉id
     * @return
     */
    @PutMapping("/appeal")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<String> quashAppeal(HttpServletRequest request,
                                       @RequestBody Long appealId) {
        Boolean aBoolean = appealService.quashAppeal(request, appealId);
        return ApiResp.judge(aBoolean, "撤销成功", ResultCode.USER_NOT_MATCH_DATA);
    }

    /**
     * 删除已完成申述
     *
     * @param request
     * @param appealId
     * @return
     */
    @DeleteMapping("/appeal")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<String> deleteAppeals(HttpServletRequest request,
                                         @RequestBody Long[] appealId) {
        Boolean b = appealService.deleteAppeals(request, appealId);
        return ApiResp.judge(b, "删除成功", ResultCode.NOT_DELETE_UNFINISHED);
    }

    /**
     * 获取本月学生个人的综测情况
     *
     * @param request
     * @return
     */
    @GetMapping("/this")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<AppraisalVo> getAppraisalThisMonth(HttpServletRequest request) throws UnknownHostException {
        AppraisalVo appraisal = appraisalService.getAppraisalThisMonth(request);
        return ApiResp.judge(appraisal != null, appraisal, ResultCode.APPRAISAL_NOT_EXISTS);
    }

    /**
     * 获取学生个人的综测情况
     *
     * @param request
     * @param month   月份
     * @return
     */
    @GetMapping
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<AppraisalVo> getAppraisal(HttpServletRequest request,
                                             Integer month) throws UnknownHostException {
        AppraisalVo appraisal = appraisalService.getAppraisal(request, month);
        return ApiResp.judge(appraisal != null, appraisal, ResultCode.APPRAISAL_NOT_EXISTS);
    }

    /**
     * 上传电子签名
     *
     * @param file    上传的签名文件
     * @param month   月份
     * @param request
     * @return
     */
    @PostMapping("/signature")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<String> uploadSignature(MultipartFile file,
                                           Integer month,
                                           HttpServletRequest request) throws Exception {
        String signature = appraisalService.uploadSignature(file, month, request);
        return ApiResp.success(signature != null ? urlUtil.getUrl(signature) : null);
    }

    /**
     * 获取学生排名
     * @param timeId
     * @param majorId
     * @return
     */
    @GetMapping("/getStudentRanking")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<RankingVo1> getStudentRanking(@RequestParam("majorId")Long majorId,
                                                 @RequestParam("timeId")Long timeId){
        Long userId = SecurityContext.get().getUserId();
        Student student = studentService.getStudent(userId);
        List<RankingVo> rankingVos;
        rankingVos = studentService.getRanking1(student,timeId,majorId);
        System.out.println(rankingVos);
        RankingVo1 rank = new RankingVo1();
        for(RankingVo rankingVo:rankingVos){
            if(rankingVo.getUserId().equals(userId)){
                rank.setRanking(rankingVo.getRanking());
                rank.setRankings(rankingVo.getRankings());
            }
        }
         return  ApiResp.success(rank);
    }
    /**
     * 获取学生排名
     * @return
     */
    @GetMapping("/getStudentRanking1")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<List<RankingVo1>> getStudentRanking(){
        Long userId = SecurityContext.get().getUserId();
        Student student = studentService.getStudent(userId);
        List<RankingVo1> ranks = new ArrayList<>();
        ranks.add(getRank(student,1));
        ranks.add(getRank(student,2));
        ranks.add(getRank(student,3));
        return  ApiResp.success(ranks);
    }
    public RankingVo1 getRank(Student student,Integer type){
        List<RankingVo> rankingVos;
        rankingVos = studentService.getRanking(type,student);
        RankingVo1 rank = new RankingVo1();
        for(RankingVo rankingVo:rankingVos){
            if(rankingVo.getUserId().equals(student.getUserId())){
                rank.setRanking(rankingVo.getRanking());
                rank.setRankings(rankingVo.getRankings());
            }
        }
        return rank;
    }
    /**
     * 获取历史学生信息
     *
     * @param year    年份
     * @param classId 班级id
     * @param keyword 关键词：学号 - 模糊；姓名 - 模糊
     * @return
     */
    @GetMapping("/history")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<IPage<StudentVo>> getStudentHistory(@RequestParam("year") Integer year,
                                                       @RequestParam(value = "classId", required = false) Integer classId,
                                                       @RequestParam(value = "keyword", required = false)@Pattern(regexp = RegConstant.KEYWORD) String keyword,
                                                       @RequestParam("current") Integer current,
                                                       @RequestParam("size") Integer size) throws UnknownHostException {
        IPage<StudentVo> studentVoIPage = studentService.getStudentHistory(year, classId, keyword, current, size);
        return ApiResp.success(studentVoIPage);
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
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<IPage<AppraisalVo>> getAppraisals(HttpServletRequest request,
                                                     @RequestParam(value = "keyword", required = false)@Pattern(regexp = RegConstant.KEYWORD)String keyword,
                                                     @RequestParam(value = "month", required = false)Integer month,
                                                     @RequestParam("rank")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size) throws UnknownHostException {
        IPage<AppraisalVo> appraisals = appraisalService.getAppraisalsToStudent(request, keyword, month, rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 获取综测可查询月份
     * @param request
     * @return
     */
    @GetMapping("/appraisal/month")
    @Identity({IdentityEnum.CLASS_ADVISER,IdentityEnum.APPRAISAL_TEAM,IdentityEnum.STUDENT})
    public ApiResp<List<Integer>> getMonth(HttpServletRequest request){
        List<Integer> list = appraisalService.getMonthToStudent(request);
        return ApiResp.success(list);
    }
}
