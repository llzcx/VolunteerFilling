package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 杨世博
 * @date 2023/12/12 20:09
 * @description StudentMapper
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    Long selectClassIdByUserNumber(String number);

    Long[] getUserIdByClassId(String classId);
}
