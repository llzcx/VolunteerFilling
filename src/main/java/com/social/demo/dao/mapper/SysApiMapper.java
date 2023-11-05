package com.social.demo.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.SysApi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 陈翔
 */
@Mapper
public interface SysApiMapper extends BaseMapper<SysApi> {
    /**
     * 查询系统中所有Api以及对应的身份列表
     * @return
     */
    List<SysApi> selectApisWithRoles();
}