package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * 志愿时间接口
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
     * 填写志愿时间接口
     */
    @PostMapping("/addWishTime")
    public ApiResp<Boolean> addWiseTime(@RequestBody WishTime wishTime){
        return ApiResp.success(wishTimeService.addWishTime(wishTime));

    }
    /**
     * 修改志愿时间接口
     */
    @PutMapping("modifyWiseTime")
    public ApiResp<Boolean> modifyWiseTime(@RequestBody WishTime wishTime){
        return ApiResp.success(wishTimeService.modifyWishTime(wishTime));
    }

    /**
     *根据学校编码搜索志愿时间接口
     */
    @GetMapping("selectWishTime")
    public ApiResp<IPage<WishTime>> selectWishTime(@RequestParam("schoolId") Integer schoolId,
                                               @RequestParam("current")Long current,
                                               @RequestParam("size")Long size){
        return ApiResp.success(wishTimeService.selectWishTime(schoolId,current,size));
    }
    /**
     *根据入学时间搜索志愿时间接口
     */
    @GetMapping("selectWishTime1")
    public ApiResp<IPage<WishTime>> selectWishTime1(@RequestParam("schoolId") Integer schoolId,
                                               @RequestParam("ago") Integer ago,
                                               @RequestParam("current")Long current,
                                               @RequestParam("size")Long size){
        return ApiResp.success(wishTimeService.selectWishTime1(schoolId,ago,current,size));
    }
    /**
     * 删除志愿时间
     * @param id
     * @return
     */
    @DeleteMapping("/deleteMajor")
    public ApiResp<Boolean> deleteMajor(@RequestParam("id")Long id) {
        Boolean deleteArea = wishTimeService.deleteWishTime(id);
        return ApiResp.success(deleteArea);
    }

}
