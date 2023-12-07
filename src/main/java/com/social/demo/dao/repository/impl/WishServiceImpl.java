package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.WishMapper;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.entity.School;
import com.social.demo.entity.Wish;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 周威宇
 */
@Service
public class WishServiceImpl extends ServiceImpl<WishMapper, Wish> implements IWishService {
  @Autowired
  WishMapper wishMapper;
  /**
   *导入学生志愿
   */
  @Override
  public  Boolean addWish(Wish wish){
    boolean insertSuccess;
    Wish existingWish = wishMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id",wish.getUserId()));
    if (existingWish == null) {
      int insertResult = wishMapper.insert(wish);
      insertSuccess = insertResult > 0;
    } else {
      throw new SystemException(ResultCode.SCHOOL_ALREADY_EXISTS);
    }
    return insertSuccess;
  }

  /**
   * 修改学生志愿
   */
  @Override
  public Boolean modifyWish(Wish wish){
    int update = wishMapper.update( wish,
            MybatisPlusUtil.queryWrapperEq("user_id",wish.getUserId()));
    return update > 0;
  }

  /**
   *查询学生志愿
   */
  @Override
  public Wish selectWish(Long userId){
    return wishMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id",userId));
  }

  @Override
  public  Boolean addWishList(String school){
  return true;
  }
}