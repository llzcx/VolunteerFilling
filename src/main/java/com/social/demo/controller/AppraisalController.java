package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.data.vo.AppraisalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 综合测评
 *
 * @author 杨世博
 * @date 2023/12/19 15:28
 * @description AppraisalController
 */
@RestController
@RequestMapping("/appraisal")
@Validated
public class AppraisalController {

    @Autowired
    IAppraisalService appraisalService;

    /**
     * 获取综测的历史信息
     * @param year 年份
     * @param month 月份
     * @param className 班级名称，模糊查找
     * @param keyword 关键词：学号或姓名，模糊查找
     * @param current 当前页码
     * @param size 每页大小
     * @return
     */
    @GetMapping("/history")
    public ApiResp<IPage<AppraisalVo>> getAppraisalHistory(@RequestParam(value = "year", required = false)Integer year,
                                                           @RequestParam(value = "month", required = false)Integer month,
                                                           @RequestParam(value = "className", required = false)String className,
                                                           @RequestParam(value = "keyword",required = false)String keyword,
                                                           @RequestParam("current")Integer current,
                                                           @RequestParam("size")Integer size){
        IPage<AppraisalVo> appraisalHistory = appraisalService.getAppraisalHistory(year, month, className, keyword, current, size);
        return ApiResp.success(appraisalHistory);
    }
}
