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
import com.social.demo.constant.StateEnum;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IClassService;
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
import com.social.demo.util.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
    IClassService classService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SubjectGroupMapper subjectGroupMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ConsigneeMapper consigneeMapper;

    @Autowired
    AppraisalMapper appraisalMapper;

    @Autowired
    AppealMapper appealMapper;

    @Qualifier("local")
    @Autowired
    UploadFile uploadFile;

    @Value("${file-picture.address.headshot}")
    private String HEADSHOT;

    //成绩初始分数
    @Value("${basic.attribute.score}")
    private Double SCORE;

    //综测成绩初始分数
    @Value("${basic.attribute.appraisal_score}")
    private Double APPRAISAL_SCORE;

    @Autowired
    URLUtil urlUtil;

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
    public StudentVo getInformationOfStudent(HttpServletRequest request) throws UnknownHostException {
        Long userId = jwtUtil.getUserId(request);
        String userNumber = userMapper.selectUserNumberByUserId(userId);
        return getStudent(userNumber);
    }

    @Override
    public Boolean modifyInformation(HttpServletRequest request, UserDtoByStudent userDtoByStudent) {
        Long userId = jwtUtil.getUserId(request);
        User user = new User();
        user.setPhone(userDtoByStudent.getPhone().isEmpty() ? null : userDtoByStudent.getPhone());
        if (!userDtoByStudent.getPhone().isEmpty()) userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        Consignee consignee = new Consignee();
        consignee.setAddress(userDtoByStudent.getConsigneeBo().getAddress().isEmpty() ? null : userDtoByStudent.getConsigneeBo().getAddress());
        consignee.setPhone(userDtoByStudent.getConsigneeBo().getPhone().isEmpty() ? null : userDtoByStudent.getConsigneeBo().getPhone());
        consignee.setUsername(userDtoByStudent.getConsigneeBo().getUsername().isEmpty() ? null : userDtoByStudent.getConsigneeBo().getUsername());
        if (!userDtoByStudent.getConsigneeBo().getPhone().isEmpty() ||
                !userDtoByStudent.getConsigneeBo().getAddress().isEmpty() ||
                !userDtoByStudent.getConsigneeBo().getUsername().isEmpty())
            consigneeMapper.update(consignee, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        return true;
    }


    @Override
    public StudentVo getStudent(String userNumber) throws UnknownHostException {
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        Student student = studentMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", user.getUserId()));
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(user, studentVo);
        BeanUtils.copyProperties(student, studentVo);
        studentVo.setHeadshot(studentVo.getHeadshot() != null ? urlUtil.getUrl(studentVo.getHeadshot()) : null);
        studentVo.setSubjects(JSONUtil.toList(subjectGroupMapper.selectSubjects(student.getHashcode()).getSubjects(), String.class));
        studentVo.setSchool(schoolMapper.selectNameBySchoolId(student.getSchoolId()));
        studentVo.setClassName(classMapper.selectNameByClassId(student.getClassId()));
        ConsigneeBo consigneeBo = new ConsigneeBo();
        BeanUtils.copyProperties(consigneeMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", user.getUserId())), consigneeBo);
        studentVo.setConsignee(consigneeBo);
        return studentVo;
    }

    @Override
    public Boolean modifyStudent(UserDtoByTeacher userDtoByTeacher){
        String userNumber = userDtoByTeacher.getUserNumber();
        userDtoByTeacher.setUserNumber(null);
        User user = new User();
        BeanUtils.copyProperties(userDtoByTeacher, user);
        Student student = new Student();
        BeanUtils.copyProperties(userDtoByTeacher, student);
        user.setUserNumber(null);
        NullifyEmptyStrings.nullifyEmptyStringsInObject(user);
        NullifyEmptyStrings.nullifyEmptyStringsInObject(student);
        if (!ObjectUtils.areAllFieldsNull(user)) userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
        if (!ObjectUtils.areAllFieldsNull(student)) studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userMapper.selectUserIdByUserNumber(userNumber)));
        return true;
    }

    @Override
    public Boolean reset(String[] userNumbers) {
        List<String> numbers = new ArrayList<>(Arrays.asList(userNumbers));
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
            user.setCreated(TimeUtil.now());
            user.setLastDdlTime(TimeUtil.now());
            users.add(user);
            Student student = new Student();
            BeanUtils.copyProperties(studentDto, student);
            Long classId = classMapper.selectClassIdByName(getClassAllName(studentDto.getClassName()));
            if (classId == null){
                classId = classService.createClass(studentDto.getClassName(), null);
            }
            student.setClassId(classId);
            Long schoolId = schoolMapper.selectSchoolIdByName(studentDto.getSchool());
            if (schoolId == null){
                School school = new School();
                school.setName(studentDto.getSchool());
                schoolMapper.insert(school);
                schoolId = school.getSchoolId();
            }
            student.setSchoolId(schoolId);
            student.setState(StateEnum.NOT_FILL.getState());
            student.setScore(SCORE);
            student.setAppraisalScore(APPRAISAL_SCORE);
            user.setPassword(DigestUtil.md5Hex(PropertiesConstant.PASSWORD));
            user.setIdentity(IdentityEnum.STUDENT.getRoleId());
            student.setEnrollmentYear(TimeUtil.now().getYear());
            Set<String> strings = new HashSet<>(Arrays.asList(studentDto.getSubjects()));
            int hashCode = 0;
            for (String string : strings) {
                hashCode += string.hashCode();
            }
            student.setHashcode(hashCode);
            if (!JudgeUser(studentDto.getUserNumber())){
                return studentDto.getUserNumber();
            }
            map.put(user.getUserNumber(), student);
        }
//        SysRole sysRole = sysRoleMapper.selectOne(MybatisPlusUtil.queryWrapperEq("role_name", PropertiesConstant.IDENTITY_STUDENT));
        userMapper.insertBatchSomeColumn(users);
        List<Consignee> consignees = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        for (User user : users) {
//            sysUserRoleMapper.insert(new SysUserRole(user.getClassUserId(), sysRole.getId()));
            Student student = map.get(user.getUserNumber());
            student.setUserId(user.getUserId());
            students.add(student);
            Consignee consignee = new Consignee();
            consignee.setUserId(user.getUserId());
            consignees.add(consignee);
        }
        studentMapper.insertBatchSomeColumn(students);
        consigneeMapper.insertBatchSomeColumn(consignees);
        return null;
    }

    private String getClassAllName(String className){
        return TimeUtil.now().getYear() + "级" + className + "班";
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
        userQueryWrapper.like(username != null, "username", username)
                .eq(identity != -1,"identity", identity).ne("identity", IdentityEnum.SUPER.getRoleId())
                .or().like(username!=null, "user_number", username)
                .eq(identity != -1,"identity", identity).ne("identity", IdentityEnum.SUPER.getRoleId());
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
            String className = null;
            if (user.getIdentity().equals(IdentityEnum.STUDENT.getRoleId())){
                className = studentMapper.selectClassNameByUserId(user.getUserId());
            }else if (user.getIdentity().equals(IdentityEnum.CLASS_ADVISER.getRoleId())){
                className = classMapper.selectClassNameByTeacherUserId(user.getUserId());
            }
            userVo.setClassName(className);
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
            Long userId = userMapper.selectUserIdByUserNumber(number);
            studentMapper.delete(MybatisPlusUtil.queryWrapperEq("user_id", userId));
            userMapper.delete(MybatisPlusUtil.queryWrapperEq("user_number", number));
            consigneeMapper.delete(MybatisPlusUtil.queryWrapperEq("user_id", userId));
            appealMapper.delete(MybatisPlusUtil.queryWrapperEq("user_id", userId));
            appraisalMapper.delete(MybatisPlusUtil.queryWrapperEq("user_id", userId));
        }
        return null;
    }

    @Override
    public List<ClassTeacherVo> getTeachers() {
        List<User> users = userMapper.selectTeacher();
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
        String upload = uploadFile.upload(file, HEADSHOT, MD5.create().digestHex(userId + TimeUtil.now().toString()));
        User user = new User();
        user.setHeadshot(upload);
        userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        return upload;
    }

    @Override
    public Boolean modifyClass(ModifyClassDto modifyClassDto) {
        for (Long userId : modifyClassDto.getUserId()) {
            Student student = new Student();
            student.setClassId(modifyClassDto.getClassId());
            studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        }
        return true;
    }
}