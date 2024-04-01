package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.dao.repository.IAppraisalSignatureService;
import com.social.demo.dao.repository.IAppraisalTeamService;
import com.social.demo.data.dto.RemoveSignatureDto;
import com.social.demo.data.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@Component
public class AppraisalTeamController {
    @Value("${file.URL}")
    private String URL;
    @Autowired
    IAppraisalService appraisalService;

    @Autowired
    IAppealService appealService;

    @Autowired
    IAppraisalTeamService appraisalTeamService;

    @Autowired
    IAppraisalSignatureService appraisalSignatureService;

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
    public ApiResp<YPage<AppraisalVo>> getAppraisals(HttpServletRequest request,
                                                     @RequestParam(value = "keyword", required = false)String keyword,
                                                     @RequestParam("month")Integer month,
                                                     @RequestParam("rank")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size){
        YPage<AppraisalVo> appraisals = appraisalService.getAppraisalsToTeam(request, keyword, month, rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 上传学生综测情况
     * @param appraisalContentVos
     * @return
     */
    @PostMapping("/appraisal")
    public ApiResp<String> uploadAppraisal(@RequestBody AppraisalContentVo[] appraisalContentVos){
        appraisalService.uploadAppraisal(appraisalContentVos);
        return ApiResp.success("上传成功");
    }

    /**
     * 综测小组获取学生申诉
     * @param request
     * @return
     */
    @GetMapping("/appeals")
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
    public ApiResp<String> uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception{
        String fileName = appraisalTeamService.uploadSignature(file, month, request);
        return ApiResp.judge(fileName != null, PropertiesConstant.URL + fileName, ResultCode.APPRAISAL_NOT_END);
    }

    /**
     * 获取综测签名
     * @param request
     * @param month
     * @return
     */
    @GetMapping("/appraisal/signature")
    public ApiResp<String> getSignature(HttpServletRequest request,
                                        @RequestParam("month") Integer month){
        String url = appraisalSignatureService.getSignature(request, month);
        return ApiResp.success(url != null ? PropertiesConstant.URL + url : null);
    }

    /**
     * 移除学生签名
     * @param request
     * @return
     */
    @PutMapping("/appraisal/signature")
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
    public ApiResp<Integer> getSignatureCount(HttpServletRequest request,
                                              @RequestParam Integer month){
        Integer count = appraisalService.getSignatureCount(request, month);
        return ApiResp.success(count);
    }
}
