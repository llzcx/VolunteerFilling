package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.Wish;
import com.social.demo.entity.WishTime;

import java.util.List;

/**
 * @author 周威宇
 */
public interface IWishTimeService extends IService<WishTime> {
    /**
     * 添加新的志愿填报
     * @param wishTime 志愿填报时间信息
     * @return
     */
    Boolean addWishTime(WishTime wishTime);

    /**
     * 修改志愿
     * @param wishTime 志愿信息
     * @return
     */
    Boolean modifyWishTime(WishTime wishTime);
    /**
     * 按照学校编码搜索志愿时间
     * @param schoolId 学校编码
     * @return
     */
    IPage<WishTime> selectWishTime(Integer schoolId, Long current, Long size);
    /**
     * 按照入学时间搜索志愿时间
     * @param schoolId 学校编码
     * @return
     */
    IPage<WishTime> selectWishTime1(Integer schoolId,Integer ago, Long current, Long size);

    /**
     * 删除志愿时间
     * @param id
     * @return
     */
    Boolean deleteWishTime(Long id);

}
