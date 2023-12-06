package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.School;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 杨世博
 */
@Mapper
public interface SchoolMapper extends BaseMapper<School> {
    /**
     * 通过学校名获取学校编号
     * @param schoolName 学校名
     * @return 返回学校编号
     */
    Long selectSchoolNumberByName(String schoolName);

    String selectNameBySchoolNumber(Long schoolNumber);
}
