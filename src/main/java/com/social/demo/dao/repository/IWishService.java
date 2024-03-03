package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.SchoolDto;
import com.social.demo.data.vo.WishVo;
import com.social.demo.entity.Wish;

import java.util.List;

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
    WishVo selectWish(Long userId);
    /**
     * 获取一个学校的所有学生的志愿
     */
    List<Wish> selectSchool(Long schoolId,Integer timeId);
    /**
     * 添加一个学校的所有学生
     * @param  school 学校名称
     * @return
     */
    Boolean addWishList(String school);

}
