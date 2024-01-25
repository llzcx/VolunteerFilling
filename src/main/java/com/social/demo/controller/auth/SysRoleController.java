package com.social.demo.controller.auth;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.Excluded;
import com.social.demo.common.Identity;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.entity.SysRoleVo;
import com.social.demo.entity.User;
import com.social.demo.manager.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 权限-角色与用户操作
 * @author 陈翔
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class SysRoleController {


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserMapper userMapper;



    /**
     * 查询当前登录的用户身份，需要携带token
     * @return
     */
    @GetMapping
    public ApiResp<SysRoleVo> getRole(HttpServletRequest request) {
        final Long userId = jwtUtil.getUserId(request);
        Integer code = userMapper.selectIdentityByUserId(userId);
        return ApiResp.success(new SysRoleVo(code,IdentityEnum.searchByCode(code).getMessage()));
    }

    /**
     * 修改用户的身份
     * @param roleCode 角色id
     * @param request 用户id
     * @return
     */
    @PostMapping("/permission")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<Boolean> saveUserRole(@PathVariable Integer roleCode,HttpServletRequest request) {
        final Long userId = jwtUtil.getUserId(request);
        IdentityEnum identityEnum = IdentityEnum.searchByCode(roleCode);
        userMapper.updateIdentityByUserId(userId,identityEnum.getRoleId());
        return ApiResp.success(true);
    }

    /**
     * 查询一个身份的所有的用户（分页给出）
     *
     * @param roleCode
     * @return
     */
    @GetMapping("/permission/{roleCode}")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<IPage<User>> selectUserRole(@PathVariable Integer roleCode, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        // 构造查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity", roleCode);
        // 调用 selectPage 方法进行分页查询
        IPage<User> resultPage = userMapper.selectPage(page,queryWrapper);
        return ApiResp.success(resultPage);
    }

}

