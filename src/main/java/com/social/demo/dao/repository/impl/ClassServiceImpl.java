package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.AppraisalTeamMapper;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IClassService;
import com.social.demo.dao.repository.IStudentService;
import com.social.demo.dao.repository.ISysRoleService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.ClassDto;
import com.social.demo.data.dto.ClassModifyDto;
import com.social.demo.data.vo.ClassVo;
import com.social.demo.data.vo.StudentVo;
import com.social.demo.entity.AppraisalTeam;
import com.social.demo.entity.WishClass;
import com.social.demo.entity.Class;
import com.social.demo.entity.User;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.RanDomUtil;
import com.social.demo.util.TimeUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 杨世博
 * @date 2023/12/4 15:43
 * @description ClassServiceImpl
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {
    @Autowired
    ClassMapper classMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    AppraisalTeamMapper appraisalTeamMapper;

    @Autowired
    IStudentService studentService;

    @Autowired
    IUserService userService;

    @Autowired
    IClassService classService;

    @Override
    public Boolean create(ClassDto classDto) {
        Long userId = getTeacherId(classDto.getUserNumber());
        createClass(classDto.getClassName(), userId);
        return true;
    }

    /**
     * 创建班级，其中userId可谓空，className不可为空
     *
     * @param className
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public Long createClass(@NotNull String className, Long userId){
        Class aClass = new Class();
        className = TimeUtil.now().getYear() + "级" + className + "班";
        aClass.setClassName(className);
        if (userId != null){
            aClass.setUserId(userId);
            sysRoleService.saveUserRole(userId, IdentityEnum.CLASS_ADVISER.getRoleId());
        }
        aClass.setYear(TimeUtil.now().getYear());
        classMapper.insert(aClass);

        //获取一个四位的随机数
        String userNumber = RanDomUtil.CreateUserNumber(String.valueOf(TimeUtil.now().getYear())+ aClass.getClassId());

        User user = new User(userNumber,
                className + "综测账号",
                MD5.create().digestHex(PropertiesConstant.PASSWORD),
                TimeUtil.now(), TimeUtil.now(), IdentityEnum.APPRAISAL_TEAM.getRoleId());
        userMapper.insert(user);
        appraisalTeamMapper.insert(new AppraisalTeam(aClass.getClassId(), user.getUserId()));
        return aClass.getClassId();
    }

    @Override
    public Boolean judgeClassName(String className) {
        className = TimeUtil.now().getYear() + "级" + className + "班";
        List<Class> classes = classMapper.selectList(MybatisPlusUtil.queryWrapperEq("class_name", className));
        if (!classes.isEmpty()) {
            throw new SystemException(ResultCode.IS_EXISTS);
        } else {
            return true;
        }
    }

    @Override
    public Class getClass(Long userId) {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Class aClass = classMapper.selectOne(queryWrapper);
        return aClass;
    }

    @Override
    public List<WishClass> getClassUserId(Long classId, Long timeId, Long current, Long size) {
        current = (current - 1) * size;
        return studentMapper.getUserIdByClassId1(classId, timeId, current, size);
    }
        @Override
        public Long getClass1 (Long classId, Long timeId){
            return studentMapper.getClass1(classId, timeId);
        }

    @Override
    public List<ClassVo> getClassList(Integer year) {
        List<Class> classes = classMapper.selectList(MybatisPlusUtil.queryWrapperEq("year", year));
        List<ClassVo> classVoList = new ArrayList<>();
        for (Class aClass : classes) {

            ClassVo classVo = new ClassVo();
            BeanUtils.copyProperties(aClass, classVo);
            User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", aClass.getUserId()));

            if (user != null){
                classVo.setUserNumber(user.getUserNumber());
                classVo.setUsername(user.getUsername());
                classVo.setPhone(user.getPhone());
            }
            classVoList.add(classVo);
        }
        return classVoList;
    }

    @Override
    public List<StudentVo> getClassStudents(Long classId) throws UnknownHostException {
        List<String> userNumbers = studentMapper.selectUserNumberByClass(classId);
        List<StudentVo> studentVos = new ArrayList<>();
        for (String userNumber : userNumbers) {
            StudentVo student = userService.getStudent(userNumber);
            studentVos.add(student);
        }
        return studentVos;
    }

    @Transactional
    @Override
    public void removeClassAdviser(Integer classId) {
        Long classIdLong = Long.valueOf(classId);
        Class aClass = new Class();
        aClass.setUserId(null);
        Long teacherId = classMapper.selectTeacherUserIdByClassId(classIdLong);
        classMapper.removeClassAdviser(classIdLong);
        sysRoleService.saveUserRole(teacherId, IdentityEnum.TEACHER.getRoleId());
    }

    private Long getTeacherId (String userNumber){
            Long userId = userMapper.selectUserIdByUserNumber(userNumber);

            List<Class> classes = classMapper.selectList(MybatisPlusUtil.queryWrapperEq("user_id", userId));

            if (classes.size() >= 1) {
                throw new SystemException(ResultCode.USER_IS_CLASS_TEACHER);
            }

            return userId;
    }

    @Override
    public IPage<ClassVo> getClassPage (Integer year,int current, int size){
        IPage<Class> classIPage;
        if (year != 0) {
            classIPage = classMapper.selectPage(new Page<>(current, size), MybatisPlusUtil.queryWrapperEq("year", year));
        } else {
            classIPage = classMapper.selectPage(new Page<>(current, size), MybatisPlusUtil.queryWrapperEq());
        }
        IPage<ClassVo> classVoIPage = new Page<>(current, size);
        classVoIPage.setTotal(classIPage.getTotal());
        List<ClassVo> classVoList = new ArrayList<>();
        for (Class aClass : classIPage.getRecords()) {

        ClassVo classVo = new ClassVo();
        BeanUtils.copyProperties(aClass, classVo);
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", aClass.getUserId()));

        if (user != null){
            classVo.setUserNumber(user.getUserNumber());
            classVo.setUsername(user.getUsername());
            classVo.setPhone(user.getPhone());
        }
        classVo.setSize(Math.toIntExact(studentMapper.selectCount(MybatisPlusUtil.queryWrapperEq("class_id", classVo.getClassId()))));
        classVoList.add(classVo);
        }
        classVoIPage.setRecords(classVoList);
        return classVoIPage;
    }

    @Override
    @Transactional
    public Boolean delete(Long[] classIds) {
        for (Long classId : classIds) {
            Long count = studentMapper.selectCount(MybatisPlusUtil.queryWrapperEq("class_id",classId));
            if (count != 0){
                return false;
            }
        }
        for (Long classId : classIds) {
            deleteClass(classId);
        }
        return true;
    }

    @Transactional
    public void deleteClass(Long classId){
        classMapper.delete(MybatisPlusUtil.queryWrapperEq("class_id", classId));
        Long userId = classMapper.selectTeacherUserIdByClassId(classId);
        sysRoleService.saveUserRole(userId, IdentityEnum.TEACHER.getRoleId());
    }

    @Override
    @Transactional
    public Boolean modify(ClassModifyDto classModifyDto) {
        if (classMapper.judge(classModifyDto.getClassId(), classModifyDto.getClassName()) != null){
            return false;
        }
        Long teacherId = classMapper.selectTeacherUserIdByClassId(classModifyDto.getClassId());
        sysRoleService.saveUserRole(teacherId, IdentityEnum.TEACHER.getRoleId());
        Class aClass = new Class();
        BeanUtils.copyProperties(classModifyDto, aClass);
        Long userId = userMapper.selectUserIdByUserNumber(classModifyDto.getUserNumber());
        aClass.setUserId(userId);
        classMapper.update(aClass, MybatisPlusUtil.queryWrapperEq("class_id", classModifyDto.getClassId()));
        sysRoleService.saveUserRole(userId, IdentityEnum.CLASS_ADVISER.getRoleId());
        return true;
    }
}


