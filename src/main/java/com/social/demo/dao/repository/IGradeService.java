package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.data.dto.GradeDto;
import com.social.demo.data.dto.GradeSubjectDto;
import com.social.demo.data.vo.GradeVo;
import com.social.demo.entity.GradeSubject;

import java.util.List;

public interface IGradeService{
    List<GradeSubject> getGradeSubject();

    void addGradeSubject(GradeSubjectDto gradeSubjectDto);

    Boolean deleteGradeSubject(Long gradeSubjectIds);

    Boolean modifyGradeSubject(GradeSubject gradeSubject);

    Boolean uploadGrades(GradeDto[] gradeDto);

    IPage<GradeVo> getGrades(String year, String keyword, Integer current, Integer size);

    void deleteGrades(String[] userNumbers);

    void modifyGrade(GradeDto gradeDto);

    List<GradeVo> getAllGradeVo(String year);
}
