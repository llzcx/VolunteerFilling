package com.social.demo.controller;

import com.social.demo.common.ApiResp;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.RegConstant;
import com.social.demo.dao.repository.IUserService;
import com.social.demo.data.bo.TokenPair;
import com.social.demo.data.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
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
    public ApiResp<TokenPair> login(@Valid @RequestBody LoginDto loginDto) throws Exception {
        final TokenPair tokenPair = userService.login(loginDto);
        return ApiResp.judge(tokenPair!=null,tokenPair, ResultCode.PASSWORD_ERROR);
    }

    /**
     * 退出登录
     * @throws Exception
     */
    @PostMapping("/loginOut")
    public ApiResp<String> loginOut(HttpServletRequest request) throws Exception {
        userService.loginOut(request);
        return ApiResp.success();
    }
}
