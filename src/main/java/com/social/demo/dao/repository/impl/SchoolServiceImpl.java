package com.social.demo.dao.repository.impl;

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
            insert = schoolMapper.insert(new School(schoolDto.getNumber(), schoolDto.getName()));
        }

        return insert > 0;
    }

    @Override
    public Boolean modifySchool(SchoolDto schoolDto) {
        int update = schoolMapper.update(new School(schoolDto.getNumber(), schoolDto.getName()),
                MybatisPlusUtil.queryWrapperEq("number", schoolDto.getNumber()));
        return update > 0;
    }

    @Override
    public Boolean deleteSchool(Long number) {
        int delete = schoolMapper.delete(MybatisPlusUtil.queryWrapperEq("number", number));
        return delete > 0;
    }

    @Override
    public School getSchool(String schoolName) {
        return schoolMapper.selectOne(MybatisPlusUtil.queryWrapperEq("name", schoolName));
    }
}
