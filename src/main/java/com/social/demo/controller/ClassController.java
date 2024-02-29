package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IClassService;
import com.social.demo.data.dto.ClassDto;
import com.social.demo.data.dto.ClassModifyDto;
import com.social.demo.data.vo.ClassVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ApiResp<String> createClass(@RequestBody ClassDto classDto){
        System.out.println(classDto);
        Boolean b = classService.create(classDto);
        return ApiResp.judge(b, "操作成功", ResultCode.IS_EXISTS);
    }

    /**
     * 获取班级
     * @param year 年份
     * @param current 当前页码
     * @param size 每页大小
     * @return 班级信息
     */
    @GetMapping
    public ApiResp<IPage<ClassVo>> getClass(@RequestParam(required = false) Integer year,
                                            @RequestParam("current")int current,
                                            @RequestParam("size")int size){
        IPage<ClassVo> classPage = classService.getClassPage(year, current, size);
        return ApiResp.success(classPage);
    }

    /**
     * 删除班级
     * @param classIds 班级id
     * @return
     */
    @DeleteMapping
    public ApiResp<String> delete(@RequestBody Long[] classIds){
        classService.delete(classIds);
        return ApiResp.success("操作成功");
    }

    /**
     * 修改班级信息
     * @param classModifyDto 班级信息
     * @return
     */
    @PutMapping
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
    public ApiResp<Boolean> judge(@RequestParam String className){
        Boolean b = classService.judgeClassName(className);
        return ApiResp.success(b);
    }
}
