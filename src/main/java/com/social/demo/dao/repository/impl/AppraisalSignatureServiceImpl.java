package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.AppraisalSignatureMapper;
import com.social.demo.dao.repository.IAppraisalSignatureService;
import com.social.demo.entity.AppraisalSignature;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public String getSignature(HttpServletRequest request, Integer month) {
        Long userId = jwtUtil.getUserId(request);
        AppraisalSignature appraisalSignature = appraisalSignatureMapper.selectOne(MybatisPlusUtil.queryWrapperEq("month", month, "user_id", userId));
        return appraisalSignature.getSignature();
    }
}
