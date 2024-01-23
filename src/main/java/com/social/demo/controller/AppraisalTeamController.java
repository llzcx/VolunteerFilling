package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.data.vo.AppealVo;
import com.social.demo.data.vo.AppraisalContentVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 综测小组获取本月学生综测
     * @param request
     * @param name 学生姓名
     * @param userNumber 学号
     * @param rank 排序类型： 0 -不排、 -1从小到大 1从大到小
     * @param current 当前页码
     * @param size 每页数
     * @return
     */
    @GetMapping("/appraisal/this")
    public ApiResp<IPage<AppraisalVo>> getAppraisalsThisMonth(HttpServletRequest request,
                                                     @RequestParam(value = "name", required = false)String name,
                                                     @RequestParam(value = "userNumber", required = false)String userNumber,
                                                     @RequestParam("rank")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size){
        IPage<AppraisalVo> appraisals = appraisalService.getAppraisalsToTeam(request, name, userNumber, TimeUtil.now().getMonthValue(),rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 综测小组获取学生综测
     * @param request
     * @param name 学生姓名
     * @param userNumber 学号
     * @param month 月份
     * @param rank 排序类型： 0 -不排、 -1从小到大 1从大到小
     * @param current 当前页码
     * @param size 每页数
     * @return
     */
    @GetMapping("/appraisal")
    public ApiResp<IPage<AppraisalVo>> getAppraisals(HttpServletRequest request,
                                                     @RequestParam(value = "name", required = false)String name,
                                                     @RequestParam(value = "userNumber", required = false)String userNumber,
                                                     @RequestParam(value = "month")Integer month,
                                                     @RequestParam("rank")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size){
        IPage<AppraisalVo> appraisals = appraisalService.getAppraisalsToTeam(request, name, userNumber, month, rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 上传学生综测情况
     * @param appraisalContentVo
     * @return
     */
    @PostMapping("/appraisal")
    public ApiResp<String> uploadAppraisal(@RequestBody AppraisalContentVo appraisalContentVo){
        appraisalService.uploadAppraisal(appraisalContentVo);
        return ApiResp.success("上传成功");
    }

    /**
     * 综测小组获取学生申诉
     * @param request
     * @return
     */
    @GetMapping("/appeals")
    public ApiResp<List<AppealVo>> getAppealsFinished(HttpServletRequest request,
                                                      @RequestParam(value = "state", required = false)Integer state){
        List<AppealVo> appealVos = appealService.getAppealByTeam(request, state);
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
}
