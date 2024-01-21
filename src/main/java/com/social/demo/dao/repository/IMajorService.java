package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.dao.mapper.MajorMapper;
import com.social.demo.entity.Area;
import com.social.demo.entity.Major;
import com.social.demo.entity.SysApi;

import java.util.List;


/**
 * @author 周威宇
 */
public interface IMajorService extends IService<Major> {
    /**
     * 添加专业
     * @param majors
     * @return
     */
    Boolean addMajor(List<Major> majors);

    /**
     * 查询专业
     * @return
     */
    IPage<Major> getMajors(Long schoolId,String name,Long current, Long size);
    /**
     * 查询一个学校的专业
     */
    IPage<Major> getSchoolMajors(Long schoolId, Long current, Long size);
    /**
     * 查询一个学校的所有专业
     */
    List<Major> getSchoolMajor(Long schoolId);
    /**
     * 查询一个学院的专业
     * @param college
     * @return
     */
    IPage<Major> getCollegeMajors(Long schoolId,String college,Long current, Long size);
    /**
     * 修改专业
     * @param major
     * @return
     */
    Major modifyMajor(Major major);

    /**
     * 删除专业
     * @param majorId
     * @return
     */
    Boolean deleteMajor(Long majorId);


}