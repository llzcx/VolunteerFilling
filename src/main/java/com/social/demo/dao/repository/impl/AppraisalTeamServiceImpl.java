package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IAppraisalTeamService;
import com.social.demo.data.dto.AppraisalTeamDto;
import com.social.demo.data.vo.AppraisalTeamVo;
import com.social.demo.entity.AppraisalTeam;
import com.social.demo.entity.AppraisalTeamUser;
import com.social.demo.entity.User;
import com.social.demo.manager.security.authentication.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Boolean addAppraisalTeam(HttpServletRequest request, String[] userNumbers) {
        Long userId = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        for (String userNumber : userNumbers) {
            String userName = userMapper.selectUserNameByUserNumber(userNumber);
            User user = new User();
            user.setUserNumber(classId + userNumber);
            user.setUsername(userName);
            user.setPassword(MD5.create().digestHex(PropertiesConstant.PASSWORD));
            user.setIdentity(PropertiesConstant.IDENTITY_APPRAISAL_TEAM);
            Long id = userMapper.selectUserIdByUserNumber(user.getUserNumber());
            if (id != null){
                return false;
            }
            userMapper.insert(user);
            AppraisalTeam appraisalTeam = new AppraisalTeam();
            appraisalTeam.setClassId(classId);
            appraisalTeam.setTeamUserId(user.getUserId());
            appraisalTeamMapper.insert(appraisalTeam);
        }
        return true;
    }

    @Override
    public List<AppraisalTeamVo> getAppraisalTeam(HttpServletRequest request) {
        Long userId = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        List<AppraisalTeamVo> list = appraisalTeamMapper.selectTeamList(classId);
        for (AppraisalTeamVo appraisalTeamVo : list) {
            appraisalTeamVo.setAppraisalTeamMemberVos(appraisalTeamUserMapper.selectClassMember(appraisalTeamVo.getUserId()));
        }
        return list;
    }

    @Override
    public void resetAppraisalTeamPwd(String userNumber) {
        User user = new User();
        user.setPassword(MD5.create().digestHex(PropertiesConstant.PASSWORD));
        userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
    }

    @Override
    public Boolean averageClassMember(HttpServletRequest request) {
        Long userId = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        List<AppraisalTeam> teamUsers = getTeamUserId(classId);
        for (AppraisalTeam teamUser : teamUsers) {
            List<AppraisalTeamUser> list = appraisalTeamUserMapper.selectList(MybatisPlusUtil.queryWrapperEq("team_user_id", teamUser.getTeamUserId()));
            if (!list.isEmpty()){
                return false;
            }
        }
        List<Long> userIds = studentMapper.getUserIdByClassId(classId);
        int classNumber = userIds.size();
        int teamNumber = teamUsers.size();
        int index = 0;
        int average = classNumber / teamNumber;

        for (int i = 0; i < classNumber; i++) {
            AppraisalTeamUser appraisalTeamUser = new AppraisalTeamUser();
            appraisalTeamUser.setClassUserId(userIds.get(i));
            appraisalTeamUser.setTeamUserId(teamUsers.get(index).getTeamUserId());
            appraisalTeamUserMapper.insert(appraisalTeamUser);
            if (i >= average && i % average == 0 && index < teamNumber - 1){
                index ++;
            }
        }
        return true;
    }

    @Override
    public void revocationMember(HttpServletRequest request) {
        Long userId = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        List<Long> longs = appraisalTeamMapper.selectTeamUserId(classId);
        for (Long aLong : longs) {
            appraisalTeamUserMapper.delete(MybatisPlusUtil.queryWrapperEq("team_user_id", aLong));
        }
    }

    @Override
    public Boolean revocationTeam(HttpServletRequest request, String[] userNumbers) {
        for (String userNumber : userNumbers) {
            int flag = appraisalTeamUserMapper.selectHaveTeamMember(userNumber);
            if (flag > 0) {
                return false;
            }
        }
        for (String userNumber : userNumbers) {
            appraisalTeamMapper.deleteTeamUser(userNumber);
            userMapper.delete(MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        }
        return true;
    }

    @Override
    public Boolean allocationClassMember(AppraisalTeamDto appraisalTeamDto) {
        Long teamUserId = appraisalTeamDto.getUserId();
        for (Long teamMemberUserId : appraisalTeamDto.getTeamMemberUserIds()) {
            AppraisalTeamUser appraisalTeamUser = appraisalTeamUserMapper.selectOne(MybatisPlusUtil.queryWrapperEq("class_user_id", teamMemberUserId));
            if (appraisalTeamUser == null){
                appraisalTeamUser = new AppraisalTeamUser(teamUserId, teamMemberUserId);
                appraisalTeamUserMapper.insert(appraisalTeamUser);
            }else {
                appraisalTeamUser.setTeamUserId(teamUserId);
                appraisalTeamUserMapper.update(appraisalTeamUser, MybatisPlusUtil.queryWrapperEq("class_user_id", teamMemberUserId));
            }
        }
        return true;
    }

    /**
     * 获取综测小组成员userId
     * @param classId 班级
     * @return 成员userId
     */
    private List<AppraisalTeam> getTeamUserId(Long classId){
        return appraisalTeamMapper.selectList(MybatisPlusUtil.queryWrapperEq("class_id", classId));
    }
}
