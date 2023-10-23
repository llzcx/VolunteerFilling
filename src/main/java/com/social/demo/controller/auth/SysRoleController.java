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
import java.util.Set;

/**
 * @author 陈翔
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/role")
public class SysRoleController {

    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    IUserService userService;


    /**
     * 根据用户id获取身份
     * @return
     */
    @GetMapping
    public ApiResp<List<SysRole>> getRole(HttpServletRequest request) {
        final String username = jwtUtil.getSubject(request);
        final List<SysRole> list = sysRoleService.getRoleByUserId(username);
        return ApiResp.success(list);
    }

    /**
     * 添加一种身份
     * @param saveSysRoleDto
     * @return
     */
    @PostMapping
    public ApiResp<Object> saveRole(@RequestBody SaveSysRoleDto saveSysRoleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(saveSysRoleDto,sysRole);
        sysRoleService.save(sysRole);
        return ApiResp.success();
    }

    /**
     * 删除一种身份
     * @param roleName
     * @return
     */
    @DeleteMapping("/{roleName}")
    public ApiResp<Object> deleteRole(@PathVariable String roleName) {
        sysRoleService.deleteRole(roleName);
        return ApiResp.success();
    }

    @GetMapping("/permission/{roleId}")
    public ApiResp<Object> getPermission(@PathVariable Long roleId) {
        // Your implementation to get permissions by roleId
        return ApiResp.success();
    }

    @PostMapping("/{roleId}/permission")
    public ApiResp<Object> savePermission(@PathVariable Long roleId, @RequestBody Set<Long> menus) {
        // Your implementation to save permissions for roleId
        return ApiResp.success();
    }
}

