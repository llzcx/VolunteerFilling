package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.Mate;
import com.social.demo.entity.SysRoleApi;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author 周威宇
 */
@Mapper
public interface MateMapper extends BaseMapper<Mate> {
    public Long mateJudge(Long schoolId, Integer timeId);
}