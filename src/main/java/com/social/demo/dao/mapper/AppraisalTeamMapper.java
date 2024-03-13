package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.data.vo.AppraisalTeamVo;
import com.social.demo.entity.AppraisalTeam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppraisalTeamMapper extends BaseMapper<AppraisalTeam> {
    List<AppraisalTeamVo> selectTeamList(@Param("classId") Long classId);

    List<Long> selectTeamUserId(Long classId);

    void deleteTeamUser(String userNumber);

    Long selectClassId(Long userId);
}
