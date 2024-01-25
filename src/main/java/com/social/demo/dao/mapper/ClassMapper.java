package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.Class;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 杨世博
 */
@Mapper
public interface ClassMapper extends BaseMapper<Class> {
    /**
     * 根据班级名获取班级id
     * @param className 班级名
     * @return 班级id
     */
    Long selectClassIdByName(String className);

    String selectNameByClassId(Long classId);

    Long judge(Long classId, String className);

    String selectClassNameByTeacherUserId(Long userId);

    Long selectClassIdByTeacherNumber(String teacherNumber);

    Long selectClassIdByTeacherUserId(Long userId);
}
