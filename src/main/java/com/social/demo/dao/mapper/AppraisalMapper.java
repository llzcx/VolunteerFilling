package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.Appraisal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppraisalMapper extends BaseMapper<Appraisal> {
    Integer selectLastMonthScore(String userNumber, Integer month);

    List<String> selectUserNumbers(Long classId, String name, String userNumber, Integer month, Integer rank, Integer number1, Integer number2);

    Integer selectTotal(Long classId, String name, String userNumber, Integer month, Integer rank);


    Appraisal selectAppraisalByUserNumber(String userNumber, Integer month);
}
