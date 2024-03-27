package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.Autograph;

import java.util.List;

public interface IAutographService extends IService<Autograph> {
    Boolean addAutograph(Autograph autograph);
    List<Autograph> getAutograph(Long userId,Long timeId);
}
