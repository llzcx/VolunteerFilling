package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.AppraisalUploadDto;
import com.social.demo.data.vo.AppraisalContentVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.data.vo.YPage;
import com.social.demo.entity.Appraisal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
import java.util.List;

public interface IAppraisalService extends IService<Appraisal> {
    AppraisalVo getAppraisal(HttpServletRequest request, Integer month) throws UnknownHostException;

    Boolean uploadAppraisal(AppraisalUploadDto appraisalUploadDto);

    String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception;


    /**
     * 获取本月综测情况
     * @param request
     * @return
     */
    AppraisalVo getAppraisalThisMonth(HttpServletRequest request) throws UnknownHostException;

    YPage<AppraisalVo> getAppraisalsToTeam(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size) throws UnknownHostException;

    YPage<AppraisalVo> getAppraisalsToTeacher(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size) throws UnknownHostException;

    List<Integer> getMonthToTeacher(HttpServletRequest request);

    List<Integer> getMonthToTeam(HttpServletRequest request);

    IPage<AppraisalVo> getAppraisalHistory(Integer year, Integer month, String className, String keyword, Integer current, Integer size) throws UnknownHostException;

    IPage<AppraisalVo> getAppraisalsToStudent(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size) throws UnknownHostException;

    List<AppraisalVo> getClassAppraisal(Long classId, Integer month, Integer year) throws UnknownHostException;

    Integer getSignatureCount(HttpServletRequest request, Integer month);

    List<Integer> getMonthToStudent(HttpServletRequest request);
}
