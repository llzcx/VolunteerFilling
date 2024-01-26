package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.AppealMapper;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.data.dto.AppealDto;
import com.social.demo.data.vo.AppealVo;
import com.social.demo.entity.Appeal;
import com.social.demo.entity.User;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/22 15:43
 * @description AppealServiceImpl
 */
@Service
public class AppealServiceImpl extends ServiceImpl<AppealMapper, Appeal> implements IAppealService {

    @Autowired
    AppealMapper appealMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<AppealVo> getAppealsToStudent(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        List<Appeal> appeals = appealMapper.selectStudentAppeal(userId);
        List<AppealVo> appealVos = new ArrayList<>();
        for (Appeal appeal : appeals) {
            User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", appeal.getUserId()));
            AppealVo appealVo = new AppealVo();
            BeanUtils.copyProperties(appeal, appealVo);
            appealVo.setUsername(user.getUsername());
            appealVo.setUserNumber(user.getUserNumber());
            appealVos.add(appealVo);
        }
        return appealVos;
    }

    @Override
    public void submitAppeal(HttpServletRequest request, AppealDto appeal) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = studentMapper.selectClassIdByUserId(userId);
        Appeal newAppeal = new Appeal(userId, classId, appeal.getContent(), TimeUtil.now(),
                PropertiesConstant.APPEAL_STATE_PENDING, TimeUtil.now());
        newAppeal.setType(appeal.getType());
        int insert = appealMapper.insert(newAppeal);
    }

    @Override
    public Boolean quashAppeal(HttpServletRequest request, Long appealId) {
        Long myUserId = jwtUtil.getUserId(request);
        Long userId = appealMapper.selectUserId(appealId);
        if (!myUserId.equals(userId)){
            return false;
        }
        Appeal appeal = new Appeal();
        appeal.setState(PropertiesConstant.APPEAL_STATE_CANCELED);
        appeal.setLastDdlTime(TimeUtil.now());
        appealMapper.update(appeal, MybatisPlusUtil.queryWrapperEq("appeal_id", appealId));
        return true;
    }

    @Override
    public Boolean disposeAppeal(HttpServletRequest request, Long appealId) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        return disposeAppeal(classId, appealId);
    }

    @Override
    public Boolean disposeAppealByTeam(HttpServletRequest request, Long appealId) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = studentMapper.selectClassIdByUserId(userId);
        return disposeAppeal(classId, appealId);
    }

    @Override
    public Boolean deleteAppeals(HttpServletRequest request, Long[] appealIds) {
        Long userId = jwtUtil.getUserId(request);
        String userNumber = userMapper.selectUserNumberByUserId(userId);
        for (Long appealId : appealIds) {
            int flag = appealMapper.selectAppealWithAppealId(userNumber, appealId);
            if (flag <= 0){
                return false;
            }
        }
        for (Long appealId : appealIds) {
            appealMapper.deleteAppeal(userNumber, appealId);
        }
        return true;
    }

    @Override
    public Boolean deleteAppealsByTeacher(HttpServletRequest request, Long[] appealIds) {
        Long classId = classMapper.selectClassIdByTeacherUserId(jwtUtil.getUserId(request));
        for (Long appealId : appealIds) {
            int flag = appealMapper.selectAppealsByClassId(classId, appealId);
            if (flag <= 0){
                return false;
            }
        }
        for (Long appealId : appealIds) {
            appealMapper.deleteAppealByClassId(classId, appealId);
        }
        return true;
    }

    @Override
    public List<AppealVo> getAppealByTeacher(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        return getAppeals(classId, true);
    }

    @Override
    public List<AppealVo> getAppealByTeam(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        List<Appeal> appeals = appealMapper.selectTeamAppeals(userId, false);
        List<AppealVo> list = new ArrayList<>();
        for (Appeal appeal : appeals) {
            User user = userMapper.selectById(appeal.getUserId());
            AppealVo appealVo = new AppealVo();
            BeanUtils.copyProperties(appeal, appealVo);
            appealVo.setUserNumber(user.getUserNumber());
            appealVo.setUsername(user.getUsername());
            list.add(appealVo);
        }
        return list;
    }

    @Override
    public Boolean deleteAppealsByTeam(HttpServletRequest request, Long[] appealIds) {
        Long classId = studentMapper.selectClassIdByUserId(jwtUtil.getUserId(request));
        for (Long appealId : appealIds) {
            int flag = appealMapper.selectAppealsByClassId(classId, appealId);
            if (flag <= 0){
                return false;
            }
        }
        for (Long appealId : appealIds) {
            appealMapper.deleteAppealByClassId(classId, appealId);
        }
        return true;
    }

    /**
     * 获取学生申述
     * @param classId 班级
     * @param type 申述类型 0-综测 1-志愿
     * @return
     */
    private List<AppealVo> getAppeals(Long classId, Boolean type){
        List<Appeal> appeals = appealMapper.selectClassAppeals(classId, type);
        List<AppealVo> list = new ArrayList<>();
        for (Appeal appeal : appeals) {
            User user = userMapper.selectById(appeal.getUserId());
            AppealVo appealVo = new AppealVo();
            BeanUtils.copyProperties(appeal, appealVo);
            appealVo.setUserNumber(user.getUserNumber());
            appealVo.setUsername(user.getUsername());
            list.add(appealVo);
        }
        return list;
    }

    private Boolean disposeAppeal(Long classId, Long appealId){
        if(!appealMapper.selectClassId(appealId).equals(classId)){
            return false;
        }
        Appeal appeal = new Appeal();
        appeal.setState(PropertiesConstant.APPEAL_STATE_PROCESSED);
        appeal.setLastDdlTime(TimeUtil.now());
        appealMapper.update(appeal, MybatisPlusUtil.queryWrapperEq("appeal_id", appealId));
        return true;
    }
}
