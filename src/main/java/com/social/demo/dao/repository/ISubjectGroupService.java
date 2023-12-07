package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.Area;
import com.social.demo.entity.SubjectGroup;

import javax.security.auth.Subject;
import java.util.List;


/**
 * @author 周威宇
 */
public interface ISubjectGroupService extends IService<SubjectGroup> {
    /**
     * 添加学科组合
     * @param subjectGroup
     * @return
     */
    Boolean addSubjectGroup(SubjectGroup subjectGroup);

    /**
     * 查询所有学科组合
     * @return
     */
    List<SubjectGroup> getSubjectGroup();
    /**
     * 修改学科组合
     * @param subjectGroup
     * @return
     */
    Boolean modifySubjectGroup(SubjectGroup subjectGroup);

}