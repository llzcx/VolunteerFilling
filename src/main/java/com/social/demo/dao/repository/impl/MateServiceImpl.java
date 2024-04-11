package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.dao.mapper.AdmissionsMajorMapper;
import com.social.demo.dao.mapper.MateMapper;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.repository.IMateService;
import com.social.demo.data.vo.RankingVo;
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
    @Autowired
    private AdmissionsMajorMapper admissionsMajorMapper;
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
                if(studentMate.getState().equals(0)&&studentMate.getVolunteerList().get(i).getMajorId()!=null){
                    Major major = majorHashMap.get(studentMate.getVolunteerList().get(i).getMajorId());
                    if(major.getEnrollmentNumber()>0){
                        Integer a = major.getEnrollmentNumber();
                        a--;
                        major.setEnrollmentNumber(a);
                        studentMate.setMajorId(major.getMajorId());
                        studentMate.setMajorName(major.getName());
                        studentMate.setState(1);
                        majorHashMap.put(major.getMajorId(),major);
                    }
                }
            }
        }
        List<AdmissionsMajor> admissionsMajors = new ArrayList<>();
        for (Long key : majorHashMap.keySet()) {
            Major value = majorHashMap.get(key);
            if(value.getEnrollmentNumber()>0){
                AdmissionsMajor admissionsMajor = new AdmissionsMajor();
                admissionsMajor.setCollege(value.getCollege());
                admissionsMajor.setName(value.getName());
                admissionsMajor.setEnrollmentNumber(value.getEnrollmentNumber());
                admissionsMajor.setMateWay(1);
                admissionsMajor.setTimeId(timeId);
                admissionsMajors.add(admissionsMajor);
            }
        }
        admissionsMajorMapper.insertBatchSomeColumn(admissionsMajors);
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
    public Long mateJudge(Long timeId, Integer type){
        return mateMapper.mateJudge(timeId,type);
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
                if(student.getState().equals(0)&&student.getVolunteerList().get(i).getMajorId()!=null){
                    Major major = majorHashMap.get(student.getVolunteerList().get(i).getMajorId());
                    if(major.getEnrollmentNumber()>0){
                        Integer a = major.getEnrollmentNumber();
                        a--;
                        major.setEnrollmentNumber(a);
                        student.setMajorId(major.getMajorId());
                        student.setMajorName(major.getName());
                        student.setState(1);
                        majorHashMap.put(major.getMajorId(),major);
                    }
                }
            }
            }
        List<AdmissionsMajor> admissionsMajors = new ArrayList<>();
        for (Long key : majorHashMap.keySet()) {
            Major value = majorHashMap.get(key);
            if(value.getEnrollmentNumber()>0){
                AdmissionsMajor admissionsMajor = new AdmissionsMajor();
                admissionsMajor.setCollege(value.getCollege());
                admissionsMajor.setName(value.getName());
                admissionsMajor.setEnrollmentNumber(value.getEnrollmentNumber());
                admissionsMajor.setMateWay(2);
                admissionsMajor.setTimeId(timeId);
                admissionsMajors.add(admissionsMajor);
            }
        }
        admissionsMajorMapper.insertBatchSomeColumn(admissionsMajors);
        List<Mate> mates = new ArrayList<>();
        for(StudentMate studentMate :studentMates){
            Mate mate = new Mate();
            mate.setMateWay(2);
            mate.setUserId(studentMate.getStudentId());
            mate.setMajorId(studentMate.getMajorId());
            mate.setMajorName(studentMate.getMajorName());
            mate.setTimeId(timeId);
            mates.add(mate);
        }
        mateMapper.insertBatchSomeColumn(mates);
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
    public List<WishResult> getPagingWishResultBySchoolId(Long schoolId, Long timeId,Long current,Long size){
        return studentMapper.getWishResultBySchoolId6(schoolId,timeId,current,size);
    }
    public List<WishResult> getWishResultBySchoolId1(Long schoolId, Long timeId, Integer mateWay,Long current,Long size,Integer type){
        current=(current-1)*size;
        if(type==1){
            return studentMapper.getWishResultBySchoolId1(schoolId,timeId,mateWay,current,size);
        }else {
            return studentMapper.getWishResultBySchoolId3(schoolId,timeId,mateWay,current,size);
        }

    }
    public List<AdmissionsMajor> getAdmissionsMajor(Integer type,Long timeId){
        QueryWrapper<AdmissionsMajor> queryWrapper = Wrappers.query();
        queryWrapper.eq("mate_way",type);
        queryWrapper.eq("time_id",timeId);
        return admissionsMajorMapper.selectList(queryWrapper);
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