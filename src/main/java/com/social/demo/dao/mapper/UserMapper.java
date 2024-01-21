package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.data.bo.StudentBo;
import com.social.demo.data.vo.ClassUserVo;
import com.social.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    Long selectUserIdByUserNumber(@Param("userNumber") String studentNumber);

    /**
     * 工具目标学校和入学时间获取学生信息
     * @param school
     * @param time
     * @return
     */
    List<User> selectUserBySchoolAndTime(String school, Integer time);

    /**
     * 查找不是班主任的老师
     * @return
     */
    List<User> selectTeacherNotClass();

    String selectUserNameByUserNumber(String number);

    List<StudentBo> selectClassStudents(Long classId, Integer number1, Integer number2);

    Integer selectClassStudentCount(Long classId);

    String selectHeadshot(String userNumber);

    List<ClassUserVo> selectClassUserNumbers(Long classId, String userNumber, String username, String role, Integer rank, Integer month, Integer number1, Integer number2);

    void updateClassIdentity(String userNumber, Integer identity);
}
