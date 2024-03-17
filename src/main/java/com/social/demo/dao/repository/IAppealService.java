package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.AppealDto;
import com.social.demo.data.vo.AppealVo;
import com.social.demo.entity.Appeal;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IAppealService extends IService<Appeal> {

    List<AppealVo> getAppealsToStudent(HttpServletRequest request);
    Boolean submitAppeal(HttpServletRequest request, AppealDto appeal);
    Boolean quashAppeal(HttpServletRequest request, Long appealId);
    Boolean disposeAppeal(HttpServletRequest request, Long appealId);
    Boolean disposeAppealByTeam(HttpServletRequest request, Long appealId);

    Boolean deleteAppeals(HttpServletRequest request, Long[] appealId);

    Boolean deleteAppealsByTeacher(HttpServletRequest request, Long[] appealId);

    List<AppealVo> getAppealByTeacher(HttpServletRequest request);

    List<AppealVo> getAppealByTeam(HttpServletRequest request);

    Boolean deleteAppealsByTeam(HttpServletRequest request, Long[] appealId);
}
