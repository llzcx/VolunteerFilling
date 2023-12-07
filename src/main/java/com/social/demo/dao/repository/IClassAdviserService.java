package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.data.vo.ClassMemberVo;


public interface IClassAdviserService {

    IPage<ClassMemberVo> getStudents(Long classId, String userNumber, String username, Integer role, Integer current, Integer size);
}
