package com.social.demo.controller.auth;

import com.social.demo.common.ApiResp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 获取api列表
     * @return
     */
    @GetMapping("")
    public ApiResp<Object> listApis(){
        return ApiResp.success();
    }


    /**
     * 添加多条 role -> api
     * @return
     */
    @PostMapping("/{roleId}")
    public ApiResp<Object> listApis(@RequestBody Set<Long> apis, @PathVariable String roleId){

        return ApiResp.success();
    }

    /**
     * 删除一条 role -> api
     * @return
     */
    @PostMapping("/{roleApiId}")
    public ApiResp<Object> deleteRoleApi(@PathVariable String roleApiId){

        return ApiResp.success();
    }


    /**
     * 获取一个role所有的 role -> api
     * @return
     */
    @GetMapping("/{roleId}")
    public ApiResp<Object> listRoleApi(@PathVariable String roleId){

        return ApiResp.success();
    }
}
