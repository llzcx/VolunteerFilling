package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IAppraisalTeamService;
import com.social.demo.entity.AppraisalTeam;
import com.social.demo.entity.User;
import com.social.demo.manager.security.authentication.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 杨世博
 * @date 2024/1/23 21:40
 * @description AppraisalTeamServiceImpl
 */
@Service
public class AppraisalTeamServiceImpl extends ServiceImpl<AppraisalTeamMapper, AppraisalTeam> implements IAppraisalTeamService {

    @Autowired
    AppraisalTeamMapper appraisalTeamMapper;

    @Autowired
    AppraisalTeamUserMapper appraisalTeamUserMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public void addAppraisalTeam(HttpServletRequest request, String userNumber) {
        Long userId = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        String userName = userMapper.selectUserNameByUserNumber(userNumber);
        User user = new User();
        user.setUserNumber(classId + userNumber);
        user.setUsername(userName);
        user.setPassword(PropertiesConstant.PASSWORD);
        user.setIdentity(PropertiesConstant.IDENTITY_APPRAISAL_TEAM);
        userMapper.insert(user);

        AppraisalTeam appraisalTeam = new AppraisalTeam();
        appraisalTeam.setClassId(classId);
        appraisalTeam.setTeamUserId(user.getUserId());
        appraisalTeamMapper.insert(appraisalTeam);
    }
}
