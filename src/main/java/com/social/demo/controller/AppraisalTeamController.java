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
     * 综测小组获取学生综测
     * @param request
     * @param name 学生姓名
     * @param userNumber 学号
     * @param month 月份 0表示本月
     * @param rank 排序类型： 0 -不排、 -1从小到大 1从大到小
     * @param current 当前页码
     * @param size 每页数
     * @return
     */
    @GetMapping("/appraisal")
    public ApiResp<IPage<AppraisalVo>> getAppraisals(HttpServletRequest request,
                                                     @RequestParam(value = "name", required = false)String name,
                                                     @RequestParam(value = "userNumber", required = false)String userNumber,
                                                     @RequestParam("month")Integer month,
                                                     @RequestParam("rank")Integer rank,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size){
        IPage<AppraisalVo> appraisals = appraisalService.getAppraisalsToTeam(request, name, userNumber, month, rank, current, size);
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
     * 修改学生综测情况
     * @param appraisalContentVo
     * @return
     */
    @PutMapping("/appraisal")
    public ApiResp<String> modifyAppraisal(@RequestBody AppraisalContentVo appraisalContentVo){
        appraisalService.modifyAppraisal(appraisalContentVo);
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
}
