package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author 陈翔
 * @since 2023-07-02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据业务username查询主键id
     * @param username
     * @return
     */
    Long selectUserIdByUserName(@Param("username") String username);

}
