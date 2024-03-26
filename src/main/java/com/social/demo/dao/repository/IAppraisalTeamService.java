package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.vo.AppraisalTeamUserVo;
import com.social.demo.data.vo.AppraisalTeamVo;
import com.social.demo.entity.AppraisalTeam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IAppraisalTeamService extends IService<AppraisalTeam> {

    AppraisalTeamVo getAppraisalTeam(HttpServletRequest request);

    void resetAppraisalTeamPwd(String userNumber);

    AppraisalTeamUserVo getMessage(HttpServletRequest request);

    String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception;
}
