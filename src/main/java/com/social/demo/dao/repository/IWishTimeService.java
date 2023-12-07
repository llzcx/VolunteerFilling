package com.social.demo.dao.repository;

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
     * 搜索志愿时间
     * @param ago 查询年份
     * @return
     */
    List<WishTime> selectWishTime(Integer ago);


}
