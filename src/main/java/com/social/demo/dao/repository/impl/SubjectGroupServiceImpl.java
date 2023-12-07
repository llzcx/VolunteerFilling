package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.MajorMapper;
import com.social.demo.dao.mapper.SubjectGroupMapper;
import com.social.demo.dao.repository.ISubjectGroupService;
import com.social.demo.entity.Major;
import com.social.demo.entity.SubjectGroup;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author 周威宇
 */
@Service
public class SubjectGroupServiceImpl extends ServiceImpl<SubjectGroupMapper, SubjectGroup> implements ISubjectGroupService {
    @Autowired
    SubjectGroupMapper subjectGroupMapper;
    /**
     *添加专业
     */
    @Override
    public  Boolean addSubjectGroup(SubjectGroup subjectGroup){
        boolean insertSuccess;
        int insertResult = subjectGroupMapper.insert(subjectGroup);
        insertSuccess = insertResult > 0;
        return insertSuccess;
    }

    /**
     * 查询全部专业
     * @return
     */
    @Override
    public List<SubjectGroup> getSubjectGroup(){
        QueryWrapper<SubjectGroup> queryWrapper = new QueryWrapper<>();
        return subjectGroupMapper.selectList(queryWrapper);
    }

    /**
     * 修改专业
     * @param subjectGroup
     * @return
     */
    @Override
    public Boolean modifySubjectGroup(SubjectGroup subjectGroup){
        int update = subjectGroupMapper.update( subjectGroup,
                MybatisPlusUtil.queryWrapperEq("group_id",subjectGroup.getGroupId()));
        return update > 0;
    }

}