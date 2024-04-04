package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.repository.IAutographService;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.dao.repository.IWishTimeService;
import com.social.demo.data.vo.NotAcceptedVos;
import com.social.demo.data.vo.WishTimeVo;
import com.social.demo.data.vo.WishTimeVo1;
import com.social.demo.entity.Autograph;
import com.social.demo.entity.Wish;
import com.social.demo.entity.WishTime;
import com.social.demo.manager.security.context.SecurityContext;
import com.social.demo.manager.security.identity.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private IAutographService autographService;
    /**
     * 填写志愿时间接口
     */
    @PostMapping("/addWishTime")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> addWiseTime(@RequestBody WishTime wishTime){
        return ApiResp.success(wishTimeService.addWishTime(wishTime));

    }
    /**
     * 修改志愿时间接口
     */
    @PutMapping("modifyWiseTime")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> modifyWiseTime(@RequestBody WishTime wishTime){
        return ApiResp.success(wishTimeService.modifyWishTime(wishTime));
    }

    /**
     *根据学校编码搜索志愿时间接口
     */
    @GetMapping("selectWishTime")
    @Identity({IdentityEnum.SUPER,IdentityEnum.STUDENT})
    public ApiResp<IPage<WishTime>> selectWishTime(@RequestParam("schoolId") Integer schoolId,
                                               @RequestParam("current")Long current,
                                               @RequestParam("size")Long size){
        return ApiResp.success(wishTimeService.selectWishTime(schoolId,current,size));
    }
    /**
     *根据入学时间搜索志愿时间接口
     */
    @GetMapping("selectWishTime1")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<IPage<WishTime>> selectWishTime1(@RequestParam("schoolId") Integer schoolId,
                                               @RequestParam("ago") Integer ago,
                                               @RequestParam("current")Long current,
                                               @RequestParam("size")Long size){
        return ApiResp.success(wishTimeService.selectWishTime1(schoolId,ago,current,size));
    }
    /**
     *搜索志愿时间未录取学生接口
     */
    @GetMapping("selectNotAccepted")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<List<NotAcceptedVos>> selectNotAccepted(@RequestParam("timeId") Long timeId){
        return ApiResp.success(wishTimeService.selectNotAccepted(timeId));
    }
    /**
     *根据学生id搜索志愿时间接口
     */
    @GetMapping("selectWishTime2")
    @Identity(IdentityEnum.STUDENT)
    public ApiResp<List<WishTimeVo1>> selectWishTime2(){
        Long userId = SecurityContext.get().getUserId();
        List<WishTimeVo1> wishTimeVos = wishTimeService.selectWishTime2(userId);
        for(WishTimeVo1 wishTimeVo:wishTimeVos){
            LocalDateTime currentTime = LocalDateTime.now(); // 获取当前时间
            if (currentTime.isAfter(wishTimeVo.getStartTime())&&currentTime.isBefore(wishTimeVo.getEndTime())) {
                wishTimeVo.setState(1L);
            }else if(currentTime.isBefore(wishTimeVo.getStartTime())){
                wishTimeVo.setState(0L);
            }else {
                wishTimeVo.setState(2L);
            }
            List<Autograph> autographList = autographService.getAutograph(userId,wishTimeVo.getId());
            wishTimeVo.setAutographList(autographList);
        }
        return ApiResp.success(wishTimeVos);
    }
    /**
     * 删除志愿时间
     * @param id
     * @return
     */
    @DeleteMapping("/deleteMajor")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> deleteMajor(@RequestParam("id")Long id) {
        Boolean deleteArea = wishTimeService.deleteWishTime(id);
        return ApiResp.success(deleteArea);
    }


}
