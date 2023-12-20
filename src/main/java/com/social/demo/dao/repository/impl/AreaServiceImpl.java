package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.AreaMapper;
import com.social.demo.dao.repository.IAreaService;
import com.social.demo.entity.Area;
import com.social.demo.entity.School;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author 周威宇
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {
    @Autowired
    AreaMapper areaMapper;
    /**
     *添加地区
     */
    @Override
    public  Boolean addArea(Area area){

        Area area1 = areaMapper.selectOne(MybatisPlusUtil.queryWrapperEq("name", area.getName()));
        int insert = 0;
        if (area1 != null){
            throw new SystemException(ResultCode.SCHOOL_ALREADY_EXISTS);
        } else {
             insert = areaMapper.insert(area);
        }
        return insert > 0;
    }

    /**
     * 查询全部地区
     * @return
     */
    @Override
    public List<Area> getAreas(){
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        return areaMapper.selectList(queryWrapper);
    }
    /**
     * 查询地区
     * @return
     */
    @Override
    public List<Area> getArea(String name){
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        return areaMapper.selectList(wrapper);
    }
    /**
     * 查询一个地区
     * @return
     */
    @Override
    public Area getArea1(Long areaId){
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("area_id",areaId);
        return areaMapper.selectOne(wrapper);
    }
    /**
     * 修改地区
     * @param area
     * @return
     */
    @Override
    public Boolean modifyArea(Area area){
        int update = areaMapper.update( area,
                MybatisPlusUtil.queryWrapperEq("area_id",area.getAreaId()));
        return update > 0;
    }

    /**
     * 删除地区
     * @param areaId
     * @return
     */
    @Override
    public Boolean deleteArea(Long areaId){
        int delete = areaMapper.delete(MybatisPlusUtil.queryWrapperEq("area_id", areaId));
        return delete > 0;
    }
}