package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.RoleApi;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author 陈翔
 */
@Mapper
public interface UserApiMapper extends BaseMapper<RoleApi> {
    // 这里可以自定义需要的方法
}