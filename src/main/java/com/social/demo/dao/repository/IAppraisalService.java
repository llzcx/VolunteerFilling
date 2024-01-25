package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.vo.AppraisalContentVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.entity.Appraisal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IAppraisalService extends IService<Appraisal> {
    AppraisalVo getAppraisal(HttpServletRequest request, Integer month);

    Boolean uploadAppraisal(AppraisalContentVo[] appraisalContentVos);

    Boolean uploadSignature(MultipartFile file, HttpServletRequest request) throws Exception;


    /**
     * 获取本月综测情况
     * @param request
     * @return
     */
    AppraisalVo getAppraisalThisMonth(HttpServletRequest request);

    IPage<AppraisalVo> getAppraisalsToTeam(HttpServletRequest request, String name, String userNumber, Integer month, Integer rank, Integer current, Integer size);

    IPage<AppraisalVo> getAppraisalsToTeacher(HttpServletRequest request, String name, String userNumber, Integer month, Integer rank, Integer current, Integer size);

    void modifyAppraisal(AppraisalContentVo appraisalContentVo);
}
