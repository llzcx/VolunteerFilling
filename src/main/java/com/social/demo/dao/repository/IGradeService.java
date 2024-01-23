package com.social.demo.dao.repository;

import com.social.demo.data.dto.GradeDto;
import com.social.demo.data.dto.GradeSubjectDto;
import com.social.demo.entity.GradeSubject;

import java.util.List;

public interface IGradeService{
    List<GradeSubject> getGradeSubject();

    void addGradeSubject(GradeSubjectDto gradeSubjectDto);

    Boolean deleteGradeSubject(Long gradeSubjectIds);

    Boolean modifyGradeSubject(GradeSubject gradeSubject);

    Boolean uploadGrades(GradeDto[] gradeDto);
}
