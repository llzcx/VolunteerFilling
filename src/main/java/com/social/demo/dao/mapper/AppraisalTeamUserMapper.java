package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.data.vo.AppraisalTeamMemberVo;
import com.social.demo.entity.AppraisalTeamUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppraisalTeamUserMapper extends BaseMapper<AppraisalTeamUser> {
    int selectHaveTeamMember(String userNumber);

    List<AppraisalTeamMemberVo> selectClassMember(Long userId);

    void add(Long teamUserId, Long classUserId);
}
