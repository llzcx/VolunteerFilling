package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.dao.repository.IClassAdviserService;
import com.social.demo.data.vo.ClassMemberVo;
import org.springframework.stereotype.Service;

/**
 * @author 杨世博
 * @date 2023/12/7 21:18
 * @description ClassAdviserServiceImpl
 */
@Service
public class ClassAdviserServiceImpl implements IClassAdviserService {

    @Override
    public IPage<ClassMemberVo> getStudents(Long classId, String userNumber, String username, Integer role, Integer current, Integer size) {
        return null;
    }
}
