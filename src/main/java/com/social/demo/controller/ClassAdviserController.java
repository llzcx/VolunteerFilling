package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.dao.repository.IClassAdviserService;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.dto.UserDtoByTeacher;
import com.social.demo.data.vo.ClassMemberVo;
import com.social.demo.data.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 班主任接口
 *
 * @author 杨世博
 * @date 2023/12/7 21:18
 * @description ClassAdviserController
 */
@RestController
@RequestMapping("/adviser")
@Validated
public class ClassAdviserController {
    @Autowired
    IClassAdviserService classAdviserService;

    @Autowired
    IUserService userService;

    /**
     * 班级成员列表
     * @param classId 班级id
     * @param userNumber 学号
     * @param username 姓名
     * @param role 班级职称
     * @param current 当前页数
     * @param size 每页大小
     * @return 班级成员列表
     */
    @GetMapping("/students")
    public ApiResp<IPage<ClassMemberVo>> getStudents(@RequestParam("classId") Long classId,
                                                     @RequestParam("userNumber")String userNumber,
                                                     @RequestParam("username")String username,
                                                     @RequestParam("role")Integer role,
                                                     @RequestParam("current")Integer current,
                                                     @RequestParam("size")Integer size){
        IPage<ClassMemberVo> classMemberVoIPage = classAdviserService.getStudents(classId, userNumber, username, role, current, size);
        return ApiResp.success(classMemberVoIPage);
    }

    /**
     * 班主任获取学生个人信息
     * @param number 学生学号
     * @return 学生个人信息
     */
    @GetMapping("/student")
    public ApiResp<StudentVo> getStudent(@RequestParam("number")String number){
        StudentVo user = userService.getStudent(number);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 班主任修改学生个人信息
     * @param userDtoByTeacher 修改的学生信息
     * @return 学生个人信息
     */
    @PutMapping("/student")
    public ApiResp<String> modifyStudent(@RequestBody UserDtoByTeacher userDtoByTeacher){
        Boolean b = userService.modifyStudent(userDtoByTeacher);
        return ApiResp.judge(b, "修改成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }
}
