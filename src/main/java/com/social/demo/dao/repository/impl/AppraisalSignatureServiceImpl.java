package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.AppraisalMapper;
import com.social.demo.dao.mapper.AppraisalSignatureMapper;
import com.social.demo.dao.repository.IAppraisalSignatureService;
import com.social.demo.data.dto.RemoveSignatureDto;
import com.social.demo.entity.AppraisalSignature;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 杨世博
 * @date 2024/3/17 11:22
 * @description AppraisalSignatureServiceImpl
 */
@Service
public class AppraisalSignatureServiceImpl extends ServiceImpl<AppraisalSignatureMapper, AppraisalSignature> implements IAppraisalSignatureService{

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AppraisalSignatureMapper appraisalSignatureMapper;

    @Autowired
    AppraisalMapper appraisalMapper;

    @Override
    public String getSignature(HttpServletRequest request, Integer month) {
        if (month == 0) month = TimeUtil.now().getMonthValue();
        Long userId = jwtUtil.getUserId(request);
        return appraisalSignatureMapper.selectSignature(month,userId);
    }

    @Override
    @Transactional
    public Boolean removeSignature(HttpServletRequest request, RemoveSignatureDto removeSignatureDto) {
        Long teamUserId = jwtUtil.getUserId(request);
        removeSignatureDto.setMonth(removeSignatureDto.getMonth() != 0 ? removeSignatureDto.getMonth() : TimeUtil.now().getMonthValue());
        appraisalSignatureMapper.delete(MybatisPlusUtil.queryWrapperEq("user_id", teamUserId, "month", removeSignatureDto.getMonth()));
        appraisalMapper.removeSignature(removeSignatureDto.getMonth(), removeSignatureDto.getUserNumber());
        return null;
    }
}
