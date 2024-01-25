package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.dao.mapper.AppraisalMapper;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IClassAdviserService;
import com.social.demo.data.dto.IdentityDto;
import com.social.demo.data.vo.ClassUserVo;
import com.social.demo.manager.security.authentication.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<ClassUserVo> getStudents(HttpServletRequest request, String userNumber, String username, String role,
                                          Integer rank, Integer current, Integer size) {
        Long userId = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        List<ClassUserVo> userList = userMapper.selectClassUserNumbers(classId, userNumber, username, role, rank, (current-1)*size, size);
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
}
