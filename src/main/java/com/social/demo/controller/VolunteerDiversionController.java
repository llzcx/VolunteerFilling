package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.repository.*;
import com.social.demo.data.dto.MateDto;
import com.social.demo.data.dto.ResultDto;
import com.social.demo.data.vo.RankingVo;
import com.social.demo.entity.*;
import com.social.demo.manager.security.identity.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 志愿匹配接口
 */
@RestController
@RequestMapping("/volunteerDiversion")
@Validated
public class VolunteerDiversionController {
    @Autowired
    private IMajorService majorService;
    @Autowired
    private IWishService wishService;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IMateService mateService;
    @Autowired
    private IWishTimeService wishTimeService;
    /**
     * 进行志愿匹配
     */
    @PostMapping("/Mate")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> MateResult(@RequestBody MateDto mateDto){
        if(mateDto.getType1()==1){
            mateService.updateWishResult(mateDto.getTimeId(),mateDto.getType());
            mateService.updateAdmissionsMajor(mateDto.getTimeId(),mateDto.getType());
        }
        Long m =mateService.mateJudge(mateDto.getTimeId(),mateDto.getType());
        if(m>0){
            return ApiResp.fail(ResultCode.REPEATED_GENERATION);
        }
        Student student = new Student();
        student.setSchoolId(mateDto.getSchoolId());
        Integer ago = wishTimeService.selectAgo(mateDto.getTimeId());
        student.setEnrollmentYear(ago);
        List<RankingVo> rankingVos  = studentService.getRanking(3,student);
        List<Major> majors = majorService.getSchoolMajor(mateDto.getSchoolId());
        List<Wish> wishes = wishService.selectSchool(mateDto.getSchoolId(),mateDto.getTimeId());
        if (mateDto.getType()==1){
            mateService.firstMate(rankingVos,majors,wishes);
        }else {
            mateService.parallelMate(rankingVos,majors,wishes);
        }
        return ApiResp.success(true);
    }
    /**
     * 查看匹配结果
     */
    @GetMapping("/getResult")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<List<WishResult1>> getResult(@RequestParam("schoolId") Long schoolId,
                                         @RequestParam("timeId") Long timeId,@RequestParam("mateWay") Integer mateWay,
                                               @RequestParam("type")Integer type){
        List<WishResult1> wishResult1s = mateService.getWishResultBySchoolId(schoolId,timeId,mateWay,type);
        wishResult1s = getWishResult(wishResult1s);
        return ApiResp.success(wishResult1s);
    }
    /**
     *查看最终匹配结果
     */
    @GetMapping("/getResult2")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<List<WishResult1>> getPagingResult(@RequestParam("schoolId") Long schoolId,
                                               @RequestParam("timeId") Long timeId){
        List<WishResult1> wishResults = mateService.getWishResultBySchoolId3(schoolId,timeId);
        wishResults =getWishResult(wishResults);
        return ApiResp.success(wishResults);
    }
    /**
     *分页查看最终匹配结果
     */
    @GetMapping("/getPagingResult")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<IPage<WishResult1>> getResult2(@RequestParam("schoolId") Long schoolId,
                                                @RequestParam("timeId") Long timeId,
                                                @RequestParam("current")Long current,@RequestParam("size") Long size){
        List<WishResult> wishResults = mateService.getWishResultBySchoolId2(schoolId,timeId);
        List<WishResult1> wishResults1 = mateService.getPagingWishResultBySchoolId(schoolId,timeId,current,size);
        wishResults1 =getWishResult(wishResults1);
        IPage<WishResult1> wishResultIPage = new Page<>();
        wishResultIPage.setRecords(wishResults1);
        wishResultIPage.setCurrent(current);
        wishResultIPage.setTotal(wishResults.size());
        wishResultIPage.setSize(size);
        return ApiResp.success(wishResultIPage);
    }
    /**
     * 分页查看匹配结果
     */
    @GetMapping("/getResult1")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<IPage<WishResult1>> getResult1(@RequestParam("schoolId") Long schoolId,
                                               @RequestParam("timeId") Long timeId,@RequestParam("mateWay") Integer mateWay,
                                               @RequestParam("current")Long current,@RequestParam("size") Long size,
                                                 @RequestParam("type")Integer type){
        List<WishResult1> wishResults = mateService.getWishResultBySchoolId(schoolId,timeId,mateWay,type);
        List<WishResult1> wishResults1 = mateService.getWishResultBySchoolId1(schoolId,timeId,mateWay,current,size,type);
        wishResults1 =getWishResult(wishResults1);
        IPage<WishResult1> wishResultIPage = new Page<>();
        wishResultIPage.setRecords(wishResults1);
        wishResultIPage.setCurrent(current);
        wishResultIPage.setTotal(wishResults.size());
        wishResultIPage.setSize(size);
        return ApiResp.success(wishResultIPage);
    }
    /**
     * 输入结果
     */
    @PostMapping("/uploadResult")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> uploadResult(@RequestBody List<ResultDto> resultDtos){
      return ApiResp.success(wishService.modifyWish1(resultDtos));
    }
    /**
     * 查看专业剩余招生人数
     */
    @GetMapping("/getAdmissionsMajor")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<List<AdmissionsMajor>> getAdmissionsMajor(@RequestParam("type") Integer type,@RequestParam("timeId")Long timeId){
           return ApiResp.success(mateService.getAdmissionsMajor(type,timeId));
    }
    public List<WishResult1>  getWishResult(List<WishResult1> wishResults1){
        for(WishResult1 wishResult1:wishResults1){
            if(wishResult1.getMajorName() == null){
                wishResult1.setResult("调剂");
            }else if(wishResult1.getMajorName().equals(wishResult1.getFirstName())){
                wishResult1.setResult("第一志愿");
            }else if(wishResult1.getMajorName().equals(wishResult1.getSecondName())){
                wishResult1.setResult("第二志愿");
            } else if(wishResult1.getMajorName().equals(wishResult1.getThirdName())){
                wishResult1.setResult("第三志愿");
            }else {
                wishResult1.setResult("调剂");
            }
        }
        return wishResults1;
    }
}
