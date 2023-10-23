package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.SysApiMapper;
import com.social.demo.dao.repository.ISysApiService;
import com.social.demo.entity.SysApi;
import org.springframework.stereotype.Service;

/**
 *
 * @author 陈翔
 */
@Service
public class SysApiServiceImpl extends ServiceImpl<SysApiMapper, SysApi> implements ISysApiService {

}