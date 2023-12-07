package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.WishTimeMapper;
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
  private IUserService userService;

  @Autowired
  private IWishService wishService;

  /**
   *添加志愿填报时间
   */
  @Override
  public  Boolean addWishTime(WishTime wishTime){
    int currentYear = LocalDate.now().getYear() - 1;
    Long id = Uuid.getUUID();
    wishTime.setId(id);
    List<User> students = userService.getUserBySchoolAndTime(wishTime.getSchool(),currentYear);
    for (User student : students) {
      Wish wish = new Wish();
      wish.setUserId(student.getUserId());
      wish.setTimeId(id);
      wishService.addWish(wish);
    }
    int insertResult = wishTimeMapper.insert(wishTime);
    return insertResult > 0;
  }

  @Override
  public Boolean modifyWishTime(WishTime wishTime){

    int update = wishTimeMapper.update( wishTime,
            MybatisPlusUtil.queryWrapperEq("id",wishTime.getId()));
    return update > 0;
  }
  @Override
  public List<WishTime> selectWishTime(Integer ago){
    QueryWrapper<WishTime> queryWrapper = Wrappers.query();
    queryWrapper.between("start_time", LocalDateTime.of(ago, 1, 1, 0, 0, 0),
            LocalDateTime.of(ago, 12, 31, 23, 59, 59));
    // 执行查询
    List<WishTime> result = wishTimeMapper.selectList(queryWrapper);
    return result;
  }
}