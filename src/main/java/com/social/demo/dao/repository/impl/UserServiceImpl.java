package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.LoginDto;
import com.social.demo.data.dto.UserDtoByStudent;
import com.social.demo.data.dto.UserDtoByTeacher;
import com.social.demo.data.vo.UserVo;
import com.social.demo.entity.User;
import com.social.demo.manager.security.authentication.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈翔
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public Boolean loginOut(HttpServletRequest request) {

        return null;
    }

    @Override
    public TokenPair login(LoginDto loginDto) {
        //md5
        loginDto.setPassword(DigestUtil.md5Hex(loginDto.getPassword()));
        final List<User> users = userMapper.selectByMap(MybatisPlusUtil.getMap("username", loginDto.getUsername()
                , "password", loginDto.getPassword()));
        if(users.size()>1){
            throw new SystemException(ResultCode.DATABASE_DATA_EXCEPTION);
        }else if(users.size()==0){
            return null;
        }else{
            final User user = users.get(0);
            return jwtUtil.createTokenAndSaveToKy(user.getUsername());
        }
    }

    @Override
    public UserVo getInformation(HttpServletRequest id) {
        return null;
    }

    @Override
    public UserVo modifyInformation(UserDtoByStudent userDtoByStudent) {
        return null;
    }

    @Override
    public UserVo getStudent(Long id) {
        return null;
    }

    @Override
    public UserVo modifyStudent(UserDtoByTeacher userDtoByTeacher) {
        return null;
    }

    @Override
    public Boolean reset(Long id) {
        return null;
    }
}
