package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.AutographMapper;
import com.social.demo.dao.repository.IAutographService;
import com.social.demo.entity.Autograph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutographServiceImpl extends ServiceImpl<AutographMapper, Autograph> implements IAutographService {

    @Autowired
    AutographMapper autographMapper;

    public Boolean addAutograph(Autograph autograph){
        boolean insertSuccess;
        int insertResult = autographMapper.insert(autograph);
        insertSuccess = insertResult > 0;
        return insertSuccess;
    }
    public List<Autograph> getAutograph(Long userId, Long timeId){
        QueryWrapper<Autograph> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.like("time_id",timeId);
        return autographMapper.selectList(queryWrapper);
    }
}
