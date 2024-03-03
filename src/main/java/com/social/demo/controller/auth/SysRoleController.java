package com.social.demo.controller.auth;


import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.cron.pattern.parser.PartParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.*;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.SysRoleApiMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.ISysRoleService;
import com.social.demo.data.vo.IdentityVo;
import com.social.demo.entity.SysApi;
import com.social.demo.entity.SysRoleApi;
import com.social.demo.entity.SysRoleVo;
import com.social.demo.entity.User;
import com.social.demo.manager.security.context.SecurityContext;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    UserMapper userMapper;

    @Autowired
    SysRoleApiMapper sysRoleApiMapper;

    @Autowired
    ISysRoleService sysRoleService;


    /**
     * 查询当前登录的用户身份，需要携带token
     * @return
     */
    @GetMapping("/current")
    public ApiResp<SysRoleVo> getRole() {
        Long userId = SecurityContext.get().getUserId();
        Integer code = userMapper.selectIdentityByUserId(userId);
        return ApiResp.success(new SysRoleVo(code,IdentityEnum.searchByCode(code).getMessage()));
    }

    /**
     * 获取一个身份绑定的所有API
     * @return
     */
    @GetMapping("/apiList/{roleId}")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<List<SysApi>> listRoleApi(@PathVariable Integer roleId){
//        List<SysApi> sysRoleApis = sysRoleApiMapper.apiList(roleId);
//        return ApiResp.success(sysRoleApis);
        throw new SystemException(ResultCode.NO_IMPL);
    }

    /**
     * 修改用户的身份
     * @param roleId 角色id
     * @return
     */
    @PutMapping("/permission/{userId}")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<Boolean> saveUserRole(@PathVariable Long userId, Integer roleId) {
        sysRoleService.saveUserRole(userId, roleId);
        return ApiResp.success(true);
    }

    /**
     * 查询一个身份的所有的用户（分页给出）
     *
     * @param roleId
     * @return
     */
    @GetMapping("/userList/{roleId}")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<IPage<User>> selectUserRole(@PathVariable Integer roleId, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        // 构造查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity", roleId);
        // 调用 selectPage 方法进行分页查询
        IPage<User> resultPage = userMapper.selectPage(page,queryWrapper);
        return ApiResp.success(resultPage);
    }


    /**
     * 获取所有身份和信息
     * @return
     */
    @GetMapping("/allRole")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<List<IdentityVo>> getAllRole() {
        List<IdentityVo> list = new ArrayList<>();
        for (IdentityEnum value : IdentityEnum.values())
            list.add(new IdentityVo(value.getRoleId(),value.getMessage(),value.getDes()));
        return ApiResp.success(list);
    }

}

