package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.WishTimeMapper;
import com.social.demo.dao.repository.IStudentService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.dao.repository.IWishTimeService;
import com.social.demo.entity.User;
import com.social.demo.entity.Wish;
import com.social.demo.entity.WishTime;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author 周威宇
 */
@Service
public class WishTimeServiceImpl extends ServiceImpl<WishTimeMapper, WishTime> implements IWishTimeService {
  @Autowired
  WishTimeMapper wishTimeMapper;

  @Autowired
  private IStudentService studentService;

  @Autowired
  private IWishService wishService;

  /**
   *添加志愿填报时间
   */
  @Override
  public  Boolean addWishTime(WishTime wishTime){
    studentService.modifyState(wishTime.getSchoolId(),wishTime.getAgo(),1);
    int insertResult = wishTimeMapper.insert(wishTime);
    return insertResult > 0;
  }

  /**
   * 修改志愿填报时间
   * @param wishTime 志愿填报时间
   * @return
   */
  @Override
  public Boolean modifyWishTime(WishTime wishTime){
    int update = wishTimeMapper.update( wishTime,
            MybatisPlusUtil.queryWrapperEq("id",wishTime.getId()));
    return update > 0;
  }

  /**
   * 根据学校编码查志愿时间
   * @param schoolId 学校编码
   * @param current
   * @param size
   * @return
   */

  @Override
  public IPage<WishTime> selectWishTime(Integer schoolId, Long current, Long size){
    QueryWrapper<WishTime> queryWrapper = Wrappers.query();
    queryWrapper.eq("school_id",schoolId);
    // 执行查询
    return wishTimeMapper.selectPage(new Page<>(current,size),queryWrapper);
  }

  /**
   * 根据入学时间查志愿时间
   * @param schoolId 学校编码
   * @param ago
   * @param current
   * @param size
   * @return
   */
  @Override
  public IPage<WishTime> selectWishTime1(Integer schoolId,Integer ago,Long current, Long size){
    QueryWrapper<WishTime> queryWrapper = Wrappers.query();
    queryWrapper.eq("school_id",schoolId);
    queryWrapper.eq("ago",ago);
    // 执行查询
    return wishTimeMapper.selectPage(new Page<>(current,size),queryWrapper);
  }
  /**
   * 删除志愿填报时间
   * @param id
   * @return
   */
  @Override
  public Boolean deleteWishTime(Long id){
    int delete = wishTimeMapper.delete(MybatisPlusUtil.queryWrapperEq("id",id));
    return delete > 0;
  }
}