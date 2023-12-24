package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.dao.mapper.AppraisalDetailMapper;
import com.social.demo.dao.mapper.AppraisalMapper;
import com.social.demo.dao.mapper.ClassMapper;
import com.social.demo.dao.mapper.UserMapper;
import com.social.demo.dao.repository.IClassAdviserService;
import com.social.demo.data.dto.IdentityDto;
import com.social.demo.data.vo.ClassUserVo;
import com.social.demo.manager.security.authentication.JwtUtil;
import com.social.demo.util.TimeUtil;
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

    @Autowired
    AppraisalDetailMapper appraisalDetailMapper;

    @Override
    public IPage<ClassUserVo> getStudents(HttpServletRequest request, String userNumber, String username, Integer role,
                                          Integer rank, Integer current, Integer size) {
        String number = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherNumber(number);
        List<ClassUserVo> userList = userMapper.selectClassUserNumbers(classId, userNumber, username, role, rank, TimeUtil.now().getMonthValue(), (current-1)*size, size);
        Integer total = userMapper.selectClassStudentCount(classId);
        if (userList.isEmpty()){
            userList = userMapper.selectClassUserNumbers(classId, userNumber, username, role, rank,
                    (TimeUtil.now().getMonthValue()-1>0?TimeUtil.now().getMonthValue()-1:12), (current-1)*size, size);
        }

        IPage<ClassUserVo> page = new Page<>(current, size);
        page.setTotal(total);
        page.setRecords(userList);

        return page;
    }

    @Override
    public void modifyIdentity(IdentityDto[] identityDto) {
        for (IdentityDto dto : identityDto) {
            userMapper.updateClassIdentity(dto.getUserNumber(), dto.getIdentity());
        }
    }
}
