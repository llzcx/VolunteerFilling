package com.social.demo.dao.repository;

import com.social.demo.dao.mapper.SysApiMapper;
import com.social.demo.dao.mapper.SysRoleMapper;
import com.social.demo.entity.SysApi;
import com.social.demo.entity.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 权限测试
 */
@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
public class AuthTest {

    @Autowired
    SysApiMapper sysApiMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;


    /**
     * 查询所有的接口资源和对应需要的身份
     * @throws Exception
     */
    @Test
    void selectApisWithRoles() throws Exception {
        final List<SysApi> sysApis = sysApiMapper.selectApisWithRoles();
        System.out.println(sysApis);
    }

    /**
     * 根据用户名获取身份列表
     * @throws Exception
     */
    @Test
    void getRole() throws Exception {
        final List<SysRole> list = sysRoleMapper.selectRoleListByUserName("admin");
        System.out.println(list);
    }
}
