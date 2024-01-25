package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.ClassDto;
import com.social.demo.data.dto.ClassModifyDto;
import com.social.demo.data.vo.ClassVo;
import com.social.demo.entity.Class;

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

    Boolean judgeClassName(String className);

    /**
     * 根据班级id获取班级所有学生id
     * @param classId
     * @return
     */
    List<Long> getClassUserId(Long classId);
}
