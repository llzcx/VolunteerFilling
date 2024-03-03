package com.social.demo.dao.repository;

import cn.hutool.json.JSONUtil;
import com.social.demo.dao.mapper.StudentMapper;
import com.social.demo.dao.mapper.SysApiMapper;
import com.social.demo.data.bo.GradeSubjectBo;
import com.social.demo.entity.Student;
import com.social.demo.entity.SysApi;
import com.social.demo.util.MybatisPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

/**
 * 权限测试
 */
@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
public class AuthTest {

    @Autowired
    SysApiMapper sysApiMapper;


    @Autowired
    StudentMapper studentMapper;


    /**
     * 查询所有的接口资源和对应需要的身份
     * @throws Exception
     */
    @Test
    void selectApisWithRoles() throws Exception {
        final List<SysApi> sysApis = sysApiMapper.selectApisWithRoles();
        System.out.println(sysApis);
    }

    /**
     * 生成随机成绩
     * @throws Exception
     */
    @Test
    void gradeTest() throws Exception {
        for (int i = 913; i <= 968; i++) {
            GradeSubjectBo[] a = new GradeSubjectBo[7];
            for (int j = 1; j <= 6; j++) {
                a[j] = new GradeSubjectBo();
                a[j].setGradeId((long) j);
                Random rand = new Random();
                int randomNum = rand.nextInt(41) + 60;
                a[j].setGrade(randomNum);
            }
            Student student = new Student();
            student.setGrade(JSONUtil.toJsonStr(a));
            studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", i));
        }
    }

    @Test
    void scoreTest() throws Exception {
        for (int i = 913; i <= 968; i++) {
            Student student = new Student();
            Random rand = new Random();
            int randomNum = rand.nextInt(41) + 60;
            student.setScore(randomNum);
            studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", i));
        }
    }
}
