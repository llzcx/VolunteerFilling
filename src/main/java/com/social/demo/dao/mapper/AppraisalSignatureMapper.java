package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.AppraisalSignature;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppraisalSignatureMapper extends BaseMapper<AppraisalSignature> {
    void add(Long classId, String signature, Integer month, Long userId);
}
