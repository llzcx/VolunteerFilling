package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.Area;

import java.util.List;


/**
 * @author 周威宇
 */
public interface IAreaService extends IService<Area> {
    /**
     * 添加地区
     * @param area
     * @return
     */
    Boolean addArea(Area area);

    /**
     * 查询所有地区
     * @return
     */
    List<Area> getAreas();
    /**
     * 查询一个地区
     * @return
     */
    Area getArea1(Long areaId);
    /**
     * 查询地区
     * @return
     */
    List<Area> getArea(String name);
    /**
     * 修改地区
     * @param area
     * @return
     */
    Boolean modifyArea(Area area);

    /**
     * 删除地区
     * @param areaId
     * @return
     */
    Boolean deleteArea(Long areaId);


}