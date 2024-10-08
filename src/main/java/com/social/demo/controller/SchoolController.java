package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.repository.ISchoolService;
import com.social.demo.data.dto.SchoolDto;
import com.social.demo.entity.School;
import com.social.demo.manager.security.identity.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学校接口
 *
 * @author 杨世博
 */
@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private ISchoolService schoolService;

    /**
     * 添加院校
     * @param schoolDto 院校信息
     * @return
     */
    @PostMapping
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> addSchool(@RequestBody SchoolDto schoolDto){
        Boolean addSchool = schoolService.addSchool(schoolDto);
        return ApiResp.success(addSchool);
    }

    /**
     * 修改院校信息
     * @param school 院校信息
     * @return
     */
    @PutMapping
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> modifySchool(@RequestBody School school){
        Boolean modifySchool = schoolService.modifySchool(school);
        return ApiResp.judge(modifySchool, modifySchool, ResultCode.SCHOOL_NOT_EXISTS);
    }

    /**
     * 删除院校
     * @param schoolId 院校id
     * @return
     */
    @DeleteMapping
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> deleteSchool(@RequestBody Long schoolId){
        Boolean deleteSchool = schoolService.deleteSchool(schoolId);
        return ApiResp.judge(deleteSchool, "删除成功", ResultCode.SCHOOL_NOT_EXISTS);
    }

    /**
     * 搜索院校
     * @param schoolName 院校名
     * @return 院校信息
     */
    @GetMapping
    @Identity(IdentityEnum.SUPER)
    public ApiResp<List<School>> getSchool(@RequestParam(value = "schoolName", required = false)String schoolName){
        List<School> school = schoolService.getSchool(schoolName);
        return ApiResp.judge(school != null, school, ResultCode.SCHOOL_NOT_EXISTS);
    }

    /**
     * 判断学校是否已存在
     * @param schoolName 学校名
     * @return
     */
    @GetMapping("/exists")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> judge(@RequestParam("schoolName") String schoolName){
        Boolean b = schoolService.judgeSchoolName(schoolName);
        return ApiResp.success(b);
    }

    /**
     * 管理员重置班级综测成绩
     * @param year 入学年份
     * @return
     */
    @GetMapping("/appraisal")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<String> resetClassAppraisal(@RequestParam Integer year){
        schoolService.resetClassAppraisal(year);
        return ApiResp.success("重置成功");
    }
}
