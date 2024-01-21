package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.data.dto.AppraisalDto;
import com.social.demo.data.vo.AppealVo;
import com.social.demo.data.vo.AppraisalToOtherVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * @param month 月份
     * @param rank 排序类型： 0 -不排、 -1从小到大 1从大到小
     * @param current 当前页码
     * @param size 每页数
     * @return
     */
    @GetMapping
    public ApiResp<IPage<AppraisalToOtherVo>> getAppraisals(HttpServletRequest request,
                                                            @RequestParam(value = "name", required = false)String name,
                                                            @RequestParam(value = "userNumber", required = false)String userNumber,
                                                            @RequestParam("month")Integer month,
                                                            @RequestParam("rank")Integer rank,
                                                            @RequestParam("current")Integer current,
                                                            @RequestParam("size")Integer size){
        IPage<AppraisalToOtherVo> appraisals = appraisalService.getAppraisalsToTeam(request, name, userNumber, month, rank, current, size);
        return ApiResp.success(appraisals);
    }

    /**
     * 上传学生综测情况
     * @param appraisalDto
     * @return
     */
    @PostMapping
    public ApiResp<String> uploadAppraisal(@RequestBody AppraisalDto appraisalDto){
        appraisalService.uploadAppraisal(appraisalDto);
        return ApiResp.success("上传成功");
    }

    /**
     * 综测小组获取学生申诉
     * @param request
     * @param username 学生姓名
     * @param userNumber 学号
     * @param state 状态
     * @param current 当前页码
     * @param size 每页数量
     * @return
     */
    @GetMapping("/appeals")
    public ApiResp<IPage<AppealVo>> getAppeals(HttpServletRequest request,
                                               @RequestParam(value = "username", required = false)String username,
                                               @RequestParam(value = "userNumber", required = false)String userNumber,
                                               @RequestParam(value = "state", required = false)Integer state,
                                               @RequestParam("current")Integer current,
                                               @RequestParam("size")Integer size){
        IPage<AppealVo> appealVoIPage = appealService.getAppealsByTeam(request, username, userNumber, state, current, size);
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
        Boolean aBoolean = appealService.disposeAppealByTeam(request, appealId);
        return ApiResp.judge(aBoolean, "操作成功", ResultCode.CLASS_NOT_MATCH_DATA);
    }
}
