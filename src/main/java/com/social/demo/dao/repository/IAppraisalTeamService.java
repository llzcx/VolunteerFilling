package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.AppraisalTeam;
import jakarta.servlet.http.HttpServletRequest;

public interface IAppraisalTeamService extends IService<AppraisalTeam> {
    void addAppraisalTeam(HttpServletRequest request, String userNumber);
}
