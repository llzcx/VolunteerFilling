package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.JsonUtil;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IAreaService;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.data.vo.AreaVo;
import com.social.demo.entity.Area;
import com.social.demo.entity.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 地区接口
 *
 * @author 周威宇
 */
@RestController
@RequestMapping("/area")
@Validated
public class AreaController {
    @Autowired
    private IAreaService areaService;
    /**
     * 添加地区
     */
    @PostMapping("/addArea")
    public ApiResp<Boolean> addWise(@RequestBody AreaVo areaVo){
        Area area = AreaVoArea(areaVo);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        area.setUpdateTime(currentTimestamp);
        return ApiResp.success(areaService.addArea(area));
    }
    /**
     * 修改地区
     */
    @PutMapping("modifyArea")
    public ApiResp<Boolean> modifyArea(@RequestBody AreaVo areaVo){
        Area area = AreaVoArea(areaVo);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        area.setUpdateTime(currentTimestamp);
        return ApiResp.success(areaService.modifyArea(area));
    }

    /**
     *查看地区
     */
    @GetMapping("selectArea")
    public ApiResp<List<Area>> selectArea(@RequestParam("name")String name){
        if (name.isEmpty()){
            return ApiResp.success(areaService.getAreas());
        }else {
            List<Area> areas = areaService.getArea(name);
           return ApiResp.success(areas);
        }

    }
    /**
     * 删除地区
     * @param areaIds
     * @return
     */
    @DeleteMapping
    public ApiResp<Boolean> deleteArea(@RequestParam("areaIds")List<Long> areaIds){
        Boolean deleteArea = null;
        for(int i=0;i<areaIds.size();i++){
            deleteArea = areaService.deleteArea(areaIds.get(i));
        }

        return ApiResp.success(deleteArea);
    }
    public Area AreaVoArea(AreaVo areaVo){
        Area area = new Area();
        area.setAreaId(areaVo.getAreaId());
        area.setName(areaVo.getName());
        area.setUpdateTime(areaVo.getUpdateTime());
        String includingProvinces = JsonUtil.object2StringSlice(areaVo.getIncludingProvinces());
        area.setIncludingProvinces(includingProvinces);
        return area;
    }
}
