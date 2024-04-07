package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IAppraisalTeamService;
import com.social.demo.data.vo.AppraisalTeamUserVo;
import com.social.demo.data.vo.AppraisalTeamVo;
import com.social.demo.entity.AppraisalTeam;
import com.social.demo.entity.User;
import com.social.demo.manager.file.UploadFile;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨世博
 * @date 2024/1/23 21:40
 * @description AppraisalTeamServiceImpl
 */
@Service
public class AppraisalTeamServiceImpl extends ServiceImpl<AppraisalTeamMapper, AppraisalTeam> implements IAppraisalTeamService {

    @Autowired
    AppraisalTeamMapper appraisalTeamMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    UserMapper userMapper;

    @Qualifier("local")
    @Autowired
    UploadFile uploadFile;

    @Autowired
    AppraisalSignatureMapper appraisalSignatureMapper;

    @Autowired
    AppraisalMapper appraisalMapper;

    //文件夹前缀-志愿签名
    @Value("${file-picture.address.signature.wish}")
    String SIGNATURE_WISH;

    @Override
    public AppraisalTeamVo getAppraisalTeam(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        return appraisalTeamMapper.selectTeamUser(classId);
    }

    @Override
    public void resetAppraisalTeamPwd(String userNumber) {
        User user = new User();
        user.setPassword(MD5.create().digestHex(PropertiesConstant.PASSWORD));
        userMapper.update(user, MybatisPlusUtil.queryWrapperEq("user_number", userNumber));
    }

    @Override
    public AppraisalTeamUserVo getMessage(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId));
        String className = appraisalTeamMapper.selectClassName(userId);
        AppraisalTeamUserVo appraisalTeamUserVo = new AppraisalTeamUserVo();
        appraisalTeamUserVo.setUsername(user.getUsername());
        appraisalTeamUserVo.setUserNumber(user.getUserNumber());
        appraisalTeamUserVo.setClassName(className);
        return appraisalTeamUserVo;
    }

    @Override
    public String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception {
        if (month == 0) month = TimeUtil.now().getMonthValue();

        Long userId = jwtUtil.getUserId(request);
        Long classId = appraisalTeamMapper.selectClassId(userId);

        String fileName = uploadFile.upload(file, SIGNATURE_WISH, MD5.create().digestHex(userId + TimeUtil.now().toString()));
        appraisalSignatureMapper.add(classId, fileName, month, userId);
        return fileName;
    }
}
