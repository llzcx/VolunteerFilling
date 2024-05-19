package com.social.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.social.demo.data.bo.UserMessageBo;
import com.social.demo.entity.*;
import com.social.demo.data.bo.GradeBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/12 20:09
 * @description StudentMapper
 */
@Mapper
public interface StudentMapper extends EasyBaseMapper<Student> {
    Long selectClassIdByUserNumber(String number);

    /**
     * 查看一个班的志愿
     * @param classId
     * @return
     */
    List<WishClass> getUserIdByClassId1(Long classId, Long timeId,Long current,Long size);
    List<Long> getUserIdByClassId(Long classId);
    List<WishResult1> getWishResultBySchoolId(Long schoolId,Long timeId,Integer mateWay);
    List<WishResult1> getWishResultBySchoolId2(Long schoolId,Long timeId,Integer mateWay);
    List<WishResult> getWishResultBySchoolId5(Long schoolId,Long timeId);
    List<WishResult1> getWishResultBySchoolId7(Long schoolId, Long timeId);
    List<WishResult1> getWishResultBySchoolId6(Long schoolId,Long timeId,Long current,Long size);
    List<WishResult1> getWishResultBySchoolId1(Long schoolId,Long timeId,Integer mateWay,Long current,Long size);
    List<WishResult1> getWishResultBySchoolId3(Long schoolId,Long timeId,Integer mateWay,Long current,Long size);
    Long getClass1(Long classId, Long timeId);
    Long selectClassIdByUserId(Long userId);

    /**
     * 查看全部学生成绩
     * @return
     */
    List<Ranking> getRanking(Integer enrollmentYear);
    /**
     * 查看同班学生成绩
     */
    List<Ranking> getClassRanking(Long classId);
    /**
     * * 查看同校学生成绩
     */
    List<Ranking> getSchoolRanking(Long schoolId,Integer ago);

    List<Ranking> getMajorRanking(Long timeId,Long majorId);

    List<GradeBo> getGrades(Integer year, String keyword, Integer number1, Integer number2);

    Integer getGradesCount(Integer year, String keyword);

    void deleteGrades(String userNumber);

    List<GradeBo> getAllGrades(Integer year);

    String selectClassNameByUserId(Long userId);

    List<String> selectUserNumberByClass(Long classId);

    List<String> selectUserNumberByClassYear(Long classId, Integer year);

    List<UserMessageBo> selectUserMessageByClassYear(Long classId, Integer year);

    Long selectCountByClassAndKeyword(Long classId, String keyword);
}
