package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.AppraisalMapper;
import com.social.demo.dao.mapper.AppraisalSignatureMapper;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IClassAdviserService;
import com.social.demo.data.dto.SignatureDto;
import com.social.demo.data.vo.ClassUserVo;
import com.social.demo.manager.file.UploadFile;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/7 21:18
 * @description ClassAdviserServiceImpl
 */
@Service
public class ClassAdviserServiceImpl implements IClassAdviserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    AppraisalMapper appraisalMapper;

    @Qualifier("local")
    @Autowired
    UploadFile uploadFile;

    @Autowired
    AppraisalSignatureMapper appraisalSignatureMapper;

    @Override
    public String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception {
        if (month == 0) month = TimeUtil.now().getMonthValue();

        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);

        if (appraisalMapper.selectIsEnd(classId, month)) return null;

        String fileName = uploadFile.upload(file, PropertiesConstant.SIGNATURE_TEAM, MD5.create().digestHex(userId + TimeUtil.now().toString()));
        appraisalSignatureMapper.add(classId, fileName, month, userId);
        return fileName;
    }

    @Override
    public IPage<ClassUserVo> getStudents(HttpServletRequest request, String keyword, String role,
                                          Integer rank, Integer current, Integer size) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        List<ClassUserVo> userList = userMapper.selectClassUserNumbers(classId, keyword, role, rank, (current-1)*size, size);
        for (ClassUserVo classUserVo : userList) {
            Integer integer = userMapper.selectIdentity(classId + classUserVo.getUserNumber());
            classUserVo.setIdentity(integer == null ? 1 : 2);
        }
        Integer total = userMapper.selectClassStudentCount(classId);
        IPage<ClassUserVo> page = new Page<>(current, size);
        page.setTotal(total);
        page.setRecords(userList);
        return page;
    }

    @Override
    public Boolean setIsEnd(HttpServletRequest request, Integer month, Boolean isEnd) {
        if (month == 0) month = TimeUtil.now().getMonthValue();

        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        appraisalMapper.updateIsEnd(classId, month, isEnd);
        return true;
    }

    @Override
    public Boolean getClassAppraisalState(HttpServletRequest request, Integer month) {
        if (month == 0) month = TimeUtil.now().getMonthValue();
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        return appraisalMapper.selectIsEnd(classId, month);
    }
}
