package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.Appraisal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppraisalMapper extends BaseMapper<Appraisal> {
    Integer selectLastMonthScore(String userNumber, Integer month);

    List<String> selectUserNumbersToTeacher(Long classId, String keyword, Integer month, Integer rank, Integer number1, Integer number2);

    Integer selectTotal(Long classId, String name, String userNumber, Integer month, Integer rank);


    Appraisal selectAppraisalByUserId(Long userId, Integer month);

    Long selectAppraisalId(String userNumber, Integer month);

    List<String> selectUserNumbersToTeam(Long userId, String keyword, Integer month, Integer rank, Integer number1, Integer number2);

    void updateIsEnd(Long classId, Integer month, Boolean isEnd);

    List<Integer> selectMonths(Long classId);
}
