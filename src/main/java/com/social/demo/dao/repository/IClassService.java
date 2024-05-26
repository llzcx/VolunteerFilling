package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.ClassDto;
import com.social.demo.data.dto.ClassModifyDto;
import com.social.demo.data.vo.ClassVo;
import com.social.demo.data.vo.StudentVo;
import com.social.demo.entity.WishClass;
import com.social.demo.entity.Class;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/4 15:42
 * @description IClassService
 */
public interface IClassService extends IService<Class> {
    Boolean create(ClassDto classDto);

    IPage<ClassVo> getClassPage(Integer year, int current, int size);

    Boolean delete(Long[] classIds);

    Boolean modify(ClassModifyDto classModifyDto);
    /**
     * 创建班级
     *
     * @param className
     * @param userId
     * @return
     */
    @Transactional
    Long createClass(@NotNull String className, Long userId);

    Boolean judgeClassName(String className);

    Class getClass(Long userId);
    /**
     * 根据班级id获取班级所有学生id
     * @param classId
     * @return
     */
    List<WishClass> getClassUserId(Long classId, Long timeId,Long current,Long size);

    Long getClass1(Long classId, Long timeId);

    List<ClassVo> getClassList(Integer year);

    List<StudentVo> getClassStudents(Long classId) throws UnknownHostException;

    void removeClassAdviser(Integer classId);

    void resetClassAppraisal(Integer classId);
}
