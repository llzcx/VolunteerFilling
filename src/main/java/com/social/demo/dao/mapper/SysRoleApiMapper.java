package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.SysApi;
import com.social.demo.entity.SysRoleApi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author 陈翔
 */
@Mapper
public interface SysRoleApiMapper extends BaseMapper<SysRoleApi> {
    // 这里可以自定义需要的方法
    List<Integer> selectAllRoleBYApiId(Long apiId);

    List<SysApi> apiList(Integer roleId);
}