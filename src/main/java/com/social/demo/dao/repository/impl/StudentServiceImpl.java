package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.SchoolMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.repository.IStudentService;
import com.social.demo.entity.Student;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 杨世博
 * @date 2023/12/26 19:36
 * @description StudentServiceImpl
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService{

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    SchoolMapper schoolMapper;

    @Override
    public Boolean modifyState(Long schoolId, Integer year, Integer state) {
        String schoolNumber = schoolMapper.selectNameBySchoolId(schoolId);
        if (schoolNumber == null){
            throw new SystemException(ResultCode.SCHOOL_NOT_EXISTS);
        }
        Student student = new Student();
        student.setState(state);
        int update = studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("school_id", schoolId, "enrollment_year", year));
        return update > 0;
    }
}
