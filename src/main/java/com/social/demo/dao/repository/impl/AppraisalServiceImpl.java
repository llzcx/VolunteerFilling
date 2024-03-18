package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.data.vo.AppraisalContentVo;
import com.social.demo.data.vo.AppraisalTotalVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.entity.Appraisal;
import com.social.demo.entity.Student;
import com.social.demo.manager.file.UploadFile;
import com.social.demo.manager.security.jwt.JwtUtil;
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

    @Autowired
    AppraisalTeamMapper appraisalTeamMapper;

    @Qualifier("local")
    @Autowired
    UploadFile uploadFile;

    @Override
    public AppraisalVo getAppraisal(HttpServletRequest request, Integer month) {
        if (month == 0) month = TimeUtil.now().getDayOfMonth();

        Long userId = jwtUtil.getUserId(request);
        return getAppraisal(userMapper.selectUserNumberByUserId(userId), month);
    }

    @Override
    public Boolean uploadAppraisal(AppraisalContentVo[] appraisalContentVos) {
        for (AppraisalContentVo appraisalContentVo : appraisalContentVos) {
            Long userId = userMapper.selectUserIdByUserNumber(appraisalContentVo.getUserNumber());
            Appraisal appraisalByUserNumber = appraisalMapper.selectAppraisalByUserId(userId, TimeUtil.now().getMonthValue());
            Appraisal appraisal;
            if (appraisalByUserNumber == null){
                appraisal = add(appraisalContentVo);
            }else {
                appraisal = modify(appraisalByUserNumber, appraisalContentVo);
            }
            Student student = new Student();
            student.setAppraisalScore(appraisal.getScore());
            studentMapper.update(student, MybatisPlusUtil.queryWrapperEq("user_id", userId));
        }
        return null;
    }

    private Appraisal modify(Appraisal appraisalByUserNumber, AppraisalContentVo appraisalContentVo){
        Appraisal appraisal;
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
        appraisal.setMonth(TimeUtil.now().getMonthValue());
        appraisal.setScore(appraisalContentVo.getPoint_total());
        appraisal.setContent(JSONUtil.toJsonStr(appraisalContentVo));
        appraisalMapper.update(appraisal, MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisal.getAppraisalId()));
        return appraisal;
    }

    private Appraisal add(AppraisalContentVo appraisalContentVo){
        Appraisal appraisal;
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
        appraisal.setMonth(TimeUtil.now().getMonthValue());
        appraisal.setScore(appraisalContentVo.getPoint_total());
        appraisal.setContent(JSONUtil.toJsonStr(appraisalContentVo));
        appraisalMapper.insert(appraisal);
        return appraisal;
    }

    @Override
    public String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception {
        if (month == 0) month = TimeUtil.now().getDayOfMonth();

        Long userId = jwtUtil.getUserId(request);
        Long appraisalId = appraisalMapper.selectAppraisalByUserId(userId, month).getAppraisalId();
        String fileName = uploadFile.upload(file, PropertiesConstant.SIGNATURE_STUDENTS, MD5.create().digestHex(userId + TimeUtil.now().toString()));
        Appraisal appraisal = new Appraisal();
        appraisal.setSignature(fileName);
        appraisalMapper.update(appraisal, MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisalId));
        return fileName ;
    }


    @Override
    public AppraisalVo getAppraisalThisMonth(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        return getAppraisal(userMapper.selectUserNumberByUserId(userId), TimeUtil.now().getMonthValue());
    }

    @Override
    public IPage<AppraisalVo> getAppraisalsToTeam(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size) {
        Long userId = jwtUtil.getUserId(request);
        List<String> userNumbers;
        if (month == 0){
            month = TimeUtil.now().getMonthValue();
        }
        userNumbers = appraisalMapper.selectUserNumbersToTeam(userId, keyword, rank, (current - 1) * size, size);
        Integer total = appraisalMapper.selectTotalToTeam(userId, keyword);
        ArrayList<AppraisalVo> appraisalVos = new ArrayList<>();
        for (String number : userNumbers) {
            AppraisalVo appraisalVo = getAppraisal(number, month);
            appraisalVos.add(appraisalVo);
        }

        IPage<AppraisalVo> appraisalVoIPage = new Page<>(current, size, total);
        appraisalVoIPage.setRecords(appraisalVos);
        return appraisalVoIPage;
    }

    @Override
    public IPage<AppraisalVo> getAppraisalsToTeacher(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size) {
        if (month == 0) month = TimeUtil.now().getDayOfMonth();
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        return getAppraisalPage(classId, keyword, month, rank, current, size);
    }

    @Override
    public List<Integer> getMonthToTeacher(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        return appraisalMapper.selectMonths(classId);
    }

    @Override
    public List<Integer> getMonthToTeam(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = appraisalTeamMapper.selectClassId(userId);
        return appraisalMapper.selectMonths(classId);
    }

    private AppraisalVo getAppraisal(String userNumber, Integer month){
        Long userId = userMapper.selectUserIdByUserNumber(userNumber);
        Appraisal appraisal = appraisalMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId, "month", month));
        AppraisalVo appraisalVo = new AppraisalVo();
        String username = userMapper.selectUserNameByUserNumber(userNumber);
        Double lastMonthScore = getLastMonthScore(userNumber, TimeUtil.now().getMonthValue());
        if (appraisal != null) {
            BeanUtils.copyProperties(appraisal, appraisalVo);
            appraisalVo.setContent(appraisal.getContent() != null ? JSONUtil.toBean(appraisal.getContent(), AppraisalContentVo.class) : new AppraisalContentVo(userNumber, username, lastMonthScore));
            appraisalVo.setTotal(appraisal.getTotal() != null ? JSONUtil.toBean(appraisal.getTotal(), AppraisalTotalVo.class) : new AppraisalTotalVo());
        }else {
            appraisalVo.setMonth(month);
            appraisalVo.setContent(new AppraisalContentVo(userNumber, username, lastMonthScore));
            appraisalVo.setTotal(new AppraisalTotalVo());
        }
        return appraisalVo;
    }

    private IPage<AppraisalVo> getAppraisalPage(Long classId, String keyword, Integer month, Integer rank, Integer current, Integer size){
        List<String> userNumbers;
        if (month == 0){
            month = TimeUtil.now().getMonthValue();
        }
        userNumbers = appraisalMapper.selectUserNumbersToTeacher(classId, keyword, month, rank, (current - 1) * size, size);
        Long count = studentMapper.selectCount(MybatisPlusUtil.queryWrapperEq("class_id", classId));
        ArrayList<AppraisalVo> appraisalVos = new ArrayList<>();
        for (String number : userNumbers) {
            AppraisalVo appraisalVo = getAppraisal(number, month);
            appraisalVos.add(appraisalVo);
        }

        IPage<AppraisalVo> appraisalVoIPage = new Page<>(current, size, count);
        appraisalVoIPage.setRecords(appraisalVos);
        return appraisalVoIPage;
    }

    /**
     * 获取上个月的分数
     * @param userNumber
     * @param month
     * @return
     */
    private Double getLastMonthScore(String userNumber, Integer month){
        Integer lastMonth = month != 1 ? month - 1 : 12;
        Integer score = appraisalMapper.selectLastMonthScore(userNumber, lastMonth);
        return score != null ? score : 100.00;
    }
}
