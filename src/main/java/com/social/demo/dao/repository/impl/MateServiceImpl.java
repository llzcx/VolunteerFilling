package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.MateMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.mapper.SysApiMapper;
import com.social.demo.dao.repository.IMateService;
import com.social.demo.dao.repository.IStudentService;
import com.social.demo.dao.repository.ISysApiService;
import com.social.demo.data.vo.RankingVo;
import com.social.demo.demo.School;
import com.social.demo.demo.Student;
import com.social.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author 周威宇
 */
@Service
public class MateServiceImpl extends ServiceImpl<MateMapper, Mate> implements IMateService {
    @Autowired
    private MateMapper mateMapper;
    @Autowired
    private StudentMapper studentMapper;
    public  Boolean firstMate(List<RankingVo> rankingVos, List<Major> majors, List<Wish> wishes){
        Long timeId = wishes.get(0).getTimeId();
        List<StudentMate> studentMates = studentMates(rankingVos,wishes);
        HashMap<Long,Major> majorHashMap= new HashMap<>();
        for (Major major:majors) {
            majorHashMap.put(major.getMajorId(),major);
        }
        int volunteersNumber = studentMates.get(0).getVolunteerList().size();
        for(int i=0;i<volunteersNumber;i++){
            for (StudentMate studentMate : studentMates) {
                if(studentMate.getState().equals(0)&&studentMate.getVolunteerList()!=null){
                    Major major = majorHashMap.get(studentMate.getVolunteerList().get(i).getMajorId());
                    if(major.getEnrollmentNumber()>0){
                        Integer a = major.getEnrollmentNumber();
                        a--;
                        major.setEnrollmentNumber(a);
                        studentMate.setMajorId(major.getMajorId());
                        studentMate.setMajorName(major.getName());
                        studentMate.setState(1);
                    }
                }
            }
        }
        List<Mate> mates = new ArrayList<>();
        for(StudentMate studentMate :studentMates){
            Mate mate = new Mate();
            mate.setMateWay(1);
            mate.setUserId(studentMate.getStudentId());
            mate.setMajorId(studentMate.getMajorId());
            mate.setMajorName(studentMate.getMajorName());
            mate.setTimeId(timeId);
            mates.add(mate);
        }
        mateMapper.insertBatchSomeColumn(mates);
    return true;
    }
    public Long mateJudge(Long schoolId, Integer type){
        return mateMapper.mateJudge(schoolId,type);
    }
    public Boolean parallelMate(List<RankingVo> rankingVos, List<Major> majors, List<Wish> wishes){
        Long timeId = wishes.get(0).getTimeId();
        List<StudentMate> studentMates = studentMates(rankingVos,wishes);
        HashMap<Long,Major> majorHashMap= new HashMap<>();
        for (Major major:majors) {
            majorHashMap.put(major.getMajorId(),major);
        }
        int volunteersNumber = studentMates.get(0).getVolunteerList().size();
            for (StudentMate student : studentMates){
                for(int i=0;i<volunteersNumber;i++){
                if(student.getState().equals(0)&&student.getVolunteerList()!=null){
                    Major major = majorHashMap.get(student.getVolunteerList().get(i).getMajorId());
                    if(major.getEnrollmentNumber()>0){
                        Integer a = major.getEnrollmentNumber();
                        a--;
                        major.setEnrollmentNumber(a);
                        student.setMajorId(major.getMajorId());
                        student.setMajorName(major.getName());
                        student.setState(1);
                    }
                }
            }
            }
        for(StudentMate studentMate :studentMates){
            Mate mate = new Mate();
            mate.setMateWay(2);
            mate.setUserId(studentMate.getStudentId());
            mate.setMajorId(studentMate.getMajorId());
            mate.setMajorName(studentMate.getMajorName());
            mate.setTimeId(timeId);
            mateMapper.insert(mate);
        }

        return true;
    }
    public List<WishResult> getWishResultBySchoolId(Long schoolId, Long timeId, Integer mateWay,Integer type){
        if(type==1){
            return studentMapper.getWishResultBySchoolId(schoolId,timeId,mateWay);
        }else {
            return studentMapper.getWishResultBySchoolId2(schoolId,timeId,mateWay);
        }

    }
    public List<WishResult> getWishResultBySchoolId2(Long schoolId, Long timeId){
        return studentMapper.getWishResultBySchoolId5(schoolId,timeId);
    }
    public List<WishResult> getWishResultBySchoolId1(Long schoolId, Long timeId, Integer mateWay,Long current,Long size,Integer type){
        current=(current-1)*size;
        if(type==1){
            return studentMapper.getWishResultBySchoolId1(schoolId,timeId,mateWay,current,size);
        }else {
            return studentMapper.getWishResultBySchoolId3(schoolId,timeId,mateWay,current,size);
        }

    }
    public List<StudentMate> studentMates(List<RankingVo> rankingVos, List<Wish> wishes){
        List<StudentMate> studentMates = new ArrayList<>();
        for(RankingVo rankingVo:rankingVos){
            StudentMate studentMate = new StudentMate();
            studentMate.setScore(rankingVo.getScore());
            studentMate.setStudentId(rankingVo.getUserId());
            studentMate.setState(0);
            for(Wish wish:wishes){
                if(wish.getUserId().equals(rankingVo.getUserId())){
                    List<Major> majors1=new ArrayList<>();
                    Major major = new Major();
                    major.setMajorId(wish.getFirst());
                    major.setName(wish.getFirstName());
                    majors1.add(major);
                    Major major1 = new Major();
                    major1.setMajorId(wish.getSecond());
                    major1.setName(wish.getSecondName());
                    majors1.add(major1);
                    Major major2 = new Major();
                    major2.setMajorId(wish.getThird());
                    major2.setName(wish.getThirdName());
                    majors1.add(major2);
                    studentMate.setVolunteerList(majors1);

                }
            }
            studentMates.add(studentMate);
        }
        return studentMates;
    }
}