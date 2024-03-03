package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.common.ApiResp;
import com.social.demo.dao.repository.*;
import com.social.demo.data.dto.MateDto;
import com.social.demo.data.vo.RankingVo;
import com.social.demo.entity.Major;
import com.social.demo.entity.Student;
import com.social.demo.entity.Wish;
import com.social.demo.entity.WishResult;
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
    public ApiResp<Boolean> MateResult(@RequestBody MateDto mateDto,Long timeId){
        Student student = new Student();
        student.setSchoolId(mateDto.getSchoolId());
        Integer ago = wishTimeService.selectAgo(timeId);
        student.setEnrollmentYear(ago);
        Long number = mateService.mateJudge(mateDto.getSchoolId(),mateDto.getTimeId());
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
                                         @RequestParam("timeId") Long timeId,@RequestParam("mateWay") Integer mateWay){
        return ApiResp.success(mateService.getWishResultBySchoolId(schoolId,timeId,mateWay));
    }
    /**
     * 查看匹配结果
     */
    @GetMapping("/getResult1")
    public ApiResp<IPage<WishResult>> getResult1(@RequestParam("schoolId") Long schoolId,
                                               @RequestParam("timeId") Long timeId,@RequestParam("mateWay") Integer mateWay,
                                               @RequestParam("current")Long current,@RequestParam("size") Long size){
        List<WishResult> wishResults = mateService.getWishResultBySchoolId(schoolId,timeId,mateWay);
        List<WishResult> wishResults1 = mateService.getWishResultBySchoolId1(schoolId,timeId,mateWay,current,size);
        IPage<WishResult> wishResultIPage = new Page<>();
        wishResultIPage.setRecords(wishResults1);
        wishResultIPage.setCurrent(current);
        wishResultIPage.setTotal(wishResults.size());
        wishResultIPage.setSize(size);
        return ApiResp.success(wishResultIPage);
    }
}
