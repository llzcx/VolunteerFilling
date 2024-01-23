package com.social.demo.dao.repository.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.data.bo.StudentBo;
import com.social.demo.data.vo.AppraisalContentVo;
import com.social.demo.data.vo.AppraisalTotalVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.entity.Appraisal;
import com.social.demo.entity.Student;
import com.social.demo.manager.file.UploadFile;
import com.social.demo.manager.security.authentication.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨世博
 * @date 2023/12/19 15:33
 * @description AppraisalServiceImpl
 */
@Service
public class AppraisalServiceImpl extends ServiceImpl<AppraisalMapper, Appraisal> implements IAppraisalService {
    @Autowired
    AppraisalMapper appraisalMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Qualifier("local")
    @Autowired
    UploadFile uploadFile;

    @Override
    public AppraisalVo getAppraisal(HttpServletRequest request, Integer month) {
        Long userId = jwtUtil.getSubject(request);
        return getAppraisal(userMapper.selectUserNumberByUserId(userId), month);
    }

    @Override
    public Boolean uploadAppraisal(AppraisalContentVo appraisalContentVo) {
        Long userId = userMapper.selectUserIdByUserNumber(appraisalContentVo.getUserNumber());
        Appraisal appraisalByUserNumber = appraisalMapper.selectAppraisalByUserId(userId, appraisalContentVo.getMonth());
        Appraisal appraisal;
        if (appraisalByUserNumber == null){
            appraisal = new Appraisal();
            AppraisalTotalVo appraisalTotalVo = new AppraisalTotalVo();
            appraisalTotalVo.setClass1(appraisalContentVo.getPoint1());
            appraisalTotalVo.setClass2(appraisalContentVo.getPoint2());
            appraisalTotalVo.setClass3(appraisalContentVo.getPoint3());
            appraisalTotalVo.setClass4(appraisalContentVo.getPoint4());
            appraisalTotalVo.setClass5(appraisalContentVo.getPoint5());
            appraisalTotalVo.setAdd(appraisalContentVo.getAdd_total());
            appraisalTotalVo.setSub(appraisalContentVo.getSub_total());
            appraisalTotalVo.setAll(appraisalContentVo.getPoint_total());
            appraisal.setTotal(JSONUtil.toJsonStr(appraisalTotalVo));
            appraisal.setUserId(userMapper.selectUserIdByUserNumber(appraisalContentVo.getUserNumber()));
            appraisal.setMonth(appraisalContentVo.getMonth());
            appraisal.setScore(appraisalContentVo.getPoint_total());
            appraisal.setContent(JSONUtil.toJsonStr(appraisalContentVo));
            appraisalMapper.insert(appraisal);
        }else {
            appraisal = appraisalByUserNumber;
            AppraisalTotalVo appraisalTotalVo = JSONUtil.toBean(appraisal.getTotal(), AppraisalTotalVo.class);
            appraisalTotalVo.setClass1(appraisalContentVo.getPoint1() + appraisalTotalVo.getClass1());
            appraisalTotalVo.setClass2(appraisalContentVo.getPoint2() + appraisalTotalVo.getClass2());
            appraisalTotalVo.setClass3(appraisalContentVo.getPoint3() + appraisalTotalVo.getClass3());
            appraisalTotalVo.setClass4(appraisalContentVo.getPoint4() + appraisalTotalVo.getClass4());
            appraisalTotalVo.setClass5(appraisalContentVo.getPoint5() + appraisalTotalVo.getClass5());
            appraisalTotalVo.setAdd(appraisalContentVo.getAdd_total() + appraisalTotalVo.getAdd());
            appraisalTotalVo.setSub(appraisalContentVo.getSub_total() + appraisalTotalVo.getSub());
            appraisalTotalVo.setAll(appraisalContentVo.getPoint_total() + appraisalContentVo.getPoint_total());
            appraisal.setTotal(JSONUtil.toJsonStr(appraisalTotalVo));
            appraisal.setUserId(userMapper.selectUserIdByUserNumber(appraisalContentVo.getUserNumber()));
            appraisal.setMonth(appraisalContentVo.getMonth());
            appraisal.setScore(appraisalContentVo.getPoint_total());
            appraisal.setContent(JSONUtil.toJsonStr(appraisalContentVo));
            appraisalMapper.update(appraisal, MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisal.getAppraisalId()));
        }
        Student student = new Student();
        studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        return null;
    }

    @Override
    public Boolean uploadSignature(MultipartFile file, HttpServletRequest request) throws Exception {
        Long userId = jwtUtil.getSubject(request);
        Long appraisalId = appraisalMapper.selectAppraisalByUserId(userId, TimeUtil.now().getMonthValue()).getAppraisalId();
        String filename = PropertiesConstant.APPRAISAL  + "-"  + appraisalId;
        String fileName = uploadFile.upload(file, PropertiesConstant.APPRAISALS, filename);
        Appraisal appraisal = new Appraisal();
        appraisal.setSignature(fileName);
        appraisalMapper.update(appraisal, MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisalId));
        return true;
    }


    @Override
    public AppraisalVo getAppraisalThisMonth(HttpServletRequest request) {
        Long userId = jwtUtil.getSubject(request);
        return getAppraisal(userMapper.selectUserNumberByUserId(userId), TimeUtil.now().getMonthValue());
    }

    @Override
    public IPage<AppraisalVo> getAppraisalsToTeam(HttpServletRequest request, String name, String userNumber, Integer month, Integer rank, Integer current, Integer size) {
        Long userId = jwtUtil.getSubject(request);
        Long classId = studentMapper.selectClassIdByUserId(userId);
        return getAppraisalPage(classId, name, userNumber, month, rank, current, size);
    }

    @Override
    public IPage<AppraisalVo> getAppraisalsToTeacher(HttpServletRequest request, String name, String userNumber, Integer month, Integer rank, Integer current, Integer size) {
        Long userId = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        return getAppraisalPage(classId, name, userNumber, month, rank, current, size);
    }

    private AppraisalVo getAppraisal(String userNumber, Integer month){
        Long userId = userMapper.selectUserIdByUserNumber(userNumber);
        Appraisal appraisal = appraisalMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId, "month", month));
        if (appraisal == null) return null;
        AppraisalVo appraisalVo = new AppraisalVo();
        BeanUtils.copyProperties(appraisal, appraisalVo);
        appraisalVo.setContent(JSONUtil.toBean(appraisal.getContent(), AppraisalContentVo.class));
        appraisalVo.setTotal(JSONUtil.toBean(appraisal.getTotal(), AppraisalTotalVo.class));
        appraisalVo.setLastScore(getLastMonthScore(userNumber,TimeUtil.now().getMonthValue()));
        return appraisalVo;
    }

    private IPage<AppraisalVo> getAppraisalPage(Long classId, String name, String userNumber, Integer month, Integer rank, Integer current, Integer size){
        List<String> userNumbers;
        if (month == null){
            month = TimeUtil.now().getDayOfMonth();
        }
        userNumbers = appraisalMapper.selectUserNumbers(classId, name, userNumber, month, rank, (current - 1) * size, size);

        ArrayList<AppraisalVo> appraisalVos = new ArrayList<>();
        for (String number : userNumbers) {
            AppraisalVo appraisalVo = getAppraisal(number, month);
            appraisalVos.add(appraisalVo);
        }

        IPage<AppraisalVo> appraisalVoIPage = new Page<>(current, size, appraisalVos.size());
        appraisalVoIPage.setRecords(appraisalVos);
        return appraisalVoIPage;
    }

    /**
     * 分页获取班级学生
     * @param classId
     * @param current
     * @param size
     * @return
     */
    private List<StudentBo> getClassStudents(Long classId, Integer current, Integer size){
        return userMapper.selectClassStudents(classId, (current-1) * size, size);
    }

    /**
     * 判断该月的综测是否存在
     * 存在返回
     * 不存在且不是月份不本月返回
     * 不存在但是本月创建一份新的
     * @param userNumber
     * @param month
     * @return
     */
    private Appraisal judgeAppraisalExist(String userNumber, Integer month){
        Long userId = userMapper.selectUserIdByUserNumber(userNumber);
        Appraisal appraisal = appraisalMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId, "month", month));
        if (appraisal == null){
            if (TimeUtil.now().getMonthValue()!=month){
                throw new SystemException(ResultCode.APPRAISAL_NOT_EXISTS);
            }else {
                appraisal = new Appraisal(userId, month, 0);
                appraisalMapper.insert(appraisal);
            }
        }
        return appraisal;
    }

    /**
     * 获取上个月的分数
     * @param userNumber
     * @param month
     * @return
     */
    private Integer getLastMonthScore(String userNumber, Integer month){
        Integer lastMonth = month != 1 ? month - 1 : 12;
        Integer score = appraisalMapper.selectLastMonthScore(userNumber, lastMonth);
        return score != null ? score : 100;
    }
}
