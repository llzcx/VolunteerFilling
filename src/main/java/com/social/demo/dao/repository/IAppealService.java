package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.dto.AppealDto;
import com.social.demo.data.vo.AppealVo;
import com.social.demo.entity.Appeal;
import jakarta.servlet.http.HttpServletRequest;

public interface IAppealService extends IService<Appeal> {
    IPage<AppealVo> getAppeals(HttpServletRequest request, String username, String userNumber, Integer state, Integer current, Integer size);
    IPage<AppealVo> getAppealsToStudent(HttpServletRequest request, Integer state, Integer current, Integer size);

    void submitAppeal(HttpServletRequest request, AppealDto appealDto);

    IPage<AppealVo> getAppealsByTeam(HttpServletRequest request, String username, String userNumber, Integer state, Integer current, Integer size);

    Boolean quashAppeal(HttpServletRequest request, Long appealId);

    Boolean disposeAppeal(HttpServletRequest request, Long appealId);

    Boolean disposeAppealByTeam(HttpServletRequest request, Long appealId);
}
