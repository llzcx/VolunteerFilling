package com.social.demo.dao.repository.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.GradeSubjectMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IGradeService;
import com.social.demo.data.bo.GradeBo;
import com.social.demo.data.bo.GradeSubjectBo;
import com.social.demo.data.dto.GradeDto;
import com.social.demo.data.dto.GradeSubjectDto;
import com.social.demo.data.vo.GradeVo;
import com.social.demo.entity.GradeSubject;
import com.social.demo.entity.Student;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author 杨世博
 * @date 2024/1/22 20:11
 * @description GradeServiceImpl
 */
@Service
public class GradeServiceImpl implements IGradeService {
    @Autowired
    GradeSubjectMapper gradeSubjectMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<GradeSubject> getGradeSubject() {
        return gradeSubjectMapper.selectList(MybatisPlusUtil.queryWrapperEq());
    }

    @Override
    public void addGradeSubject(GradeSubjectDto gradeSubjectDto) {
        GradeSubject gradeSubject = new GradeSubject();
        BeanUtils.copyProperties(gradeSubjectDto, gradeSubject);
        gradeSubjectMapper.insert(gradeSubject);
    }

    @Override
    public Boolean deleteGradeSubject(Long gradeSubjectId) {
        if (gradeSubjectId.equals(PropertiesConstant.SUBJECT_CHINESE)
                || gradeSubjectId.equals(PropertiesConstant.SUBJECT_MATH)
                || gradeSubjectId.equals(PropertiesConstant.SUBJECT_ENGLISH)){
            return false;
        }
        gradeSubjectMapper.delete(MybatisPlusUtil.queryWrapperEq("grade_id", gradeSubjectId));
        return true;
    }

    @Override
    public Boolean modifyGradeSubject(GradeSubject gradeSubject) {
        Long gradeSubjectId = gradeSubject.getGradeId();
        if (gradeSubjectId.equals(PropertiesConstant.SUBJECT_CHINESE)
                || gradeSubjectId.equals(PropertiesConstant.SUBJECT_MATH)
                || gradeSubjectId.equals(PropertiesConstant.SUBJECT_ENGLISH)){
            return false;
        }
        gradeSubjectMapper.update(gradeSubject, MybatisPlusUtil.queryWrapperEq("grade_id", gradeSubject.getGradeId()));
        return true;
    }

    @Override
    public Boolean uploadGrades(GradeDto[] gradeDto) {
        HashMap<String, Long> idMap = new HashMap<>();
        for (GradeDto dto : gradeDto) {
            String userNumber = dto.getUserNumber();
            Long userId = userMapper.selectUserIdByUserNumber(userNumber);
            if (userId == null){
                return false;
            }
            idMap.put(userNumber, userId);
        }
        for (GradeDto dto : gradeDto) {
            Long userId = idMap.get(dto.getUserNumber());
            String jsonStr = JSONUtil.toJsonStr(dto.getGradeSubjectBos());
            Student student = new Student();
            student.setGrade(jsonStr);
            student.setScore(dto.getScore());
            studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        }
        return true;
    }

    @Override
    public IPage<GradeVo> getGrades(String year, String keyword, Integer current, Integer size) {
        int newYear = 0;
        if (year==null || year.isEmpty()){
            newYear = TimeUtil.now().getYear();
        }else {
            newYear = Integer.parseInt(year);
        }
        List<GradeBo> gradeBoList = studentMapper.getGrades(newYear, keyword, (current - 1) * size, size);
        Integer number = studentMapper.getGradesCount(newYear, keyword);
        List<GradeVo> list = new ArrayList<>();
        for (GradeBo gradeBo : gradeBoList) {
            GradeVo gradeVo = new GradeVo();
            BeanUtils.copyProperties(gradeBo, gradeVo);
            gradeVo.setGradeSubjectBos(JSONUtil.toList(gradeBo.getGrade(), GradeSubjectBo.class));
            list.add(gradeVo);
        }
        IPage<GradeVo> gradeVoIPage = new Page<>(current, size);
        gradeVoIPage.setTotal(number);
        gradeVoIPage.setRecords(list);
        return gradeVoIPage;
    }

    @Override
    public void deleteGrades(String[] userNumbers) {
        Student student = new Student();
        List<GradeSubject> gradeSubjects = gradeSubjectMapper.selectList(MybatisPlusUtil.queryWrapperEq());
        List<GradeSubjectBo> gradeSubjectBos = new ArrayList<>();
        for (GradeSubject gradeSubject : gradeSubjects) {
            GradeSubjectBo gradeSubjectBo = new GradeSubjectBo();
            gradeSubjectBo.setGradeId(gradeSubject.getGradeId());
            gradeSubjectBo.setGrade(0.00);
            gradeSubjectBos.add(gradeSubjectBo);
        }
        student.setScore(0.00);
        student.setGrade(JSONUtil.toJsonStr(gradeSubjectBos));
        for (String userNumber : userNumbers) {
            studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userMapper.selectUserIdByUserNumber(userNumber)));
        }
    }

    @Override
    public void modifyGrade(GradeDto gradeDto) {
        Student student = new Student();
        student.setGrade(JSONUtil.toJsonStr(gradeDto.getGradeSubjectBos()));
        student.setScore(gradeDto.getScore());
        studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userMapper.selectUserIdByUserNumber(gradeDto.getUserNumber())));
    }

    @Override
    public List<GradeVo> getAllGradeVo(String year) {
        int newYear = 0;
        if (year==null || year.isEmpty()){
            newYear = TimeUtil.now().getYear();
        }else {
            newYear = Integer.parseInt(year);
        }
        List<GradeBo> gradeBoList = studentMapper.getAllGrades(newYear);
        List<GradeVo> list = new ArrayList<>();
        for (GradeBo gradeBo : gradeBoList) {
            GradeVo gradeVo = new GradeVo();
            BeanUtils.copyProperties(gradeBo, gradeVo);
            gradeVo.setGradeSubjectBos(JSONUtil.toList(gradeBo.getGrade(), GradeSubjectBo.class));
            list.add(gradeVo);
        }
        return list;
    }
}
