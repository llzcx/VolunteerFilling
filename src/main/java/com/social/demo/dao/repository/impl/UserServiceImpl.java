package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.bo.ConsigneeBo;
import com.social.demo.data.bo.LoginBo;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.*;
import com.social.demo.data.vo.*;
import com.social.demo.entity.*;
import com.social.demo.entity.Class;
import com.social.demo.manager.file.UploadFile;
import com.social.demo.manager.security.context.SecurityContext;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    StudentMapper studentMapper;


    @Autowired
    ConsigneeMapper consigneeMapper;

    @Qualifier("local")
    @Autowired
    UploadFile uploadFile;


    /**
     * 退出登录
     */
    @Override
    public void loginOut() {
        jwtUtil.delTokenForRedis(SecurityContext.get().getUserId());
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
            return new LoginBo(jwtUtil.createTokenAndSaveToKy(user.getUserId()),
                    user.getIdentity().toString());//获取角色 后续需修改
        }
    }

    @Override
    public StudentVo getInformationOfStudent(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        String userNumber = userMapper.selectUserNumberByUserId(userId);
        return getStudent(userNumber);
    }

    @Override
    public Boolean modifyInformation(HttpServletRequest request, UserDtoByStudent userDtoByStudent) {

        Long userId = jwtUtil.getUserId(request);
        User user = new User();
        user.setPhone(userDtoByStudent.getPhone());
        userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        Consignee consignee = new Consignee();
        BeanUtils.copyProperties(userDtoByStudent.getConsigneeBo(), consignee);
        consigneeMapper.update(consignee, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        return true;
    }


    @Override
    public StudentVo getStudent(String userNumber){
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        Student student = studentMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", user.getUserId()));
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(user, studentVo);
        BeanUtils.copyProperties(student, studentVo);
        studentVo.setSubjects(JSONUtil.toList(subjectGroupMapper.selectSubjects(student.getHashcode()).getSubjects(), String.class));
        studentVo.setSchool(schoolMapper.selectNameBySchoolNumber(student.getSchoolNumber()));
        studentVo.setClassName(classMapper.selectNameByClassId(student.getClassId()));
        ConsigneeBo consigneeBo = new ConsigneeBo();
        BeanUtils.copyProperties(consigneeMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", user.getUserId())), consigneeBo);
        studentVo.setConsignee(consigneeBo);
        return studentVo;
    }

    @Override
    public Boolean modifyStudent(UserDtoByTeacher userDtoByTeacher) {
        String userNumber = userDtoByTeacher.getUserNumber();
        userDtoByTeacher.setUserNumber(null);
        User user = new User();
        BeanUtils.copyProperties(userDtoByTeacher, user);
        Student student = new Student();
        BeanUtils.copyProperties(userDtoByTeacher, student);
        user.setUserNumber(null);
        userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userMapper.selectUserIdByUserNumber(userNumber)));
        return true;
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
        Long userId = jwtUtil.getUserId(request);
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId));
        BeanUtils.copyProperties(user, teacherVo);
        String className = classMapper.selectClassNameByTeacherUserId(user.getUserId());
        teacherVo.setClassName(className);
        return teacherVo;
    }

    @Override
    public Boolean modifyPhone(HttpServletRequest request, String phone) {
        Long userId = jwtUtil.getUserId(request);
        User user = new User();
        user.setPhone(phone);
        int update = userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        return update > 0;
    }

    @Override
    public List<User> getUserBySchoolAndTime(String school, Integer time) {
        if (school.isEmpty() || school.equals("") || time == null){
            throw new SystemException(ResultCode.NULL_POINT_EXCEPTION);
        }
        return userMapper.selectUserBySchoolAndTime(school, time);
    }

    @Override
    @Transactional
    public String importStudents(List<StudentDto> studentDtos) {
        List<User> users = new ArrayList<>();
        HashMap<String, Student> map = new HashMap<>();
        for (StudentDto studentDto : studentDtos) {
            User user = new User();
            BeanUtils.copyProperties(studentDto, user);
            users.add(user);
            Student student = new Student();
            BeanUtils.copyProperties(studentDto, student);
            Long classId = classMapper.selectClassIdByName(studentDto.getClassName());
            student.setClassId(classId);
            Long schoolNumber = schoolMapper.selectSchoolNumberByName(studentDto.getSchool());
            student.setSchoolNumber(schoolNumber);
            user.setPassword(DigestUtil.md5Hex(PropertiesConstant.PASSWORD));
            user.setIdentity(IdentityEnum.STUDENT.getRoleId());
            student.setEnrollmentYear(TimeUtil.now().getYear());
            Set<String> strings = new HashSet<>(Arrays.asList(studentDto.getSubjects()));
            student.setHashcode(JSONUtil.toJsonStr(strings).hashCode());
            if (!JudgeUser(studentDto.getUserNumber())){
                return studentDto.getUserNumber();
            }
            map.put(user.getUserNumber(), student);
        }
//        SysRole sysRole = sysRoleMapper.selectOne(MybatisPlusUtil.queryWrapperEq("role_name", PropertiesConstant.IDENTITY_STUDENT));
        for (User user : users) {
            userMapper.insert(user);
//            sysUserRoleMapper.insert(new SysUserRole(user.getClassUserId(), sysRole.getId()));
            Student student = map.get(user.getUserNumber());
            student.setUserId(user.getUserId());
            studentMapper.insert(student);
            Consignee consignee = new Consignee();
            consignee.setUserId(user.getUserId());
            consigneeMapper.insert(consignee);
        }
        return null;
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        DecodedJWT claimsByToken = jwtUtil.getClaimsByToken(refreshToken);
        if(claimsByToken == null){
            throw new SystemException(ResultCode.TOKEN_DECODE_ERROR);
        }
        Long userId = jwtUtil.getUserId(claimsByToken);
        TokenPair tokenForRedis = jwtUtil.getTokenForRedis(userId);
        if(tokenForRedis.getRefreshToken() == null){
            throw new SystemException(ResultCode.TOKEN_LOST_IN_DATABASE);
        }else if(!tokenForRedis.getRefreshToken().equals(refreshToken)){
            throw new SystemException(ResultCode.OLD_TOKEN);
        }else{
            jwtUtil.delTokenForRedis(userId);
            // 生成新的 访问令牌 和 刷新令牌
            return jwtUtil.createTokenAndSaveToKy(userId);
        }
    }

    @Override
    public String importTeachers(List<TeacherDto> teachers) {
        List<User> users = new ArrayList<>();
        for (TeacherDto teacher : teachers) {
            User user = new User();
            BeanUtils.copyProperties(teacher, user);
            user.setIdentity(IdentityEnum.TEACHER.getRoleId());
            user.setPassword(DigestUtil.md5Hex(PropertiesConstant.PASSWORD));
            users.add(user);
            if (!JudgeUser(teacher.getUserNumber())){
                return teacher.getUserNumber();
            }
        }

//        SysRole sysRole = sysRoleMapper.selectOne(MybatisPlusUtil.queryWrapperEq("role_name", PropertiesConstant.IDENTITY_TEACHER));
        for (User user : users) {
            userMapper.insert(user);
//            sysUserRoleMapper.insert(new SysUserRole(user.getClassUserId(), sysRole.getId()));
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
        //获取身份code类型
        int identity;
        if ("".equals(role)){
            identity = -1;
        }else identity = IdentityEnum.searchByString(role).getRoleId();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(username != null, "username", username).
                eq(identity != -1,"identity", identity);
        userPage = userMapper.selectPage(new Page<>(current, size), userQueryWrapper);

        userVoPage.setCurrent(userPage.getCurrent());
        userVoPage.setTotal(userPage.getTotal());
        userVoPage.setSize(userPage.getSize());
        List<UserVo> userVos = new ArrayList<>();
        for (User user : userPage.getRecords()) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            //获取身份的string类型
            userVo.setRole(IdentityEnum.searchByCode(user.getIdentity()).getMessage());
            userVos.add(userVo);
        }
        userVoPage.setRecords(userVos);
        return userVoPage;
    }

    @Override
    public String deleteUser(String[] userNumbers) {
        List<String> numbers = new ArrayList<>(Arrays.asList(userNumbers));
        for (String number : numbers) {
            List<User> users = userMapper.selectList(MybatisPlusUtil.queryWrapperEq("user_number", number));
            if (users.size() > 1){
                throw new SystemException(ResultCode.DATABASE_DATA_EXCEPTION);
            }else if (users.isEmpty()){
                throw new SystemException(ResultCode.USER_NOT_EXISTS);
            }
            User user = users.get(0);
            if (user.getIdentity().equals(IdentityEnum.TEACHER.getRoleId())){
                Class aClass = classMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", user.getUserId()));
                if (aClass != null){
                    return number;
                }
            }
        }
        for (String number : numbers) {
            studentMapper.delete(MybatisPlusUtil.queryWrapperEq("user_id", userMapper.selectUserIdByUserNumber(number)));
            userMapper.delete(MybatisPlusUtil.queryWrapperEq("user_number", number));
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
        Long userId = jwtUtil.getUserId(request);
        User user = new User();
        user.setPassword(DigestUtil.md5Hex(password));
        int update = userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        return update > 0;
    }

    @Override
    public String uploadHeadshot(HttpServletRequest request, MultipartFile file) throws Exception {
        Long userId = jwtUtil.getUserId(request);
        String upload = uploadFile.upload(file, PropertiesConstant.USERS, MD5.create().digestHex(userId + TimeUtil.now().toString()));
        User user = new User();
        user.setHeadshot(upload);
        userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        return upload;
    }
}
