package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.vo.AppraisalContentVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.data.vo.YPage;
import com.social.demo.entity.Appraisal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAppraisalService extends IService<Appraisal> {
    AppraisalVo getAppraisal(HttpServletRequest request, Integer month);

    Boolean uploadAppraisal(AppraisalContentVo[] appraisalContentVos);

    String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception;


    /**
     * 获取本月综测情况
     * @param request
     * @return
     */
    AppraisalVo getAppraisalThisMonth(HttpServletRequest request);

    YPage<AppraisalVo> getAppraisalsToTeam(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size);

    IPage<AppraisalVo> getAppraisalsToTeacher(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size);

    List<Integer> getMonthToTeacher(HttpServletRequest request);

    List<Integer> getMonthToTeam(HttpServletRequest request);

    IPage<AppraisalVo> getAppraisalHistory(Integer year, Integer month, String className, String keyword, Integer current, Integer size);
}
