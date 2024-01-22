package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.List;


/**
 * @author 陈翔
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过用户userNumber获取身份列表
     * @param userNumber
     * @return
     */
    List<SysRole> selectRoleListByUserName(@Param("userNumber")String userNumber);


    List<SysRole> selectRoleListByUserId(Long userId);
}