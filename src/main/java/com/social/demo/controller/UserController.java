package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.constant.RegConstant;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.bo.LoginBo;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.bo.UserMessageBo;
import com.social.demo.data.dto.*;
import com.social.demo.data.vo.*;
import com.social.demo.manager.security.identity.Identity;
import com.social.demo.util.URLUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    URLUtil urlUtil;

    /**
     * 登录
     * @param loginDto
     * @return 登录令牌
     * @throws Exception
     */
    @PostMapping("/login")
    @Identity({IdentityEnum.SUPER, IdentityEnum.APPRAISAL_TEAM, IdentityEnum.STUDENT, IdentityEnum.TEACHER, IdentityEnum.CLASS_ADVISER})
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
    @Identity({IdentityEnum.SUPER, IdentityEnum.APPRAISAL_TEAM, IdentityEnum.STUDENT, IdentityEnum.TEACHER, IdentityEnum.CLASS_ADVISER})
    public ApiResp<TokenPair> refresh(@PathVariable String refreshToken){
        TokenPair tokenPair = userService.refresh(refreshToken);
        return ApiResp.success(tokenPair);
    }

    /**
     * 退出登录
     * @throws Exception
     */
    @GetMapping("/loginOut")
    @Identity({IdentityEnum.SUPER, IdentityEnum.APPRAISAL_TEAM, IdentityEnum.STUDENT, IdentityEnum.TEACHER, IdentityEnum.CLASS_ADVISER})
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
    @Identity(IdentityEnum.SUPER)
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
    @Identity(IdentityEnum.SUPER)
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
    @Identity(IdentityEnum.SUPER)
    public ApiResp<IPage<UserVo>> getUser(@RequestParam(value = "username", required = false)@Pattern(regexp = RegConstant.KEYWORD)String username,
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
    @Identity({IdentityEnum.SUPER, IdentityEnum.CLASS_ADVISER})
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
    @Identity(IdentityEnum.SUPER)
    public ApiResp<String> deleteUser(@RequestBody String[] userNumbers){
        String s = userService.deleteUser(userNumbers);
        return ApiResp.judge( s == null, "操作成功",s , ResultCode.USER_HAVE_CLASS);
    }

    /**
     * 获取老师列表
     * @return
     */
    @GetMapping("/teachers")
    @Identity(IdentityEnum.SUPER)
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
    @Identity({IdentityEnum.SUPER, IdentityEnum.APPRAISAL_TEAM, IdentityEnum.STUDENT, IdentityEnum.TEACHER, IdentityEnum.CLASS_ADVISER})
    public ApiResp<String> uploadHeadshot(HttpServletRequest request, MultipartFile file) throws Exception {
        return ApiResp.success(urlUtil.getUrl(userService.uploadHeadshot(request, file)));
    }

    /**
     * 管理员修改用户头像
     * @param file 文件
     * @return
     */
    @PostMapping("/admin/headshot")
    @Identity({IdentityEnum.SUPER})
    public ApiResp<String> adminUploadHeadshot(Integer userId, MultipartFile file) throws Exception {
        return ApiResp.success(urlUtil.getUrl(userService.adminUploadHeadshot(Long.valueOf(userId), file)));
    }

    /**
     * 修改密码
     * @param request
     * @param password
     * @return
     */
    @PutMapping("/password")
    @Identity({IdentityEnum.SUPER, IdentityEnum.APPRAISAL_TEAM, IdentityEnum.STUDENT, IdentityEnum.TEACHER, IdentityEnum.CLASS_ADVISER})
    public ApiResp<String> modifyPassword(HttpServletRequest request,
                                          @RequestBody PasswordDto password){
        Boolean b = userService.modifyPassword(request, password.getPassword());
        return ApiResp.judge(b, "修改成功", ResultCode.PARAM_NOT_VALID);
    }

    /**
     * 修改学生班级信息
     * @return
     */
    @PutMapping("/class")
    @Identity({IdentityEnum.SUPER})
    public ApiResp<String> modifyClass(ModifyClassDto modifyClassDto){
        Boolean b = userService.modifyClass(modifyClassDto);
        return ApiResp.judge(b, "修改成功", ResultCode.PARAM_NOT_VALID);
    }

    /**
     * 管理员获取学生个人信息
     * @param userId 学生id
     * @return 学生个人信息
     */
    @GetMapping("/student")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<StudentForAdminVo> getStudent(@RequestParam("userId")Long userId) throws UnknownHostException {
        String userNumber = userMapper.selectUserNumberByUserId(userId);
        StudentForAdminVo user = userService.getStudentForAdmin(userNumber);
        return ApiResp.judge(user != null, user, ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 管理员修改学生个人信息
     * @param userDtoByAdmin
     * @return 学生个人信息
     */
    @PutMapping("/student")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<String> modifyStudent(@RequestBody UserDtoByAdmin userDtoByAdmin) throws IllegalAccessException {
        Boolean b = userService.modifyStudentForAdmin(userDtoByAdmin);
        return ApiResp.judge(b, "修改成功", ResultCode.DATABASE_DATA_EXCEPTION);
    }

    /**
     * 获取管理员信息
     * @param request
     * @return
     */
    @GetMapping("/admin")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<AdminVo> getAdminMessage(HttpServletRequest request){
        AdminVo adminVo = userService.getAdminMessage(request);
        return ApiResp.success(adminVo);
    }
}
