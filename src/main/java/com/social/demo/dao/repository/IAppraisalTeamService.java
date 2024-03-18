package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.AppraisalTeamDto;
import com.social.demo.data.vo.AppraisalTeamUserVo;
import com.social.demo.data.vo.AppraisalTeamVo;
import com.social.demo.entity.AppraisalTeam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAppraisalTeamService extends IService<AppraisalTeam> {
    Boolean addAppraisalTeam(HttpServletRequest request, String[] userNumbers);

    List<AppraisalTeamVo> getAppraisalTeam(HttpServletRequest request);

    void resetAppraisalTeamPwd(String userNumber);

    Boolean averageClassMember(HttpServletRequest request);

    void revocationMember(HttpServletRequest request);

    Boolean revocationTeam(HttpServletRequest request, String[] userNumbers);

    Boolean allocationClassMember(AppraisalTeamDto appraisalTeamDto);

    AppraisalTeamUserVo getMessage(HttpServletRequest request);

    String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception;

    Boolean getClassAppraisalState(HttpServletRequest request, Integer month);
}
