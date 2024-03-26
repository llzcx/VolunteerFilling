package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.JsonUtil;
import com.social.demo.dao.mapper.SchoolMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.repository.IStudentService;
import com.social.demo.dao.repository.IWishService;
import com.social.demo.data.bo.GradeSubjectBo;
import com.social.demo.data.vo.RankingVo;
import com.social.demo.data.vo.StudentVo;
import com.social.demo.entity.Ranking;
import com.social.demo.entity.Student;
import com.social.demo.entity.Wish;
import com.social.demo.util.MybatisPlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/26 19:36
 * @description StudentServiceImpl
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService{

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    SchoolMapper schoolMapper;
    @Autowired
    private IWishService wishService;
    @Override
    public Boolean modifyState(Long schoolId, Integer year, Integer state,Long id) {
        Student student = new Student();
        student.setState(state);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id",schoolId);
        queryWrapper.eq("enrollment_year",year);
        List<Student> studentList =studentMapper.selectList(queryWrapper);
        for(Student student1:studentList){
            Wish wish = new Wish();
            wish.setUserId(student1.getUserId());
            wish.setTimeId(id);
            wish.setFrequency(3);
            wishService.addWish(wish);
        }
        int update = studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("school_id", schoolId, "enrollment_year", year));
        return update > 0;
    }
    @Override
    public Student getStudent(Long userId){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return studentMapper.selectOne(queryWrapper);
    }
    @Override
    public List<RankingVo> getRanking(Integer type,Student student){
        List<Ranking> rankings = new ArrayList<>();
        if(type==1){
        rankings=studentMapper.getRanking(student.getEnrollmentYear());
        }else if(type==2){
        rankings=studentMapper.getClassRanking(student.getClassId());
        }else if(type==3){
        rankings=studentMapper.getSchoolRanking(student.getSchoolId(),student.getEnrollmentYear());
        }
        sort(rankings);
        return getRankingVo(rankings);
    }
    @Override
    public List<RankingVo> getRanking1(Student student,Long timeId,Long majorId){
        List<Ranking> rankings;
        rankings = studentMapper.getMajorRanking(timeId,majorId);
        Ranking ranking = new Ranking();
        ranking.setScore(student.getScore());
        ranking.setGrade(student.getGrade());
        ranking.setUserId(student.getUserId());
        rankings.add(ranking);
        sort(rankings);
        return getRankingVo(rankings);
    }
    public  List<RankingVo> getRankingVo(List<Ranking> rankings){
        int s = rankings.size();
        List <RankingVo> rankingVos = new ArrayList<>();
        Ranking ranking = rankings.get(0);
        RankingVo rankingVo = new RankingVo();
        rankingVo.setScore(ranking.getScore());
        rankingVo.setGrades(ranking.getGrade());
        rankingVo.setRanking(1);
        rankingVo.setUserId(ranking.getUserId());
        rankingVos.add(rankingVo);
        for(int i=1;i<rankings.size();i++){
            RankingVo rankingVo1 = new RankingVo();
            rankingVo1.setScore(rankings.get(i).getScore());
            rankingVo1.setGrades(rankings.get(i).getGrade());
            rankingVo1.setRanking(i+1);
            rankingVo1.setUserId(rankings.get(i).getUserId());
            rankingVo1.setRankings(s);
            rankingVos.add(rankingVo1);
            if(rankings.get(i).getScore().equals(rankings.get(i-1).getScore())){
                List<GradeSubjectBo>gradeSubjectBos1 =JsonUtil.ListJson(rankings.get(i-1).getGrade(),GradeSubjectBo.class);
                List<GradeSubjectBo>gradeSubjectBos2 =JsonUtil.ListJson(rankings.get(i).getGrade(),GradeSubjectBo.class);
                for(int j=0;j<3;j++){
                    if(gradeSubjectBos1.get(j).getGrade()>gradeSubjectBos2.get(j).getGrade()){
                        rankingVos.get(i-1).setRanking(i+1);
                        rankingVos.get(i).setRanking(i);
                        break;
                    }
                }
            }
        }
        return rankingVos;
    }

    @Override
    public IPage<StudentVo> getStudentHistory(Integer year, String className, String keyword) {
        
        return null;
    }

    public static void sort(List<Ranking> rankings) {
        Collections.sort(rankings, new Comparator<Ranking>() {
            @Override
            public int compare(Ranking r1, Ranking r2) {
                return Double.compare(r2.getScore(), r1.getScore());
            }
        });
    }
}
