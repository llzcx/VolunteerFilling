package com.social.demo.dao.repository.impl;

import cn.hutool.json.JSONUtil;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.GradeSubjectMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IGradeService;
import com.social.demo.data.dto.GradeDto;
import com.social.demo.data.dto.GradeSubjectDto;
import com.social.demo.entity.GradeSubject;
import com.social.demo.entity.Student;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        }
        return true;
    }
}
