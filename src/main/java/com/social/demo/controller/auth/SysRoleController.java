package com.social.demo.controller.auth;


import com.social.demo.common.ApiResp;
import com.social.demo.dao.repository.ISysRoleService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.SaveSysRoleDto;
import com.social.demo.entity.SysRole;
import com.social.demo.manager.security.authentication.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    ISysRoleService sysRoleService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    IUserService userService;


    /**
     * 根据用户id获取身份
     *
     * @return
     */
    @GetMapping
    public ApiResp<List<SysRole>> getRole(HttpServletRequest request) {
        final Long userId = jwtUtil.getSubject(request);
        final List<SysRole> list = sysRoleService.getRoleByUserId(userId);
        return ApiResp.success(list);
    }

    /**
     * 添加一种身份
     *
     * @param saveSysRoleDto
     * @return
     */
    @PostMapping
    public ApiResp<Object> saveRole(@RequestBody SaveSysRoleDto saveSysRoleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(saveSysRoleDto, sysRole);
        sysRoleService.save(sysRole);
        return ApiResp.success();
    }

    /**
     * 删除一种身份
     * @param roleId 身份id
     * @return
     */
    @DeleteMapping("/{roleId}")
    public ApiResp<Boolean> deleteRole(@PathVariable String roleId) {
        sysRoleService.removeById(roleId);
        return ApiResp.success(true);
    }

    /**
     * 添加一条role -> user
     * @param roleId 角色id
     * @param userId 用户id
     * @return
     */
    @PostMapping("/permission")
    public ApiResp<Boolean> saveUserRole(@PathVariable Long roleId, @PathVariable Long userId) {

        return ApiResp.success(true);
    }

    /**
     * 删除一条role -> user
     * @param userRoleId
     * @return
     */
    @DeleteMapping("/permission/{userRoleId}")
    public ApiResp<Object> deleteUserRole(@PathVariable String userRoleId) {

        return ApiResp.success();
    }

    /**
     * 查询一个身份的所有的role -> user （分页给出）
     *
     * @param roleId
     * @return
     */
    @GetMapping("/permission/{roleId}")
    public ApiResp<Object> selectUserRole(@PathVariable Long roleId) {
        // Your implementation to save permissions for roleId
        return ApiResp.success();
    }

}

