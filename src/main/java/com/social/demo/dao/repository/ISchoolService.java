package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.SchoolDto;
import com.social.demo.entity.School;

/**
 * @author 杨世博
 */
public interface ISchoolService extends IService<School> {
    /**
     * 添加院校
     * @param schoolDto 院校信息
     * @return
     */
    Boolean addSchool(SchoolDto schoolDto);

    /**
     * 修改院校信息
     * @param schoolDto 院校信息
     * @return
     */
    Boolean modifySchool(SchoolDto schoolDto);

    /**
     * 删除院校
     * @param number 院校编码
     * @return
     */
    Boolean deleteSchool(Long number);

    /**
     * 搜索院校
     * @param schoolName 院校名
     * @return 院校信息
     */
    School getSchool(String schoolName);
}
