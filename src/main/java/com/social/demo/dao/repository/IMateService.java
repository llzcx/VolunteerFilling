package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.vo.RankingVo;
import com.social.demo.entity.*;

import java.util.List;


/**
 * @author 周威宇
 */
public interface IMateService extends IService<Mate> {
    /**
     * 第一志愿优先
     */
    Boolean firstMate(List<RankingVo> rankingVos, List<Major> majors, List<Wish> wishes);
    /**
     * 平行志愿优先
     */
    Boolean parallelMate(List<RankingVo> rankingVos, List<Major> majors, List<Wish> wishes);
    /**
     * 查看是否已经生成志愿
     */
    Long mateJudge(Long timeId,Integer type);
    /**
     * 查看匹配结果
     * @param schoolId
     * @param timeId
     * @param mateWay
     * @return
     */
    List<WishResult1> getWishResultBySchoolId(Long schoolId, Long timeId, Integer mateWay,Integer type);

    /**
     *
     * @param schoolId
     * @param timeId
     * @return
     */
    List<WishResult> getWishResultBySchoolId2(Long schoolId, Long timeId);
    List<WishResult1> getWishResultBySchoolId3(Long schoolId, Long timeId);
    List<WishResult1> getPagingWishResultBySchoolId(Long schoolId, Long timeId,Long current,Long size);
     void updateWishResult(Long timeId,Integer type);
     void updateAdmissionsMajor(Long timeId,Integer type);
    /**
     * 查看匹配结果
     * @param schoolId
     * @param timeId
     * @param mateWay
     * @return
     */
    List<WishResult1> getWishResultBySchoolId1(Long schoolId, Long timeId, Integer mateWay,Long current,Long size,Integer type);
    /**
     * 专业剩余招生人数
     */
    List<AdmissionsMajor> getAdmissionsMajor(Integer type,Long timeId);

}