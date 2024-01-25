package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.Appeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppealMapper extends BaseMapper<Appeal> {

    Integer selectAppealCount(Long classId);

    List<Appeal> selectStudentAppeal(Long userId);

    Integer selectStudentAppealCount(String userNumber, Integer state);

    Long selectUserId(Long appealId);

    Long selectClassId(Long appealId);

    List<Appeal> selectClassAppeals(Long classId, Boolean type);

    int deleteAppeal(String userNumber, Long appealId);

    int selectAppealWithAppealId(String userNumber, Long appealId);

    int selectAppealsByClassId(Long classId, Long appealId);

    void deleteAppealByClassId(Long classId, Long appealId);

    List<Appeal> selectTeamAppeals(Long userId, Boolean type);
}
