package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.entity.SysApi;

import java.util.List;
import java.util.Set;


/**
 * @author 陈翔
 */
public interface ISysApiService extends IService<SysApi> {

    Boolean addApis(Set<Long> apis, Integer roleCode);

    List<SysApi> selectApisWithRoles();
}