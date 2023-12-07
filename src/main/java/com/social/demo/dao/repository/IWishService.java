package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.SchoolDto;
import com.social.demo.entity.Wish;

/**
 * @author 周威宇
 */
public interface IWishService extends IService<Wish> {
    /**
     * 填写志愿
     * @param wish 志愿信息
     * @return
     */
    Boolean addWish(Wish wish);

    /**
     * 修改志愿
     * @param wish 志愿信息
     * @return
     */
    Boolean modifyWish(Wish wish);

    /**
     * 搜索志愿
     * @param userId 用户id
     * @return
     */
    Wish selectWish(Long userId);
    /**
     * 添加一个学校的所有学生
     * @param  school 学校名称
     * @return
     */
    Boolean addWishList(String school);

}
