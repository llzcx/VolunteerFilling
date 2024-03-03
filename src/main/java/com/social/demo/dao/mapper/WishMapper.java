package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.data.vo.WishVo;
import com.social.demo.entity.Wish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 周威宇
 */
@Mapper
public interface WishMapper extends BaseMapper<Wish> {
    /**
     * 查看学生填报信息
     */
    WishVo selectWish(Long userId);
    /**
     * 查看学校所有学生志愿
     */
    List<Wish> selectSchoolWish(Long schoolId,Integer timeId);
}
