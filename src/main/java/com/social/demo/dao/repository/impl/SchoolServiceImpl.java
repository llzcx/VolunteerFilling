package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.SchoolMapper;
import com.social.demo.dao.repository.ISchoolService;
import com.social.demo.data.dto.SchoolDto;
import com.social.demo.entity.School;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 杨世博
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {
    @Autowired
    SchoolMapper schoolMapper;

    @Override
    public Boolean addSchool(SchoolDto schoolDto) {
        School school = schoolMapper.selectOne(MybatisPlusUtil.queryWrapperEq("number", schoolDto.getNumber(),
                "name", schoolDto.getName()));
        int insert = 0;
        if (school != null){
            throw new SystemException(ResultCode.SCHOOL_ALREADY_EXISTS);
        } else {
            insert = schoolMapper.insert(new School(Long.valueOf(schoolDto.getNumber()), schoolDto.getName()));
        }

        return insert > 0;
    }

    @Override
    public Boolean modifySchool(School school) {
        int update = schoolMapper.update(new School(school.getNumber(), school.getName()),
                MybatisPlusUtil.queryWrapperEq("school_id", school.getSchoolId()));
        return update > 0;
    }

    @Override
    public Boolean deleteSchool(Long schoolId) {
        int delete = schoolMapper.delete(MybatisPlusUtil.queryWrapperEq("school_id", schoolId));
        return delete > 0;
    }

    @Override
    public List<School> getSchool(String schoolName) {
        QueryWrapper<School> schoolQueryWrapper = new QueryWrapper<>();
        schoolQueryWrapper.like(schoolName != null,"name", schoolName);
        return schoolMapper.selectList(schoolQueryWrapper);
    }

    @Override
    public Boolean judgeSchoolName(String schoolName) {
        QueryWrapper<School> schoolQueryWrapper = new QueryWrapper<>();
        schoolQueryWrapper.eq(schoolName != null,"name", schoolName);
        List<School> schools = schoolMapper.selectList(schoolQueryWrapper);
        if (!schools.isEmpty()){
            throw new SystemException(ResultCode.IS_EXISTS);
        }else {
            return true;
        }
    }
}
