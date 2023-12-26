package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.Student;

public interface IStudentService extends IService<Student> {
    Boolean modifyState(String schoolName, Integer year, Integer state);
}
