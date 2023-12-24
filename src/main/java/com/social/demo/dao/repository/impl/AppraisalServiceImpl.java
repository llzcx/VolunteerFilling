package com.social.demo.dao.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.PropertiesConstant;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.data.bo.StudentBo;
import com.social.demo.data.dto.AppraisalDetailDto;
import com.social.demo.data.dto.AppraisalDto;
import com.social.demo.data.vo.AppraisalDetailVo;
import com.social.demo.data.vo.AppraisalToOtherVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.entity.Appraisal;
import com.social.demo.entity.AppraisalDetail;
import com.social.demo.manager.file.UploadFile;
import com.social.demo.manager.security.authentication.JwtUtil;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    AppraisalDetailMapper appraisalDetailMapper;

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

        String userNumber = jwtUtil.getSubject(request);

        return getAppraisal(userNumber, month);
    }

    @Override
    public IPage<AppraisalToOtherVo> getAppraisalsToTeacher(HttpServletRequest request, String name, String userNumber, Integer month,
                                                            Integer rank, Integer current, Integer size) {
        String teacherNumber = jwtUtil.getSubject(request);
        Long classId = classMapper.selectClassIdByTeacherNumber(teacherNumber);
        return getAppraisals(classId, name, userNumber, month, rank, current, size);
    }

    @Override
    public Boolean uploadAppraisal(AppraisalDto appraisalDto) {
        Appraisal appraisal = appraisalMapper.selectAppraisalByUserNumber(appraisalDto.getUserNumber(), appraisalDto.getMonth());
        int subtractPoints = 0;
        int bonusPoints = 0;
        for (AppraisalDetailDto appraisalDetailDto : appraisalDto.getAppraisalDetails()) {
            AppraisalDetail appraisalDetailBo = appraisalDetailMapper.selectOne(MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisal.getAppraisalId(), "classes", appraisalDetailDto.getClasses()));
            AppraisalDetail appraisalDetail = new AppraisalDetail();
            BeanUtils.copyProperties(appraisalDetailDto, appraisalDetail);
            appraisalDetail.setAppraisalId(appraisal.getAppraisalId());
            appraisalDetail.setExplain(getExplain(appraisalDetailDto.getPlusExplain(), appraisalDetailDto.getMinusExplain()));
            if (appraisalDetailBo != null){
                subtractPoints += appraisalDetailDto.getSubtractPoints() - appraisalDetailBo.getSubtractPoints();
                bonusPoints += appraisalDetailDto.getBonusPoints() - appraisalDetailBo.getBonusPoints();
                appraisalDetailMapper.update(appraisalDetail, MybatisPlusUtil.queryWrapperEq("appraisal_detail_id", appraisalDetailBo.getAppraisalId()));
            }else {
                subtractPoints += appraisalDetailDto.getSubtractPoints();
                bonusPoints += appraisalDetailDto.getBonusPoints();
                appraisalDetailMapper.insert(appraisalDetail);
            }
        }
        appraisal.setScore(appraisal.getScore() + subtractPoints + bonusPoints);
        appraisalMapper.update(appraisal, MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisal.getAppraisalId()));
        return true;
    }

    @Override
    public IPage<AppraisalToOtherVo> getAppraisalsToTeam(HttpServletRequest request, String name, String userNumber, Integer month, Integer rank, Integer current, Integer size) {
        String number = jwtUtil.getSubject(request);
        Long classId = studentMapper.selectClassIdByUserNumber(number);
        return getAppraisals(classId, name, userNumber, month, rank, current, size);
    }

    @Override
    public Boolean uploadSignature(MultipartFile file, HttpServletRequest request) throws Exception {
        String userNumber = jwtUtil.getSubject(request);
        Long appraisalId = appraisalMapper.selectAppraisalByUserNumber(userNumber, TimeUtil.now().getMonthValue()).getAppraisalId();
        String filename = PropertiesConstant.APPRAISAL  + "-"  + appraisalId;
        String fileName = uploadFile.upload(file, PropertiesConstant.APPRAISALS, filename);
        Appraisal appraisal = new Appraisal();
        appraisal.setSignature(fileName);
        appraisalMapper.update(appraisal, MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisalId));
        return true;
    }

    @Override
    public Boolean downloadSignature(Integer month, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userNumber = jwtUtil.getSubject(request);
        Appraisal appraisal = appraisalMapper.selectAppraisalByUserNumber(userNumber, month);
        return uploadFile.download(response, PropertiesConstant.APPRAISALS, appraisal.getSignature());
    }

    @Override
    public Boolean downloadSignature(Integer month, String userNumber, HttpServletResponse response) throws Exception {
        Appraisal appraisal = appraisalMapper.selectAppraisalByUserNumber(userNumber, month);
        return uploadFile.download(response, PropertiesConstant.APPRAISALS, appraisal.getSignature());
    }

    /**
     * 获取分页的综测信息
     * @param classId
     * @param name
     * @param userNumber
     * @param month
     * @param rank
     * @param current
     * @param size
     * @return
     */
    private IPage<AppraisalToOtherVo> getAppraisals(Long classId, String name, String userNumber, Integer month,
                                                      Integer rank, Integer current, Integer size){
        List<String> userNumbers = appraisalMapper.selectUserNumbers(classId, name, userNumber,month , rank, (current-1) * size, size);
        List<AppraisalToOtherVo> appraisalToOtherVos = new ArrayList<>();
        IPage<AppraisalToOtherVo> appraisalVoIPage = new Page<>();
        Integer total;
        if (name.isEmpty() && userNumber.isEmpty() && userNumbers.isEmpty() && month == TimeUtil.now().getMonthValue()){
            List<StudentBo> classStudents = getClassStudents(classId, current, size);
            total = userMapper.selectClassStudentCount(classId);
            for (StudentBo classStudent : classStudents) {
                Appraisal appraisal = new Appraisal(classStudent.getUserId(), month, getLastMonthScore(classStudent.getUserNumber(), month));
                appraisalMapper.insert(appraisal);
                AppraisalVo appraisalVo = getAppraisal(classStudent.getUserNumber(), month);
                AppraisalToOtherVo appraisalToOtherVo = new AppraisalToOtherVo();
                BeanUtils.copyProperties(appraisalVo, appraisalToOtherVo);
                appraisalToOtherVo.setUserNumber(classStudent.getUserNumber());
                appraisalToOtherVo.setName(userMapper.selectUserNameByUserNumber(classStudent.getUserNumber()));
                appraisalToOtherVos.add(appraisalToOtherVo);
            }
        }else {
            for (String number : userNumbers) {
                AppraisalVo appraisalVo = getAppraisal(number, month);
                AppraisalToOtherVo appraisalToOtherVo = new AppraisalToOtherVo();
                BeanUtils.copyProperties(appraisalVo, appraisalToOtherVo);
                appraisalToOtherVo.setUserNumber(number);
                appraisalToOtherVo.setName(userMapper.selectUserNameByUserNumber(number));
                appraisalToOtherVos.add(appraisalToOtherVo);
            }
            total = appraisalMapper.selectTotal(classId, name, userNumber, month, rank);
        }
        appraisalVoIPage.setRecords(appraisalToOtherVos);
        appraisalVoIPage.setTotal(total);
        appraisalVoIPage.setSize(size);
        appraisalVoIPage.setCurrent(current);
        return appraisalVoIPage;
    }

    /**
     * 根据学号和月份获取用户综测情况
     * @param userNumber
     * @param month
     * @return
     */
    private AppraisalVo getAppraisal(String userNumber, Integer month){
        Appraisal appraisal = judgeAppraisalExist(userNumber, month);
        List<AppraisalDetail> appraisalDetails = appraisalDetailMapper.selectList(
                MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisal.getAppraisalId()));
        Integer totalPlus = 0;
        Integer totalMinus = 0;
        List<AppraisalDetailVo> detailList = new ArrayList<>();
        for (AppraisalDetail appraisalDetail : appraisalDetails) {
            totalPlus += appraisalDetail.getBonusPoints();
            totalMinus += appraisalDetail.getSubtractPoints();
            AppraisalDetailVo appraisalDetailVo = getAppraisalDetailVo(appraisalDetail);
            detailList.add(appraisalDetailVo);
        }

        return new AppraisalVo(detailList, totalPlus, totalMinus, month,
                getLastMonthScore(userNumber, month), appraisal.getScore(), appraisal.getSignature());
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
     * 获取每月综测详情
     * @param appraisalDetail
     * @return
     */
    private AppraisalDetailVo getAppraisalDetailVo(AppraisalDetail appraisalDetail){
        String str = appraisalDetail.getExplain();
        int hashIndex = str.indexOf("#");
        String plusExplain = "";
        String minusExplain = "";
        if (hashIndex != -1) {
            String beforeHash = str.substring(0, hashIndex);
            String afterHash = str.substring(hashIndex + 1);
            if (!beforeHash.isEmpty()) {
                plusExplain = beforeHash;
            }
            if (!afterHash.isEmpty()) {
                minusExplain = afterHash;
            }
        }
        return new AppraisalDetailVo(appraisalDetail.getClasses(), plusExplain,
                minusExplain, appraisalDetail.getBonusPoints(), appraisalDetail.getSubtractPoints());
    }

    /**
     * 获取将加分减分说明存起来的字符串
     * @param s1
     * @param s2
     * @return
     */
    private String getExplain(String s1, String s2){
        return s1+"#"+s2;
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
