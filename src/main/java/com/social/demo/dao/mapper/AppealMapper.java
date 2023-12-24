package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.Appeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppealMapper extends BaseMapper<Appeal> {
    List<Appeal> selectAppealPage(Long classId, String username, String userNumber, Integer state, Integer number1, Integer number2);

    Integer selectAppealCount(Long classId, String username, String userNumber, Integer state);

    List<Appeal> selectStudentAppealPage(String userNumber, Integer state, Integer number1, Integer number2);

    Integer selectStudentAppealCount(String userNumber, Integer state);

    Long selectUserId(Long appealId);

    Long selectClassId(Long appealId);
}
