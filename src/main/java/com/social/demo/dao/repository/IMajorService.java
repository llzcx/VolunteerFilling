package com.social.demo.dao.repository;

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
     * @param major
     * @return
     */
    Boolean addMajor(Major major);

    /**
     * 查询所有专业
     * @return
     */
    List<Major> getMajors();

    /**
     * 修改专业
     * @param major
     * @return
     */
    Boolean modifyMajor(Major major);
}