package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class MajorServiceImpl extends ServiceImpl<MajorMapper,Major> implements IMajorService {
    @Autowired
    MajorMapper majorMapper;
    /**
     *添加专业
     */
    @Override
    public  Boolean addMajor(List<Major> majors){
        boolean insertSuccess;
        insertSuccess = this.saveBatch(majors);
        return insertSuccess;
    }

    /**
     * 查询全部专业
     * @return
     */
    @Override
    public IPage<Major> getMajors(Long schoolId,String name,Long current, Long size){
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id",schoolId);
        queryWrapper.like("name",name);
        return majorMapper.selectPage(new Page<>(current,size),queryWrapper);
    }
    /**
     * 查询一个学校的专业
     */
    @Override
    public List<Major> getSchoolMajor(Long schoolId){

        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id",schoolId);
        queryWrapper.ne("enrollment_number", 0);
        return majorMapper.selectList(queryWrapper);
    }
    /**
     * 查询一个学校的专业
     */
    @Override
    public IPage<Major> getSchoolMajors(Long schoolId,Long current, Long size){
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id",schoolId);
        return majorMapper.selectPage(new Page<>(current,size),queryWrapper);
    }
    /**
     * 查询学院专业
     * @param college
     * @return
     */
    @Override
    public IPage<Major> getCollegeMajors(Long schoolId,String college,Long current, Long size){
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id",schoolId);
        queryWrapper.like("college",college);
        return majorMapper.selectPage(new Page<>(current,size),queryWrapper);
    }
    /**
     * 修改专业
     * @param major
     * @return
     */
    @Override
    public Major modifyMajor(Major major){
        int update = majorMapper.update( major,
                MybatisPlusUtil.queryWrapperEq("major_id",major.getMajorId()));
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("major_id",major.getMajorId());
        return  majorMapper.selectOne(queryWrapper);
    }
    /**
     * 删除专业
     * @param majorId
     * @return
     */
    @Override
    public Boolean deleteMajor(Long majorId){
        int delete = majorMapper.delete(MybatisPlusUtil.queryWrapperEq("major_id", majorId));
        return delete > 0;
    }
}