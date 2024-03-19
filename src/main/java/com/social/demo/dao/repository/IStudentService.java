package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.vo.RankingVo;
import com.social.demo.data.vo.StudentVo;
import com.social.demo.entity.Ranking;
import com.social.demo.entity.Student;

import java.util.List;

public interface IStudentService extends IService<Student> {
    Boolean modifyState(Long schoolName, Integer year, Integer state,Long id);
    Student getStudent(Long userId);

    /**
     * 查看学生排名
     * @return
     */
    List<RankingVo> getRanking(Integer type, Student student);

    /**
     * 查看学生专业排名
     * @return
     */
    List<RankingVo> getRanking1(Student student,Long majorId);

    IPage<StudentVo> getStudentHistory(Integer year, String className, String keyword);
}
