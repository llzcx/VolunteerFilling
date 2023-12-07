package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.SchoolMapper;
import com.social.demo.dao.mapper.SubjectGroupMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.bo.LoginBo;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.*;
import com.social.demo.data.vo.ClassTeacherVo;
import com.social.demo.data.vo.TeacherVo;
import com.social.demo.data.vo.StudentVo;
import com.social.demo.data.vo.UserVo;
import com.social.demo.entity.Class;
import com.social.demo.entity.School;
import com.social.demo.entity.User;
import com.social.demo.manager.security.authentication.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
    ClassMapper classMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SubjectGroupMapper subjectGroupMapper;

    @Override
    public void loginOut(HttpServletRequest request) {
        final String token = request.getHeader(jwtUtil.getTokenHeader());
        jwtUtil.addBlacklist(token, jwtUtil.getExpirationData(token));
    }

    @Override
    public LoginBo login(LoginDto loginDto) {
        //md5
        loginDto.setPassword(DigestUtil.md5Hex(loginDto.getPassword()));
        final List<User> users = userMapper.selectByMap(MybatisPlusUtil.getMap("user_number", loginDto.getUserNumber()
                , "password", loginDto.getPassword()));
        if(users.size()>1){
            throw new SystemException(ResultCode.DATABASE_DATA_EXCEPTION);
        }else if(users.isEmpty()){
            return null;
        }else{
            final User user = users.get(0);
            return new LoginBo(jwtUtil.createTokenAndSaveToKy(user.getUserNumber()), "manager");
        }
    }

    @Override
    public StudentVo getInformationOfStudent(HttpServletRequest request) {
        String userNumber = jwtUtil.getSubject(request);
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(user, studentVo);
        studentVo.setSchool(schoolMapper.selectNameBySchoolNumber(user.getSchoolNumber()));
        studentVo.setClassName(classMapper.selectNameByClassId(user.getClassId()));
        studentVo.setSubjects(subjectGroupMapper.selectSubjects(user.getGroupId()));
        return studentVo;
    }

    @Override
    public Boolean modifyInformation(HttpServletRequest request, UserDtoByStudent userDtoByStudent) {
        String userNumber = jwtUtil.getSubject(request);
        User user = new User();
        BeanUtils.copyProperties(userDtoByStudent, user);
        int update = userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        return update > 0;
    }

    @Override
    public StudentVo getStudent(Long number) {
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_number", number));
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(user, studentVo);
        School school = schoolMapper.selectOne(MybatisPlusUtil.queryWrapperEq("number", user.getSchoolNumber()));
        Set<String> subjects = userMapper.selectStudentSubjects(user.getUserId());
        Class aClass = classMapper.selectOne(MybatisPlusUtil.queryWrapperEq("class_id", user.getClassId()));
        studentVo.setSchool(school.getName());
        studentVo.setSubjects(subjects);
        studentVo.setClassName(aClass.getClassName());
        studentVo.setSex(user.getSex());
        return studentVo;
    }

    @Override
    public StudentVo modifyStudent(UserDtoByTeacher userDtoByTeacher) {



        return null;
    }

    @Override
    public Boolean reset(String[] userNumbers) {
        List<String> numbers = new ArrayList<>();
        numbers.addAll(Arrays.asList(userNumbers));
        for (String number : numbers) {
            List<User> users = userMapper.selectList(MybatisPlusUtil.queryWrapperEq("user_number", number));
            if (users.size() > 1){
                throw new SystemException(ResultCode.DATABASE_DATA_EXCEPTION);
            }else if (users.isEmpty()){
                throw new SystemException(ResultCode.USER_NOT_EXISTS);
            }
        }
        User user = new User();
        user.setPassword(DigestUtil.md5Hex(PropertiesConstant.PASSWORD));
        for (String number : numbers) {
            userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_number", number));
        }
        return true;
    }

    @Override
    public TeacherVo getInformationOfTeacher(HttpServletRequest request) {
        TeacherVo teacherVo = new TeacherVo();
        String userNumber = jwtUtil.getSubject(request);
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        BeanUtils.copyProperties(user, teacherVo);
        teacherVo.setClassName(classMapper.selectNameByTeacherNumber(user.getUserId()));
        return teacherVo;
    }

    @Override
    public Boolean modifyPhone(HttpServletRequest request, String phone) {
        String userNumber = jwtUtil.getSubject(request);
        User user = new User();
        user.setPhone(phone);
        int update = userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        return update > 0;
    }

    @Override
    public List<User> getUserBySchoolAndTime(String school, LocalDateTime time) {
        if (school.isEmpty() || school.equals("") || time == null){
            throw new SystemException(ResultCode.NULL_POINT_EXCEPTION);
        }
        return userMapper.selectUserBySchoolAndTime(school, time);
    }

    @Override
    public String importStudents(List<StudentDto> students) {
        List<User> users = new ArrayList<>();
        for (StudentDto student : students) {
            User user = new User();
            BeanUtils.copyProperties(student, user);
            Long classId = classMapper.selectClassIdByName(student.getClassName());
            user.setClassId(classId);
            Long schoolNumber = schoolMapper.selectSchoolNumberByName(student.getSchool());
            user.setSchoolNumber(schoolNumber);
            user.setPassword(DigestUtil.md5Hex(PropertiesConstant.PASSWORD));
            user.setEnrollmentYear(TimeUtil.now().getYear());
            Set<String> strings = new HashSet<>();
            strings.addAll(Arrays.asList(student.getSubjects()));
            Long groupId = subjectGroupMapper.selectGroupId(JSONUtil.toJsonStr(strings));
            user.setGroupId(groupId);
            user.setIdentity(PropertiesConstant.IDENTITY_STUDENT);
            users.add(user);

            if (!JudgeUser(student.getUserNumber())){
                return student.getUserNumber();
            }
        }
        for (User user : users) {
            userMapper.insert(user);

        }
        return null;
    }

//    private List<StudentVo> getStudent(){
//
//        List<StudentVo> studentVos = new ArrayList<>();
//        for (Long userId : userIds) {
//            User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId));
//            StudentVo studentVo = new StudentVo();
//            BeanUtils.copyProperties(user, studentVo);
//            String className = classMapper.selectNameByClassId(user.getClassId());
//            studentVo.setClassName(className);
//            String schoolName = schoolMapper.selectNameBySchoolNumber(user.getSchoolNumber());
//            studentVo.setSchool(schoolName);
//            Set<String> subjects = subjectGroupMapper.selectSubjects(user.getGroupId());
//            studentVo.setSubjects(subjects);
//
//            studentVos.add(studentVo);
//        }
//
//        return studentVos;
//    }

    @Override
    public TokenPair refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader(jwtUtil.getTokenHeader());
        // 刷新令牌 放入黑名单
        jwtUtil.addBlacklist(refreshToken, jwtUtil.getExpirationData(refreshToken));
        // 访问令牌 放入黑名单
        String odlAccessToken = jwtUtil.getAccessTokenByRefresh(refreshToken);
        if (!StringUtils.isEmpty(odlAccessToken)) {
            jwtUtil.addBlacklist(odlAccessToken, jwtUtil.getExpirationData(odlAccessToken));
        }

        jwtUtil.delTokenForRedis(refreshToken);

        // 生成新的 访问令牌 和 刷新令牌
        return jwtUtil.createTokenAndSaveToKy(jwtUtil.getSubject(request));
    }

    @Override
    public String importTeachers(List<TeacherDto> teachers) {
        List<User> users = new ArrayList<>();
        for (TeacherDto teacher : teachers) {
            User user = new User();
            BeanUtils.copyProperties(teacher, user);
            user.setPassword(DigestUtil.md5Hex(PropertiesConstant.PASSWORD));
            user.setIdentity(PropertiesConstant.IDENTITY_TEACHER);
            users.add(user);
            if (!JudgeUser(teacher.getUserNumber())){
                return teacher.getUserNumber();
            }
        }

        for (User user : users) {
            userMapper.insert(user);
        }
        return null;
    }

    private Boolean JudgeUser(String userNumber){
        List<User> users = userMapper.selectList(MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        if (users.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public IPage<UserVo> getUser(String username, String role, Long current, Long size) {
        IPage<UserVo> userVoPage = new Page<>();
        IPage<User> userPage;
        int identity = -1;
        switch (role){
            case "学生" : identity = PropertiesConstant.IDENTITY_STUDENT;break;
            case "老师" : identity = PropertiesConstant.IDENTITY_TEACHER;break;
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(username != null || username.isEmpty(), "username", username).
                eq(identity != -1, "identity", identity);
        userPage = userMapper.selectPage(new Page<>(current, size), userQueryWrapper);

        userVoPage.setCurrent(userPage.getCurrent());
        userVoPage.setTotal(userPage.getTotal());
        userVoPage.setSize(userPage.getSize());
        List<UserVo> userVos = new ArrayList<>();
        for (User user : userPage.getRecords()) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            switch (user.getIdentity()){
                case 0: userVo.setRole("学生"); break;
                case 1: userVo.setRole("老师"); break;
            }
            userVos.add(userVo);
        }
        userVoPage.setRecords(userVos);
        return userVoPage;
    }

    @Override
    public String deleteUser(String[] userNumbers) {
        List<String> numbers = new ArrayList<>();
        numbers.addAll(Arrays.asList(userNumbers));
        for (String number : numbers) {
            List<User> users = userMapper.selectList(MybatisPlusUtil.queryWrapperEq("user_number", number));
            if (users.size() > 1){
                throw new SystemException(ResultCode.DATABASE_DATA_EXCEPTION);
            }else if (users.isEmpty()){
                throw new SystemException(ResultCode.USER_NOT_EXISTS);
            }
            User user = users.get(0);
            if (user.getIdentity() == PropertiesConstant.IDENTITY_TEACHER){
                Class aClass = classMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", user.getUserId()));
                if (aClass != null){
                    return number;
                }
            }
        }
        for (String number : numbers) {
            int userNumber = userMapper.delete(MybatisPlusUtil.queryWrapperEq("user_number", number));
            if (userNumber != 1){
                throw new SystemException(ResultCode.DATABASE_DATA_EXCEPTION);
            }
        }
        return null;
    }

    @Override
    public List<ClassTeacherVo> getTeachers() {
        List<User> users = userMapper.selectTeacherNotClass();
        List<ClassTeacherVo> teacherVos = new ArrayList<>();
        for (User user : users) {
            ClassTeacherVo classTeacherVo = new ClassTeacherVo();
            BeanUtils.copyProperties(user, classTeacherVo);
            teacherVos.add(classTeacherVo);
        }
        return teacherVos;
    }

    @Override
    public Boolean modifyPassword(HttpServletRequest request, String password) {
        String userNumber = jwtUtil.getSubject(request);
        User user = new User();
        user.setPassword(DigestUtil.md5Hex(password));
        int update = userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        return update > 0;
    }
}
