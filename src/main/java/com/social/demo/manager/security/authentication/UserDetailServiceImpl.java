package com.social.demo.manager.security.authentication;

import com.social.demo.dao.mapper.SysRoleMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.entity.SysRole;
import com.social.demo.entity.User;
import com.social.demo.util.MybatisPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证service类，获取用户信息（给出UserDetails）
 * @author 陈翔
 */
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user =
                userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("userNumber", username));
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new AccountUserBo(user.getUserId(), user.getUsername(), user.getPassword(), getUserAuthority(username));

    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     * @param username
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(String username) {
        // 根据userId查询用户的权限信息，这里假设从数据库中获取
        List<String> authorities = sysRoleMapper.selectRoleListByUserName(username).stream().
        map(SysRole::getRoleName).collect(Collectors.toList());
        // 将权限信息转换为 GrantedAuthority 对象列表
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        log.debug("该用户身份为:{}",authorities);
        return grantedAuthorities;
    }
}
