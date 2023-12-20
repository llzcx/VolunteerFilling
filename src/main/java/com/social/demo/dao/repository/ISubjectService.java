package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.Subject;
import com.social.demo.entity.SysApi;

import java.util.List;


/**
 * @author 周威宇
 */
public interface ISubjectService extends IService<Subject> {
    /**
     * 添加科目
     * @param subject
     * @return
     */
    Boolean addSubject(Subject subject);

    /**
     * 删除科目
     * @param subjectId
     * @return
     */
    Boolean deleteSubject(Long subjectId);

    /**
     * 查询科目
     * @return
     */
    List<Subject> getSubject();
}