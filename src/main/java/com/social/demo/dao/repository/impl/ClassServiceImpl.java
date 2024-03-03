package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IClassService;
import com.social.demo.dao.repository.ISysRoleService;
import com.social.demo.data.dto.ClassDto;
import com.social.demo.data.dto.ClassModifyDto;
import com.social.demo.data.vo.ClassVo;
import com.social.demo.entity.Class;
import com.social.demo.entity.User;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        aClass.setClassName(className);
        if (userId != null){
            aClass.setUserId(userId);
            sysRoleService.saveUserRole(userId, IdentityEnum.CLASS_ADVISER.getRoleId());
        }
        aClass.setYear(TimeUtil.now().getYear());
        classMapper.insert(aClass);
        return aClass.getClassId();
    }

    @Override
    public Boolean judgeClassName(String className){
        List<Class> classes = classMapper.selectList(MybatisPlusUtil.queryWrapperEq("class_name", className));
        if (!classes.isEmpty()){
            throw new SystemException(ResultCode.IS_EXISTS);
        }else {
            return true;
        }
    }

    @Override
    public List<Long> getClassUserId(Long classId) {
        return studentMapper.getUserIdByClassId(classId);
    }

    private Long getTeacherId(String userNumber){
        Long userId = userMapper.selectUserIdByUserNumber(userNumber);

        List<Class> classes = classMapper.selectList(MybatisPlusUtil.queryWrapperEq("user_id", userId));

        if (classes.size() >= 1){
            throw new SystemException(ResultCode.USER_IS_CLASS_TEACHER);
        }

        return userId;
    }

    @Override
    public IPage<ClassVo> getClassPage(Integer year, int current, int size) {
        IPage<Class> classIPage;
        if (year != 0){
            classIPage = classMapper.selectPage(new Page<>(current, size), MybatisPlusUtil.queryWrapperEq("year", year));
        }else {
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
            Long count = userMapper.selectCount(MybatisPlusUtil.queryWrapperEq(classId));
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
