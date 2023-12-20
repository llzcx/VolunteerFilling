package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.MajorMapper;
import com.social.demo.dao.mapper.SubjectMapper;
import com.social.demo.dao.repository.ISubjectService;
import com.social.demo.entity.Area;
import com.social.demo.entity.Major;
import com.social.demo.entity.Subject;
import com.social.demo.entity.User;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author 周威宇
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {
    @Autowired
    SubjectMapper subjectMapper;
    /**
     *添加科目
     */
    @Override
    public  Boolean addSubject(Subject subject){
        Subject subject1 = subjectMapper.selectOne(MybatisPlusUtil.queryWrapperEq("subject_name",subject.getSubjectName()));
        int insert = 0;
        if (subject1 != null){
            throw new SystemException(ResultCode.SUBJECT_HAVE_CLASS);
        } else {
            insert =  subjectMapper.insert(subject);
        }
        return insert > 0;
    }
    /**
     * 删除科目
     */
    @Override
    public Boolean deleteSubject(Long subjectId){
        int delete = subjectMapper.delete(MybatisPlusUtil.queryWrapperEq("subject_id", subjectId));
        return delete > 0;
    }
    /**
     * 查询地区
     * @return
     */
    @Override
    public List<Subject> getSubject(){
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        return subjectMapper.selectList(queryWrapper);
    }
}