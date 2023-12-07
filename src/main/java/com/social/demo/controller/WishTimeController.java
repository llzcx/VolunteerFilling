package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.dao.repository.IWishTimeService;
import com.social.demo.entity.Wish;
import com.social.demo.entity.WishTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 志愿接口
 *
 * @author 周威宇
 */
@RestController
@RequestMapping("/wishTime")
@Validated
public class WishTimeController {
    @Autowired
    private IWishTimeService wishTimeService;

    /**
     * 填写志愿接口
     */
    @PostMapping("/addWishTime")
    public ApiResp<Boolean> addWiseTime(@RequestBody WishTime wishTime){
        return ApiResp.success(wishTimeService.addWishTime(wishTime));
    }
    /**
     * 修改个人志愿接口
     */
    @PutMapping("modifyWiseTime")
    public ApiResp<Boolean> modifyWiseTime(@RequestBody WishTime wishTime){
        return ApiResp.success(wishTimeService.modifyWishTime(wishTime));
    }

    /**
     *个人查看志愿接口
     */
    @GetMapping("selectWish")
    public ApiResp<List<WishTime>> selectWish(){
        LocalDate currentDate = LocalDate.now();
        Integer ago = currentDate.getYear();
        return ApiResp.success(wishTimeService.selectWishTime(ago));
    }
}
