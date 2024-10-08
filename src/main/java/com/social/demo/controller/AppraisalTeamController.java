package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.constant.RegConstant;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.dao.repository.IAppraisalSignatureService;
import com.social.demo.dao.repository.IAppraisalTeamService;
import com.social.demo.data.dto.AppraisalUploadDto;
import com.social.demo.data.dto.RemoveSignatureDto;
import com.social.demo.data.vo.*;
import com.social.demo.manager.security.identity.Identity;
import com.social.demo.util.URLUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 测评小组接口
 *
 * @author 杨世博
 * @date 2023/12/20 20:39
 * @description AppraisalTeamController
 */
@RestController
@RequestMapping("/appraisal-team")
@Validated
public class AppraisalTeamController {
    @Autowired
    IAppraisalService appraisalService;

    @Autowired
    IAppealService appealService;

    @Autowired
    IAppraisalTeamService appraisalTeamService;

    @Autowired
    IAppraisalSignatureService appraisalSignatureService;

    @Autowired
    URLUtil urlUtil;

    /**
     * 综测小组获取学生综测
     * @param request
     * @param keyword 学号或姓名
     * @param month 月份 0表示本月
     * @param rank 排序类型： 0 -不排、 -1从小到大 1从大到小
     * @param current 当前页码
     * @param size 每页数
     * @return
     */
    @GetMapping("/appraisal")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<YPage<AppraisalVo>> getAppraisals(HttpServletRequest request,
                                                     @RequestParam(value = "keyword", required = false)@Pattern(regexp = RegConstant.KEYWORD)String keyword,
                                                     @RequestParam("month")Integer month,
                                                     @RequestParam("rank")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size) throws UnknownHostException {
        YPage<AppraisalVo> appraisals = appraisalService.getAppraisalsToTeam(request, keyword, month, rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 上传学生综测情况
     * @param appraisalUploadDto
     * @return
     */
    @PostMapping("/appraisal")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<String> uploadAppraisal(@RequestBody AppraisalUploadDto appraisalUploadDto){
        appraisalService.uploadAppraisal(appraisalUploadDto);
        return ApiResp.success("修改成功");
    }

    /**
     * 综测小组获取学生申诉
     * @param request
     * @return
     */
    @GetMapping("/appeals")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<List<AppealVo>> getAppealsFinished(HttpServletRequest request){
        List<AppealVo> appealVos = appealService.getAppealByTeam(request);
        return ApiResp.success(appealVos);
    }

    /**
     * 处理申诉
     * @param appealId 申述id
     * @return
     */
    @PutMapping("/appeal")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<String> disposeAppeal(HttpServletRequest request,
                                         @RequestBody Long appealId){
        Boolean aBoolean = appealService.disposeAppealByTeam(request, appealId);
        return ApiResp.judge(aBoolean, "操作成功", ResultCode.CLASS_NOT_MATCH_DATA);
    }

    /**
     * 删除已完成申述
     * @param request
     * @param appealId
     * @return
     */
    @DeleteMapping("/appeal")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<String> deleteAppeals(HttpServletRequest request,
                                         @RequestBody Long[] appealId){
        Boolean b = appealService.deleteAppealsByTeam(request, appealId);
        return ApiResp.judge(b, "删除成功", ResultCode.NOT_DELETE_UNFINISHED);
    }

    /**
     * 获取可查询综测月份
     * @param request
     * @return
     */
    @GetMapping("/appraisal/month")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<List<Integer>> getMonth(HttpServletRequest request){
        List<Integer> list = appraisalService.getMonthToTeam(request);
        return ApiResp.success(list);
    }

    /**
     * 获取综测小组成员个人消息
     * @param request
     * @return
     */
    @GetMapping("/message")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<AppraisalTeamUserVo> getMessage(HttpServletRequest request){
        AppraisalTeamUserVo userMessage = appraisalTeamService.getMessage(request);
        return ApiResp.success(userMessage);
    }

    /**
     * 上传签名
     * @param file
     * @param month 0-表示本月
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/appraisal/signature")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<String> uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception{
        String fileName = appraisalTeamService.uploadSignature(file, month, request);
        return ApiResp.judge(fileName != null, urlUtil.getUrl(fileName), ResultCode.APPRAISAL_NOT_END);
    }

    /**
     * 获取综测签名
     * @param request
     * @param month
     * @return
     */
    @GetMapping("/appraisal/signature")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<String> getSignature(HttpServletRequest request,
                                        @RequestParam("month") Integer month) throws UnknownHostException {
        String url = appraisalSignatureService.getSignature(request, month);
        return ApiResp.success(url != null ? urlUtil.getUrl(url) : null);
    }

    /**
     * 移除学生签名
     * @param request
     * @return
     */
    @PutMapping("/appraisal/signature")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<String> removeSignature(HttpServletRequest request,
                                           @RequestBody RemoveSignatureDto removeSignatureDto){
        Boolean b = appraisalSignatureService.removeSignature(request, removeSignatureDto);
        return ApiResp.success("移除成功");
    }

    /**
     * 获取某月班级签名人数
     * @param request
     * @param month 0-本月
     * @return
     */
    @GetMapping("/appraisal/signature/count")
    @Identity(IdentityEnum.APPRAISAL_TEAM)
    public ApiResp<Integer> getSignatureCount(HttpServletRequest request,
                                              @RequestParam Integer month){
        Integer count = appraisalService.getSignatureCount(request, month);
        return ApiResp.success(count);
    }
}
