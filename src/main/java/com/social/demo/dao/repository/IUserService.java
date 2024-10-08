package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.social.demo.data.bo.LoginBo;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.*;
import com.social.demo.data.vo.*;
import com.social.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author 陈翔
 */
public interface IUserService extends IService<User> {


    /**
     * 退出登录
     */
    void loginOut();


    /**
     * 登录
     *
     * @param loginDto
     * @return token 登录令牌
     */
    LoginBo login(LoginDto loginDto);

    /**
     * 获取用户信息-学生
     * @param request 用户id
     * @return 用户信息
     */
    StudentVo getInformationOfStudent(HttpServletRequest request) throws UnknownHostException;

    /**
     * 修改个人信息-学生
     *
     * @param request
     * @param userDtoByStudent 用户修改后的数据
     * @return 是否修改成功
     */
    Boolean modifyInformation(HttpServletRequest request, UserDtoByStudent userDtoByStudent);

    /**
     * 老师获取学生个人信息
     *
     * @param number 学号
     * @return 学生个人信息
     */
    StudentVo getStudent(String number) throws UnknownHostException;

    /**
     * 修改学生个人信息
     *
     * @param userDtoByTeacher 老师上传的学生修改信息
     * @return 学生个人信息
     */
    Boolean modifyStudent(UserDtoByTeacher userDtoByTeacher) throws IllegalAccessException;

    /**
     * 重置学生密码
     *
     * @param userNumbers 学号
     * @return 是否操作成功
     */
    Boolean reset(String[] userNumbers);

    /**
     * 获取用户信息-老师
     *
     * @param request
     * @return 老师用户信息
     */
    TeacherVo getInformationOfTeacher(HttpServletRequest request);

    /**
     * 老师修改电话号码
     *
     * @param request
     * @param phone   电话号码
     * @return 修改后的学生信息
     */
    Boolean modifyPhone(HttpServletRequest request, String phone);

    /**
     * 根据目标学校和入学时间获取学生信息
     * @param school
     * @param time
     * @return
     */
    List<User> getUserBySchoolAndTime(String school, Integer time);

    /**
     * 批量上传学生信息
     *
     * @param students
     * @return
     */
    String importStudents(List<StudentDto> students);

    /**
     * 刷新token
     * @param refreshToken
     * @return
     */
    TokenPair refresh(String refreshToken);

    /**
     * 批量上传老师信息
     *
     * @param teachers
     * @return
     */
    String importTeachers(List<TeacherDto> teachers);

    /**
     * 获取用户信息(暂未实现用户角色查询)
     *
     * @param username   用户姓名
     * @param role       用户角色
     * @param current
     * @param size
     * @return
     */
    IPage<UserVo> getUser(String username, String role, Long current, Long size);

    String deleteUser(String[] userNumbers);

    List<ClassTeacherVo> getTeachers();

    Boolean modifyPassword(HttpServletRequest request, String password);

    String uploadHeadshot(HttpServletRequest request, MultipartFile file) throws Exception;

    Boolean modifyClass(ModifyClassDto modifyClassDto);

    String adminUploadHeadshot(Long userId, MultipartFile file) throws Exception;

    Boolean modifyStudentForAdmin(UserDtoByAdmin userDtoByAdmin);

    AdminVo getAdminMessage(HttpServletRequest request);

    StudentForAdminVo getStudentForAdmin(String userNumber) throws UnknownHostException;
}
