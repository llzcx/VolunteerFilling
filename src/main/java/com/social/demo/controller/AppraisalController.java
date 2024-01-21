package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.data.vo.AppraisalVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 获取学生个人的综测情况
     * @param request
     * @param month 月份
     * @return
     */
    @GetMapping
    public ApiResp<AppraisalVo> getAppraisal(HttpServletRequest request,
                                             Integer month){
        AppraisalVo appraisal = appraisalService.getAppraisal(request, month);
        return ApiResp.success(appraisal);
    }

    /**
     * 上传电子签名
     * @param file 上传的签名文件
     * @param request
     * @return
     */
    @PostMapping("/signature")
    public ApiResp<String> uploadSignature(@RequestBody MultipartFile file,
                                           HttpServletRequest request) throws Exception {
        appraisalService.uploadSignature(file, request);
        return ApiResp.success("上传成功");
    }

    /**
     * 获取个人某月的综测签名
     * @param month 月份
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/signature")
    public void downloadSignature(@RequestParam("month") Integer month,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        appraisalService.downloadSignature(month, request, response);
    }

    /**
     * 获取其他用户某月的综测签名
     * @param month 月份
     * @param userNumber 学号
     * @param response
     * @throws Exception
     */
    @GetMapping("/other/signature")
    public void downloadSignatureOther(@RequestParam("month") Integer month,
                                  @RequestParam("userNumber") String userNumber,
                                  HttpServletResponse response) throws Exception {
        appraisalService.downloadSignature(month, userNumber, response);
    }
}
