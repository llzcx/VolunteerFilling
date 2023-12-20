package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.JsonUtil;
import com.social.demo.dao.mapper.SubjectGroupMapper;
import com.social.demo.dao.repository.ISubjectGroupService;
import com.social.demo.entity.SubjectGroup;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
     *添加科目组合
     */
    @Override
    public  Boolean addSubjectGroup(List<String>subjectScope,Integer subjectNumber){
        boolean insertSuccess;
        List<String> subjectGroup = subjectGroupMapper.selectSubjectGroups();
        List<List<String>> permutations = new ArrayList<>();
        Collections.sort(subjectScope);
        backtrack(subjectScope,subjectNumber,0, new ArrayList<>(), permutations);
        for (List<String> jsonList : permutations) {
            int hashCode = 0;
            for (String string : jsonList) {
                hashCode += string.hashCode();
            }
            String json = JsonUtil.object2StringSlice(jsonList);
            boolean contains = subjectGroup.contains(json);
            if (!contains) {
                SubjectGroup subjectGroup1 = new SubjectGroup();
                subjectGroup1.setHashcode(hashCode);
                subjectGroup1.setSubjects(json);
                subjectGroupMapper.insert(subjectGroup1);
            }
        }
        return true;
    }

    /**
     * 查询全部科目组合
     * @return
     */
    @Override
    public List<SubjectGroup> getSubjectGroup(){
        QueryWrapper<SubjectGroup> queryWrapper = new QueryWrapper<>();
        return subjectGroupMapper.selectList(queryWrapper);
    }

    /**
     * 修改科目组合
     * @param subjectGroup
     * @return
     */
    @Override
    public Boolean modifySubjectGroup(SubjectGroup subjectGroup){
        int update = subjectGroupMapper.update( subjectGroup,
                MybatisPlusUtil.queryWrapperEq("group_id",subjectGroup.getGroupId()));
        return update > 0;
    }
    private static void backtrack(List<String> list, int num, int start, List<String> current, List<List<String>> permutations) {
        if (current.size() == num) {
            permutations.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < list.size(); i++) {
            current.add(list.get(i));
            backtrack(list, num, i + 1, current, permutations);
            current.remove(current.size() - 1);
        }
    }
}