package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.AdmissionsMajorMapper;
import com.social.demo.dao.mapper.MateMapper;
import com.social.demo.dao.mapper.WishMapper;
import com.social.demo.dao.mapper.WishTimeMapper;
import com.social.demo.dao.repository.IStudentService;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.dao.repository.IWishTimeService;
import com.social.demo.data.vo.NotAcceptedVo;
import com.social.demo.data.vo.NotAcceptedVos;
import com.social.demo.data.vo.WishTimeVo1;
import com.social.demo.entity.AdmissionsMajor;
import com.social.demo.entity.WishTime;
import com.social.demo.util.MybatisPlusUtil;
import org.checkerframework.checker.units.qual.N;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @author 周威宇
 */
@Service
public class WishTimeServiceImpl extends ServiceImpl<WishTimeMapper, WishTime> implements IWishTimeService {
  @Autowired
  WishTimeMapper wishTimeMapper;
  @Autowired
  WishMapper wishMapper;
  @Autowired
  MateMapper mateMapper;
  @Autowired
  AdmissionsMajorMapper admissionsMajorMapper;
  @Autowired
  private IStudentService studentService;


  @Autowired
  private IWishService wishService;

  /**
   *添加志愿填报时间
   */
  @Override
  public  Boolean addWishTime(WishTime wishTime){
    int insertResult = wishTimeMapper.insert(wishTime);
    Long id = wishTime.getId();
    studentService.modifyState(wishTime.getSchoolId(),wishTime.getAgo(),1,id);
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
  @Override
  public List<WishTimeVo1> selectWishTime2(Long userId){
    return wishTimeMapper.selectWishTime(userId);
  }
  /**
   * 删除志愿填报时间
   * @param id
   * @return
   */
  @Override
  @Transactional
  public Boolean deleteWishTime(Long id){
    int delete = wishTimeMapper.delete(MybatisPlusUtil.queryWrapperEq("id",id));
    int delete1 = wishMapper.delete(MybatisPlusUtil.queryWrapperEq("time_id",id));
    int delete2 = mateMapper.delete(MybatisPlusUtil.queryWrapperEq("time_id",id));
    int delete3 = admissionsMajorMapper.delete(MybatisPlusUtil.queryWrapperEq("time_id",id));

    return delete > 0;
  }
  /**
   * 查看志愿时间年份
   */
  public Integer selectAgo(Long timeId){
    QueryWrapper<WishTime> queryWrapper = Wrappers.query();
    queryWrapper.eq("id",timeId);
    WishTime wishTime = wishTimeMapper.selectOne(queryWrapper);
    return  wishTime.getAgo();
  }
  public WishTime selectWishTime3(Long timeId){
    QueryWrapper<WishTime> queryWrapper = Wrappers.query();
    queryWrapper.eq("id",timeId);
    WishTime wishTime = wishTimeMapper.selectOne(queryWrapper);
    return wishTime;
  }
  public List<NotAcceptedVos> selectNotAccepted(Long timeId){
    List<NotAcceptedVo> notAcceptedVoList = wishTimeMapper.selectNotAccepted(timeId);
    HashMap<String,List<NotAcceptedVo>> hashMap = new HashMap<>();
    for(NotAcceptedVo notAcceptedVo:notAcceptedVoList){
      List<NotAcceptedVo> notAcceptedVoList1;
      if(hashMap.containsKey(notAcceptedVo.getClassName())){
        notAcceptedVoList1 = hashMap.get(notAcceptedVo.getClassName());
      }else {
        notAcceptedVoList1 = new ArrayList<>();
      }
      notAcceptedVoList1.add(notAcceptedVo);
      hashMap.put(notAcceptedVo.getClassName(),notAcceptedVoList1);
    }
    List<NotAcceptedVos> notAcceptedVos = new ArrayList<>();
    for (Map.Entry<String, List<NotAcceptedVo>> entry : hashMap.entrySet()) {
      NotAcceptedVos notAcceptedVos1 = new NotAcceptedVos();
      notAcceptedVos1.setClassName(entry.getKey());
      notAcceptedVos1.setNotAcceptedVoList(entry.getValue());
      notAcceptedVos.add(notAcceptedVos1);
     }
    return notAcceptedVos;
  }
}