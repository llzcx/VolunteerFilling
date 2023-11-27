package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

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
     * @param studentNumber
     * @return
     */
    Long selectUserIdByUserName(@Param("userNumber") String studentNumber);

    /**
     * 工具目标学校和入学时间获取学生信息
     * @param school
     * @param time
     * @return
     */
    List<User> selectUserBySchoolAndTime(String school, LocalDateTime time);
}
