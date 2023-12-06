package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.SubjectGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface SubjectGroupMapper extends BaseMapper<SubjectGroup> {
    Long selectGroupId(String subjects);

    Set<String> selectSubjects(Long groupId);
}
