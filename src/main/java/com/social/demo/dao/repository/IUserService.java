package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.LoginDto;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.dto.UserDtoByTeacher;
import com.social.demo.data.vo.UserVo;
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

    /**
     * 获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    UserVo getInformation(HttpServletRequest id);

    /**
     * 修改个人信息
     * @param userDtoByStudent 用户修改后的数据
     * @return 用户修改后的个人信息
     */
    UserVo modifyInformation(UserDtoByStudent userDtoByStudent);

    /**
     * 获取学生个人信息
     * @param id 学生id
     * @return 学生个人信息
     */
    UserVo getStudent(Long id);

    /**
     * 修改学生个人信息
     * @param userDtoByTeacher 老师上传的学生修改信息
     * @return 学生个人信息
     */
    UserVo modifyStudent(UserDtoByTeacher userDtoByTeacher);

    /**
     * 重置学生密码
     * @param id 学生id
     * @return 是否操作成功
     */
    Boolean reset(Long id);
}
