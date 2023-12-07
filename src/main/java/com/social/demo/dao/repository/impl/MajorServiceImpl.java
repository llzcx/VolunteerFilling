package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.AreaMapper;
import com.social.demo.dao.mapper.MajorMapper;
import com.social.demo.dao.repository.IMajorService;
import com.social.demo.entity.Area;
import com.social.demo.entity.Major;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author 周威宇
 */
@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements IMajorService {
    @Autowired
    MajorMapper majorMapper;
    /**
     *添加专业
     */
    @Override
    public  Boolean addMajor(Major major){
        boolean insertSuccess;
        int insertResult = majorMapper.insert(major);
        insertSuccess = insertResult > 0;
        return insertSuccess;
    }

    /**
     * 查询全部专业
     * @return
     */
    @Override
    public List<Major> getMajors(){
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        return majorMapper.selectList(queryWrapper);
    }

    /**
     * 修改专业
     * @param major
     * @return
     */
    @Override
    public Boolean modifyMajor(Major major){
        int update = majorMapper.update( major,
                MybatisPlusUtil.queryWrapperEq("major_id",major.getMajorId()));
        return update > 0;
    }

}