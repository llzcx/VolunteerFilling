package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.entity.AppraisalSignature;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppraisalSignatureMapper extends BaseMapper<AppraisalSignature> {
    void add(Long classId, String signature, Integer month, Long userId);


    String getSignature(Long userId, Long classId, Integer month);

    String selectSignature(Integer month, Long userId);

    String getTeacherSignature(Long classId, Integer month);

    String getTeamSignature(Long classId, Integer month);
}
