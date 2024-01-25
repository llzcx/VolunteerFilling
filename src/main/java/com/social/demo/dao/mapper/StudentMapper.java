package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.data.bo.GradeBo;
import com.social.demo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.redis.connection.ReactiveSetCommands;

import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/12 20:09
 * @description StudentMapper
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    Long selectClassIdByUserNumber(String number);

    List<Long> getUserIdByClassId(Long classId);

    Long selectClassIdByUserId(Long userId);

    List<GradeBo> getGrades(Integer year, String keyword, Integer number1, Integer number2);

    Integer getGradesCount(Integer year, String keyword);

    void deleteGrades(String userNumber);

    List<GradeBo> getAllGrades(Integer year);
}
