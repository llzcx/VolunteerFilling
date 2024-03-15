package com.social.demo.controller.auth;

import com.social.demo.common.*;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.mapper.SysRoleApiMapper;
import com.social.demo.dao.repository.ISysApiService;
import com.social.demo.entity.SysApi;
import com.social.demo.entity.SysRoleApi;
import com.social.demo.manager.security.identity.Excluded;
import com.social.demo.manager.security.identity.Identity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 权限-角色与接口操作
 * @author 陈翔
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class SysApiController {

    @Autowired
    ISysApiService apiService;

    @Autowired
    SysRoleApiMapper sysRoleApiMapper;

    /**
     * 获取所有api
     * @return
     */
    @GetMapping("/list")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<List<SysApi>> listApis(){
        return ApiResp.success(apiService.selectApisWithRoles());
    }


    /**
     * 为身份绑定api【重复绑定会报错】
     * @param apis 需要绑定的接口
     * @param roleId 被绑定的身份
     * @return
     */
    @PostMapping("/add/{roleId}")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<Boolean> addApis(@RequestBody Set<Long> apis, @PathVariable Integer roleId){
        if(roleId.equals(IdentityEnum.SUPER.getRoleId())){
            throw new SystemException(ResultCode.SUPER_PERMISSION_CANT_CHANGE);
        }
        return ApiResp.judge(apiService.addApis(apis,Integer.valueOf(roleId)),true, ResultCode.COMMON_FAIL);
    }

    /**
     * 为身份解绑一条API【不存在这条绑定记录会报错】
     * @param roleApiId 绑定关系的id
     * @return
     */
    @DeleteMapping("/delete/{roleApiId}")
    @Identity(IdentityEnum.SUPER)
    @Excluded
    public ApiResp<Boolean> deleteRoleApi(@PathVariable String roleApiId){
        SysRoleApi sysRoleApi = sysRoleApiMapper.selectById(roleApiId);
        Integer roleId = sysRoleApi.getRoleId();
        if(roleId.equals(IdentityEnum.SUPER.getRoleId())){
            throw new SystemException(ResultCode.SUPER_PERMISSION_CANT_CHANGE);
        }
        sysRoleApiMapper.deleteById(roleApiId);
        return ApiResp.success(true);
    }


}
