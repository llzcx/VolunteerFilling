package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.bo.LoginBo;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.LoginDto;
import com.social.demo.data.dto.ModifyClassDto;
import com.social.demo.data.dto.StudentDto;
import com.social.demo.data.dto.TeacherDto;
import com.social.demo.data.vo.ClassTeacherVo;
import com.social.demo.data.vo.UserVo;
import com.social.demo.util.URLUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户接口
 *
 * @author 陈翔
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 登录
     * @param loginDto
     * @return 登录令牌
     * @throws Exception
     */
    @PostMapping("/login")
    public ApiResp<LoginBo> login(@Valid @RequestBody LoginDto loginDto) throws Exception {
        final LoginBo login = userService.login(loginDto);
        return ApiResp.judge(login !=null, login, ResultCode.PASSWORD_ERROR);
    }

    /**
     * 刷新accessToken
     * @param refreshToken
     * @return
     */
    @GetMapping("/refresh/{refreshToken}")
    public ApiResp<TokenPair> refresh(@PathVariable String refreshToken){
        TokenPair tokenPair = userService.refresh(refreshToken);
        return ApiResp.success(tokenPair);
    }

    /**
     * 退出登录
     * @throws Exception
     */
    @GetMapping("/loginOut")
    public ApiResp<Boolean> loginOut() throws Exception {
        userService.loginOut();
        return ApiResp.success(true);
    }

    /**
     * 批量上传学生信息
     * @param students 学生信息
     * @return 上传成功的学生信息
     */
    @PostMapping("/students")
    public ApiResp<String> importStudents(@RequestBody List<StudentDto> students){
        String s = userService.importStudents(students);
        return ApiResp.judge(s == null,"上传成功",s , ResultCode.USER_IS_EXISTS);
    }

    /**
     * 批量上传老师信息
     * @param teachers 老师信息
     * @return 上传成功的老师信息
     */
    @PostMapping("/teachers")
    public ApiResp<String> importTeacher(@RequestBody List<TeacherDto> teachers){
        String s = userService.importTeachers(teachers);
        return ApiResp.judge(s == null,"上传成功",s , ResultCode.USER_IS_EXISTS);
    }

    /**
     * 搜索用户
     * @param username 用户名
     * @param role 身份
     * @param current 当前页码
     * @param size 每页大小
     * @return
     */
    @GetMapping
    public ApiResp<IPage<UserVo>> getUser(@RequestParam(value = "username", required = false)String username,
                                          @RequestParam(value = "role", required = false)String role,
                                          @RequestParam("current")Long current,
                                          @RequestParam("size")Long size){
        IPage<UserVo> user = userService.getUser(username, role, current, size);
        return ApiResp.judge(user != null, user, ResultCode.USER_NOT_EXISTS);
    }

    /**
     * 重置密码
     * @param userNumbers 被重置密码用户学号
     * @return
     */
    @PutMapping("/reset")
    public ApiResp<String> reset(@RequestBody String[] userNumbers){
        userService.reset(userNumbers);
        return ApiResp.success("操作成功");
    }

    /**
     * 删除用户
     * @param userNumbers 被删除用户学号
     * @return
     */
    @DeleteMapping
    public ApiResp<String> deleteUser(@RequestBody String[] userNumbers){
        String s = userService.deleteUser(userNumbers);
        return ApiResp.judge( s == null, "操作成功",s , ResultCode.USER_HAVE_CLASS);
    }

    /**
     * 获取老师列表
     * @return
     */
    @GetMapping("/teachers")
    public ApiResp<List<ClassTeacherVo>> getClassTeachers(){
        List<ClassTeacherVo> teachers = userService.getTeachers();
        return ApiResp.success(teachers);
    }

    /**
     * 上传头像
     * @param request
     * @param file 文件
     * @return
     */
    @PostMapping("/headshot")
    public ApiResp<String> uploadHeadshot(HttpServletRequest request, MultipartFile file) throws Exception {
        return ApiResp.success(URLUtil.getPictureUrl(request) + userService.uploadHeadshot(request, file));
    }

    /**
     * 修改密码
     * @param request
     * @param password
     * @return
     */
    @PutMapping("/password")
    public ApiResp<String> modifyPassword(HttpServletRequest request,
                                          @RequestBody String password){
        Boolean b = userService.modifyPassword(request, password);
        return ApiResp.judge(b, "修改成功", ResultCode.PARAM_NOT_VALID);
    }

    /**
     * 修改学生班级信息
     * @return
     */
    @PutMapping("/class")
    public ApiResp<String> modifyClass(ModifyClassDto modifyClassDto){
        Boolean b = userService.modifyClass(modifyClassDto);
        return ApiResp.judge(b, "修改成功", ResultCode.PARAM_NOT_VALID);
    }
}
