package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.AppraisalDto;
import com.social.demo.data.vo.AppraisalToOtherVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.entity.Appraisal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IAppraisalService extends IService<Appraisal> {
    AppraisalVo getAppraisal(HttpServletRequest request, Integer month);

    IPage<AppraisalToOtherVo> getAppraisalsToTeacher(HttpServletRequest request, String name, String userNumber, Integer month, Integer rank, Integer current, Integer size);

    Boolean uploadAppraisal(AppraisalDto appraisalDto);

    IPage<AppraisalToOtherVo> getAppraisalsToTeam(HttpServletRequest request, String name, String userNumber, Integer month, Integer rank, Integer current, Integer size);

    Boolean uploadSignature(MultipartFile file, HttpServletRequest request) throws Exception;

    Boolean downloadSignature(Integer month, String userNumber, HttpServletResponse response) throws Exception;

    Boolean downloadSignature(Integer month, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
