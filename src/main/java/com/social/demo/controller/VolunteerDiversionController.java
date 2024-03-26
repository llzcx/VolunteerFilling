package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.*;
import com.social.demo.data.dto.MateDto;
import com.social.demo.data.dto.ResultDto;
import com.social.demo.data.vo.RankingVo;
import com.social.demo.entity.*;
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
    public ApiResp<Boolean> MateResult(@RequestBody MateDto mateDto){
        System.out.println(mateDto);
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
    public ApiResp<List<WishResult>> getResult(@RequestParam("schoolId") Long schoolId,
                                         @RequestParam("timeId") Long timeId,@RequestParam("mateWay") Integer mateWay,
                                               @RequestParam("type")Integer type){
        return ApiResp.success(mateService.getWishResultBySchoolId(schoolId,timeId,mateWay,type));
    }
    /**
     *查看最终匹配结果
     */
    @GetMapping("/getResult2")
    public ApiResp<List<WishResult>> getPagingResult(@RequestParam("schoolId") Long schoolId,
                                               @RequestParam("timeId") Long timeId){
        return ApiResp.success(mateService.getWishResultBySchoolId2(schoolId,timeId));
    }
    /**
     *分页查看最终匹配结果
     */
    @GetMapping("/getPagingResult")
    public ApiResp<IPage<WishResult>> getResult2(@RequestParam("schoolId") Long schoolId,
                                                @RequestParam("timeId") Long timeId,
                                                @RequestParam("current")Long current,@RequestParam("size") Long size){
        List<WishResult> wishResults = mateService.getWishResultBySchoolId2(schoolId,timeId);
        List<WishResult> wishResults1 = mateService.getPagingWishResultBySchoolId(schoolId,timeId,current,size);
        IPage<WishResult> wishResultIPage = new Page<>();
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
    public ApiResp<IPage<WishResult>> getResult1(@RequestParam("schoolId") Long schoolId,
                                               @RequestParam("timeId") Long timeId,@RequestParam("mateWay") Integer mateWay,
                                               @RequestParam("current")Long current,@RequestParam("size") Long size,
                                                 @RequestParam("type")Integer type){
        List<WishResult> wishResults = mateService.getWishResultBySchoolId(schoolId,timeId,mateWay,type);
        List<WishResult> wishResults1 = mateService.getWishResultBySchoolId1(schoolId,timeId,mateWay,current,size,type);
        IPage<WishResult> wishResultIPage = new Page<>();
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
    public ApiResp<Boolean> uploadResult(@RequestBody List<ResultDto> resultDtos){
      return ApiResp.success(wishService.modifyWish1(resultDtos));
    }
    /**
     * 查看专业剩余招生人数
     */
    @GetMapping("/getAdmissionsMajor")
    public ApiResp<List<AdmissionsMajor>> getAdmissionsMajor(@RequestParam("type") Integer type,@RequestParam("timeId")Long timeId){
           return ApiResp.success(mateService.getAdmissionsMajor(type,timeId));
    }
}
