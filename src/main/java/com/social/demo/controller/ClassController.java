package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.repository.IClassService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.ClassDto;
import com.social.demo.data.dto.ClassModifyDto;
import com.social.demo.data.vo.ClassVo;
import com.social.demo.data.vo.StudentVo;
import com.social.demo.manager.security.identity.Identity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

/**
 * 班级管理接口
 *
 * @author 杨世博
 * @date 2023/12/4 15:40
 * @description 班级管理接口
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    IClassService classService;

    /**
     * 创建班级
     * @param classDto 班级消息
     * @return
     */
    @PostMapping
    @Identity(IdentityEnum.SUPER)
    public ApiResp<String> createClass(@RequestBody ClassDto classDto){
        Boolean b = classService.create(classDto);
        return ApiResp.judge(b, "操作成功", ResultCode.IS_EXISTS);
    }

    /**
     * 获取班级分页
     * @param year 年份
     * @param current 当前页码
     * @param size 每页大小
     * @return 班级信息
     */
    @GetMapping
    @Identity(IdentityEnum.SUPER)
    public ApiResp<IPage<ClassVo>> getClassPage(@RequestParam(required = false) Integer year,
                                            @RequestParam("current")int current,
                                            @RequestParam("size")int size){
        IPage<ClassVo> classPage = classService.getClassPage(year, current, size);
        return ApiResp.success(classPage);
    }

    /**
     * 获取班级
     * @param year 年份
     * @return 班级信息
     */
    @GetMapping("/list")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<List<ClassVo>> getClass(@RequestParam(required = false) Integer year){
        List<ClassVo> classList = classService.getClassList(year);
        return ApiResp.success(classList);
    }

    /**
     * 删除班级
     * @param classIds 班级id
     * @return
     */
    @DeleteMapping
    @Identity(IdentityEnum.SUPER)
    public ApiResp<String> delete(@RequestBody Long[] classIds){
        Boolean delete = classService.delete(classIds);
        return ApiResp.judge(delete, "操作成功", ResultCode.CLASS_DELETE_LOSE);
    }

    /**
     * 修改班级信息
     * @param classModifyDto 班级信息
     * @return
     */
    @PutMapping
    @Identity(IdentityEnum.SUPER)
    public ApiResp<String> modify(@RequestBody ClassModifyDto classModifyDto){
        System.out.println(classModifyDto);
        Boolean b = classService.modify(classModifyDto);
        return ApiResp.judge(b, "操作成功", ResultCode.IS_EXISTS);
    }

    /**
     * 判断班级是否已存在
     * @param className 班级名
     * @return
     */
    @GetMapping("/exists")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> judge(@RequestParam String className){
        Boolean b = classService.judgeClassName(className);
        return ApiResp.success(b);
    }

    /**
     * 获取某个班级的所有学生 - 用户打印
     * @param classId
     * @return
     */
    @GetMapping("/student")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<List<StudentVo>> getClassStudents(@RequestParam("classId")Long classId) throws UnknownHostException {
        List<StudentVo> studentVos = classService.getClassStudents(classId);
        return ApiResp.success(studentVos);
    }
}
