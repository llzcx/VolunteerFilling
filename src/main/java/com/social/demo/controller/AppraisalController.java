package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
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


}
