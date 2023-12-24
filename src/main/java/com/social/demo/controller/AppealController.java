package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.data.vo.AppealVo;
import com.social.demo.entity.Appeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 申诉接口
 *
 * @author 杨世博
 * @date 2023/12/22 15:45
 * @description AppealController
 */
@RestController
@RequestMapping("/appeal")
@Validated
public class AppealController {

    @Autowired
    IAppealService appealService;


}
