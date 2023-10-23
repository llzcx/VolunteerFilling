package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.constant.RedisConstant;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.LoginDto;
import com.social.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 陈翔
 */
public interface IUserService extends IService<User> {



    /**
     * 退出登录
     * @param request
     * @return
     */
    Boolean loginOut(HttpServletRequest request);



    /**
     * 登录
     * @param loginDto
     * @return token 登录令牌
     */
    TokenPair login(LoginDto loginDto);
}
