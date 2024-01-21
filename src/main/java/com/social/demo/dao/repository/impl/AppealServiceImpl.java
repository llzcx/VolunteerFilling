package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.AppealMapper;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IAppealService;
import com.social.demo.data.vo.AppealVo;
import com.social.demo.entity.Appeal;
import com.social.demo.entity.User;
import com.social.demo.manager.security.authentication.JwtUtil;
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
    public IPage<AppealVo> getAppeals(HttpServletRequest request, Integer current, Integer size) {
        String teacherNumber = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherNumber(teacherNumber);
        return getAppealVoPage(classId, current, size);
    }

    @Override
    public IPage<AppealVo> getAppealsToStudent(HttpServletRequest request, Integer state, Integer current, Integer size) {
        String userNumber = jwtUtil.getSubject(request);
        List<Appeal> appeals = appealMapper.selectStudentAppealPage(userNumber, state, (current-1) * size, size);
        List<AppealVo> appealVos = new ArrayList<>();
        for (Appeal appeal : appeals) {
            User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", appeal.getUserId()));
            AppealVo appealVo = new AppealVo();
            BeanUtils.copyProperties(appeal, appealVo);
            appealVo.setUsername(user.getUsername());
            appealVo.setUserNumber(user.getUserNumber());
            appealVos.add(appealVo);
        }

        IPage<AppealVo> appealVoIPage = new Page<>(current, size);
        appealVoIPage.setRecords(appealVos);
        Integer count = appealMapper.selectStudentAppealCount(userNumber, state);
        appealVoIPage.setTotal(count);
        return appealVoIPage;
    }

    @Override
    public void submitAppeal(HttpServletRequest request, String appeal) {
        String userNumber = jwtUtil.getSubject(request);
        Long userId = userMapper.selectUserIdByUserNumber(userNumber);
        Long classId = studentMapper.selectClassIdByUserNumber(userNumber);
        Appeal newAppeal = new Appeal(userId, classId, appeal, TimeUtil.now(),
                PropertiesConstant.APPEAL_STATE_PENDING, TimeUtil.now());
        int insert = appealMapper.insert(newAppeal);
    }

    @Override
    public IPage<AppealVo> getAppealsByTeam(HttpServletRequest request, String username, String userNumber, Integer state, Integer current, Integer size) {
        String number = jwtUtil.getSubject(request);
        Long classId = studentMapper.selectClassIdByUserNumber(number);
        return getAppealVoPage(classId, current, size);
    }

    @Override
    public Boolean quashAppeal(HttpServletRequest request, Long appealId) {
        String userNumber = jwtUtil.getSubject(request);
        Long userId = appealMapper.selectUserId(appealId);
        if (!userMapper.selectUserIdByUserNumber(userNumber).equals(userId)){
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
        String userNumber = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherNumber(userNumber);
        return disposeAppeal(classId, appealId);
    }

    @Override
    public Boolean disposeAppealByTeam(HttpServletRequest request, Long appealId) {
        String userNumber = jwtUtil.getSubject(request);
        Long classId = studentMapper.selectClassIdByUserNumber(userNumber);
        return disposeAppeal(classId, appealId);
    }

    private IPage<AppealVo> getAppealVoPage(Long classId, Integer current, Integer size){
        List<Appeal> appeals = appealMapper.selectAppealPage(classId, (current-1) * size, size);
        List<AppealVo> appealVos = new ArrayList<>();
        for (Appeal appeal : appeals) {
            User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", appeal.getUserId()));
            AppealVo appealVo = new AppealVo();
            BeanUtils.copyProperties(appeal, appealVo);
            appealVo.setUsername(user.getUsername());
            appealVo.setUserNumber(user.getUserNumber());
            appealVos.add(appealVo);
        }

        IPage<AppealVo> appealVoIPage = new Page<>(current, size);
        appealVoIPage.setRecords(appealVos);
        Integer count = appealMapper.selectAppealCount(classId);
        appealVoIPage.setTotal(count);
        return appealVoIPage;
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
