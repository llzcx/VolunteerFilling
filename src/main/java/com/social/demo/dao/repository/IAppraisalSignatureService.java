package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.RemoveSignatureDto;
import com.social.demo.entity.AppraisalSignature;
import jakarta.servlet.http.HttpServletRequest;

public interface IAppraisalSignatureService extends IService<AppraisalSignature> {
    String getSignature(HttpServletRequest request, Integer month);

    Boolean removeSignature(HttpServletRequest request, RemoveSignatureDto removeSignatureDto);
}
