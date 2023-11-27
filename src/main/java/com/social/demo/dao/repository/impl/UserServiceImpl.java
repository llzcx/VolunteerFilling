package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.SchoolMapper;
import com.social.demo.dao.mapper.SubjectMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.LoginDto;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.dto.UserDtoByTeacher;
import com.social.demo.data.vo.TeacherVo;
import com.social.demo.data.vo.StudentVo;
import com.social.demo.entity.Class;
import com.social.demo.entity.School;
import com.social.demo.entity.Subject;
import com.social.demo.entity.User;
import com.social.demo.manager.security.authentication.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈翔
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SchoolMapper schoolMapper;

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public Boolean loginOut(HttpServletRequest request) {

        return null;
    }

    @Override
    public TokenPair login(LoginDto loginDto) {
        //md5
        loginDto.setPassword(DigestUtil.md5Hex(loginDto.getPassword()));
        final List<User> users = userMapper.selectByMap(MybatisPlusUtil.getMap("student_number", loginDto.getStudentNumber()
                , "password", loginDto.getPassword()));
        if(users.size()>1){
            throw new SystemException(ResultCode.DATABASE_DATA_EXCEPTION);
        }else if(users.size()==0){
            return null;
        }else{
            final User user = users.get(0);
            return jwtUtil.createTokenAndSaveToKy(user.getUsername());
        }
    }

    @Override
    public StudentVo getInformationOfStudent(HttpServletRequest request) {
        return null;
    }

    @Override
    public StudentVo modifyInformation(HttpServletRequest request, UserDtoByStudent userDtoByStudent) {
        return null;
    }

    @Override
    public StudentVo getStudent(Long number) {
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_number", number));
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(user, studentVo);
        School school = schoolMapper.selectOne(MybatisPlusUtil.queryWrapperEq("number", user.getSchoolNumber()));
        List<Subject> subjects = subjectMapper.selectSubjects(user.getChooseId());
        Class aClass = classMapper.selectOne(MybatisPlusUtil.queryWrapperEq("class_id", user.getClassId()));
        studentVo.setSchool(school.getName());
        studentVo.setSubjects(subjects);
        studentVo.setClassName(aClass.getClassName());
        studentVo.setSex(user.getSex() ? "男" : "女");
        return studentVo;
    }

    @Override
    public StudentVo modifyStudent(UserDtoByTeacher userDtoByTeacher) {



        return null;
    }

    @Override
    public Boolean reset(Long id) {
        int reset = userMapper.update(null, MybatisPlusUtil.queryWrapperEq("password", "12345"));
        return reset > 0;
    }

    @Override
    public StudentVo getInformationOfTeacher(HttpServletRequest request) {
        return null;
    }

    @Override
    public TeacherVo modifyPhone(HttpServletRequest request, String phone) {
        return null;
    }
}
