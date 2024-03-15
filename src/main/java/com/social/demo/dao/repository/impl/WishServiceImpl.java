package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.WishMapper;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.data.dto.ResultDto;
import com.social.demo.data.vo.WishVo;
import com.social.demo.entity.School;
import com.social.demo.entity.Wish;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
      int insertResult = wishMapper.insert(wish);
      insertSuccess = insertResult > 0;
    return insertSuccess;
  }
  @Override
  public List<Wish> selectSchool(Long schoolId,Long timeId){
   return wishMapper.selectSchoolWish(schoolId,timeId);
  }
  /**
   * 修改学生志愿
   */
  @Override
  public Boolean modifyWish(Wish wish){
    int update = wishMapper.update( wish,
            MybatisPlusUtil.queryWrapperEq("user_id",wish.getUserId(),"time_id",wish.getTimeId()));
    return update > 0;
  }
  /**
   * 修改学生志愿
   */
  @Override
  public Boolean modifyWish1(List<ResultDto> resultDtos){
    for(ResultDto resultDto:resultDtos){
       wishMapper.updateWish(resultDto.getUserNumber(),resultDto.getAdmissionResultId(),resultDto.getAdmissionResultName());
    }
    return true;
  }
  /**
   *查询学生志愿
   */
  @Override
  public WishVo selectWish(Long userId,Long timeId){
    return wishMapper.selectWish(userId,timeId);
  }

  @Override
  public  Boolean addWishList(String school){
  return true;
  }
}