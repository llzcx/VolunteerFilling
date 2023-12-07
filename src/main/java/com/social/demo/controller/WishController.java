package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.entity.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 志愿接口
 *
 * @author 周威宇
 */
@RestController
@RequestMapping("/wish")
@Validated
public class WishController {
    @Autowired
    private IWishService wishService;
    /**
     * 填写志愿接口
     */
    @PostMapping("/addWish")
    public ApiResp<Boolean> addWise(@RequestBody Wish wish){
        wish.setUserId(10L);
        return ApiResp.success(wishService.addWish(wish));
    }
    /**
     * 修改个人志愿接口
     */
    @PutMapping("modifyWise")
    public ApiResp<Boolean> modifyWise(@RequestBody Wish wish){
        return ApiResp.success(wishService.modifyWish(wish));
    }

    /**
     *个人查看志愿接口
     */
    @GetMapping("selectWish")
    public ApiResp<Wish> selectWish(){
        return ApiResp.success(wishService.selectWish(10L));
    }
}
