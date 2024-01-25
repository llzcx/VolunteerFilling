package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.data.dto.IdentityDto;
import com.social.demo.data.vo.ClassUserVo;
import jakarta.servlet.http.HttpServletRequest;


public interface IClassAdviserService {

    IPage<ClassUserVo> getStudents(HttpServletRequest request, String userNumber, String username, String role, Integer rank, Integer current, Integer size);
}
