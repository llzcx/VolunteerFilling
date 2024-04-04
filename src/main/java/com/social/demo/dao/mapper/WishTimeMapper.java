package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.data.vo.NotAcceptedVo;
import com.social.demo.data.vo.WishTimeVo1;
import com.social.demo.entity.WishTime;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 周威宇
 */
@Mapper
public interface WishTimeMapper extends BaseMapper<WishTime> {
    /**
     * 查看学生填报信息
     */
    List<WishTimeVo1> selectWishTime(Long userId);
    /**
     * 获取未录取学生
     */
    List<NotAcceptedVo> selectNotAccepted(Long timeId);
}
