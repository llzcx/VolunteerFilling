package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IGradeService;
import com.social.demo.data.dto.GradeDto;
import com.social.demo.data.dto.GradeSubjectDto;
import com.social.demo.data.vo.GradeVo;
import com.social.demo.entity.GradeSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成绩
 *
 * @author 杨世博
 * @date 2024/1/22 19:59
 * @description GradeController
 */
@RestController
@RequestMapping("/grade")
@Validated
public class GradeController {

    @Autowired
    IGradeService gradeService;

    /**
     * 获取成绩科目列表
     * @return
     */
    @GetMapping("/subject")
    public ApiResp<List<GradeSubject>> getGradeSubjects(){
        List<GradeSubject> subjects = gradeService.getGradeSubject();
        return ApiResp.success(subjects);
    }

    /**
     * 上传科目名称
     * @param gradeSubjectDto 科目
     * @return
     */
    @PostMapping("/subject")
    public ApiResp<String> addGradeSubject(@RequestBody GradeSubjectDto gradeSubjectDto){
        gradeService.addGradeSubject(gradeSubjectDto);
        return ApiResp.success("添加成功");
    }

    /**
     * 删除
     * @param gradeSubjectId
     * @return
     */
    @DeleteMapping("/subject")
    public ApiResp<String> deleteGradeSubject(@RequestParam("id")Integer gradeSubjectId){
        Boolean b = gradeService.deleteGradeSubject(Long.valueOf(gradeSubjectId));
        return ApiResp.judge(b, "删除成功", ResultCode.MAJOR_NOT_DELETE);
    }

    /**
     * 修改科目内容
     * @param gradeSubject 科目
     * @return
     */
    @PutMapping("/subject")
    public ApiResp<String> modifyGradeSubject(@RequestBody GradeSubject gradeSubject){
        Boolean b = gradeService.modifyGradeSubject(gradeSubject);
        return ApiResp.judge(b, "修改成功", ResultCode.MAJOR_NOT_MODIFY);
    }

    /**
     * 上传成绩
     * @param gradeDto
     * @return
     */
    @PostMapping
    public ApiResp<String> uploadGrades(@RequestBody GradeDto[] gradeDto){
        Boolean b = gradeService.uploadGrades(gradeDto);
        return ApiResp.judge(b, "上传成功", ResultCode.GRADE_NOT_EXISTS);
    }

    /**
     * 学生成绩
     * @param year 年份
     * @param keyword 查找关键字，姓名或学号，模糊查找
     * @param current 当前页码
     * @param size 每页多少条
     * @return
     */
    @GetMapping
    public ApiResp<IPage<GradeVo>> getGrades(@RequestParam(value = "year", required = false)String year,
                                            @RequestParam("keyword") String keyword,
                                            @RequestParam("current")Integer current,
                                            @RequestParam("size")Integer size){
        IPage<GradeVo> gradeVoIPage = gradeService.getGrades(year, keyword, current, size);
        return ApiResp.success(gradeVoIPage);
    }

    /**
     * 重置学生成绩
     * @param userNumbers
     * @return
     */
    @PutMapping("/reset")
    public ApiResp<String> deleteGrades(@RequestBody String[] userNumbers){
        gradeService.deleteGrades(userNumbers);
        return ApiResp.success("重置成功");
    }

    /**
     * 修改学生成绩
     * @param gradeDto
     * @return
     */
    @PutMapping("/grade")
    public ApiResp<String> modifyGrade(@RequestBody GradeDto gradeDto){
        gradeService.modifyGrade(gradeDto);
        return ApiResp.success("修改成功");
    }

    /**
     * 获取所有成绩
     * @param year 年份，不传即为今年
     * @return
     */
    @GetMapping("/all")
    public ApiResp<List<GradeVo>> getAllGrade(@RequestParam(value = "year", required = false)String year){
        List<GradeVo> list = gradeService.getAllGradeVo(year);
        return ApiResp.success(list);
    }
}
