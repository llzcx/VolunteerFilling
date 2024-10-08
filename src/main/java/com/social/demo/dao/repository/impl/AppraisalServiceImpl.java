package com.social.demo.dao.repository.impl;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.mapper.*;
import com.social.demo.dao.repository.IAppraisalService;
import com.social.demo.data.bo.UserMessageBo;
import com.social.demo.data.dto.AppraisalUploadDto;
import com.social.demo.data.vo.AppraisalContentVo;
import com.social.demo.data.vo.AppraisalVo;
import com.social.demo.data.vo.YPage;
import com.social.demo.entity.Appraisal;
import com.social.demo.entity.Student;
import com.social.demo.entity.User;
import com.social.demo.manager.file.UploadFile;
import com.social.demo.manager.security.jwt.JwtUtil;
import com.social.demo.util.ConvertNullToEmptyString;
import com.social.demo.util.MybatisPlusUtil;
import com.social.demo.util.TimeUtil;
import com.social.demo.util.URLUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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

    @Autowired
    AppraisalSignatureMapper appraisalSignatureMapper;

    //文件夹前缀-学生综测签名
    @Value("${file-picture.address.signature.student}")
    private String SIGNATURE_STUDENTS;

    @Autowired
    URLUtil urlUtil;

    @Override
    public AppraisalVo getAppraisal(HttpServletRequest request, Integer month) throws UnknownHostException {
        Long userId = jwtUtil.getUserId(request);
        return getAppraisal(userMapper.selectUserNumberByUserId(userId), month);
    }

    @Override
    public Boolean uploadAppraisal(AppraisalUploadDto appraisalUploadDto) {
        Integer month = appraisalUploadDto.getMonth();
        if (month == 0) month = TimeUtil.now().getMonthValue();

        for (AppraisalContentVo appraisalContentVo : appraisalUploadDto.getAppraisalContentVos()) {

            Long userId = userMapper.selectUserIdByUserNumber(appraisalContentVo.getUserNumber());
            Appraisal appraisalByUserNumber = appraisalMapper.selectAppraisalByUserId(userId, month);
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
        appraisal.setUserId(userMapper.selectUserIdByUserNumber(appraisalContentVo.getUserNumber()));
        appraisal.setMonth(TimeUtil.now().getMonthValue());
        appraisal.setScore(appraisalContentVo.getPoint_total());
        ConvertNullToEmptyString.convert(appraisalContentVo);
        appraisal.setContent(JSONUtil.toJsonStr(appraisalContentVo));
        appraisalMapper.update(appraisal, MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisal.getAppraisalId()));
        return appraisal;
    }

    private Appraisal add(AppraisalContentVo appraisalContentVo){
        Appraisal appraisal;
        appraisal = new Appraisal();
        appraisal.setUserId(userMapper.selectUserIdByUserNumber(appraisalContentVo.getUserNumber()));
        appraisal.setMonth(TimeUtil.now().getMonthValue());
        appraisal.setScore(appraisalContentVo.getPoint_total());
        ConvertNullToEmptyString.convert(appraisalContentVo);
        appraisal.setContent(JSONUtil.toJsonStr(appraisalContentVo));
        appraisalMapper.insert(appraisal);
        return appraisal;
    }

    @Override
    public String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception {
        if (month == 0) month = TimeUtil.now().getMonthValue();

        Long userId = jwtUtil.getUserId(request);
        Appraisal appraisal1 = appraisalMapper.selectAppraisalByUserId(userId, month);
        if (appraisal1 == null) {
            User user = userMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId));
            Student student = studentMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId));
            appraisal1 = new Appraisal();
            appraisal1.setContent(JSONUtil.toJsonStr(new AppraisalContentVo(user.getUserNumber(), user.getUsername(), student.getAppraisalScore())));
            appraisal1.setMonth(month);
            appraisal1.setUserId(userId);
            appraisalMapper.insert(appraisal1);
        }
        Long appraisalId = appraisal1.getAppraisalId();
        String fileName = uploadFile.upload(file, SIGNATURE_STUDENTS, MD5.create().digestHex(userId + TimeUtil.now().toString()));
        Appraisal appraisal = new Appraisal();
        appraisal.setSignature(fileName);
        appraisalMapper.update(appraisal, MybatisPlusUtil.queryWrapperEq("appraisal_id", appraisalId));
        return fileName ;
    }


    @Override
    public AppraisalVo getAppraisalThisMonth(HttpServletRequest request) throws UnknownHostException {
        Long userId = jwtUtil.getUserId(request);
        return getAppraisal(userMapper.selectUserNumberByUserId(userId), TimeUtil.now().getMonthValue());
    }

    @Override
    public YPage<AppraisalVo> getAppraisalsToTeam(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size) throws UnknownHostException {
        Long userId = jwtUtil.getUserId(request);
        Long classId = appraisalTeamMapper.selectClassId(userId);
        if (month == 0){
            month = TimeUtil.now().getMonthValue();
        }
        List<UserMessageBo> userMessageBos = appraisalMapper.selectUserMessageToTeam(userId, keyword, rank, (current - 1) * size, size);
        Integer total = appraisalMapper.selectTotalToTeam(userId, keyword);
        List<AppraisalVo> appraisalVos = getAppraisals(userMessageBos, month);
        String signature = appraisalSignatureMapper.getSignature(userId, classId, month);
        signature = signature != null ? urlUtil.getUrl(signature) : null;
        String teacherSignature = appraisalSignatureMapper.getTeacherSignature(classId, month);
        teacherSignature = teacherSignature != null ? urlUtil.getUrl(teacherSignature) : null;
        YPage<AppraisalVo> appraisalVoIPage = new YPage<>(current, size, total, signature, teacherSignature);
        appraisalVoIPage.setRecords(appraisalVos);
        return appraisalVoIPage;
    }

    @Override
    public YPage<AppraisalVo> getAppraisalsToTeacher(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size) throws UnknownHostException {
        if (month == 0) month = TimeUtil.now().getMonthValue();
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        IPage<AppraisalVo> appraisalPage = getAppraisalPage(classId, keyword, month, rank, current, size);
        String teacherSignature = appraisalSignatureMapper.getSignature(userId, classId, month);
        String signature = appraisalSignatureMapper.getTeamSignature(classId, month);
        signature = signature != null ? urlUtil.getUrl(signature) : null;
        teacherSignature = teacherSignature != null ? urlUtil.getUrl(teacherSignature) : null;
        YPage<AppraisalVo> appraisalVoIPage = new YPage<>(current, size, appraisalPage.getTotal(), signature, teacherSignature);
        appraisalVoIPage.setRecords(appraisalPage.getRecords());
        return appraisalVoIPage;
    }

    @Override
    public List<Integer> getMonthToTeacher(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = classMapper.selectClassIdByTeacherUserId(userId);
        List<Integer> list = appraisalMapper.selectMonths(classId);
        Integer month = TimeUtil.now().getMonthValue();
        int flag = 0;
        for (Integer integer : list) {
            if (integer.equals(month)) {
                flag = 1;
                break;
            }
        }
        if (flag == 0) list.add(month);
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        return list;
    }

    @Override
    public List<Integer> getMonthToTeam(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = appraisalTeamMapper.selectClassId(userId);
        List<Integer> list = appraisalMapper.selectMonths(classId);
        Integer month = TimeUtil.now().getMonthValue();
        int flag = 0;
        for (Integer integer : list) {
            if (integer.equals(month)) {
                flag = 1;
                break;
            }
        }
        if (flag == 0) list.add(month);
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        return list;
    }

    @Override
    public IPage<AppraisalVo> getAppraisalHistory(Integer year, Integer month, Long classId, String keyword, Integer current, Integer size) throws UnknownHostException {
        List<UserMessageBo> userMessageBos = appraisalMapper.selectUserMessageHistory(year, classId, keyword, (current - 1) * size, size);
        Long total = appraisalMapper.selectHistoryTotal(year, month, classId, keyword);
        List<AppraisalVo> appraisalVos = getAppraisals(userMessageBos, month);
        IPage<AppraisalVo> appraisalVoIPage = new Page<>(current, size, total);
        appraisalVoIPage.setRecords(appraisalVos);
        return appraisalVoIPage;
    }

    @Override
    public IPage<AppraisalVo> getAppraisalsToStudent(HttpServletRequest request, String keyword, Integer month, Integer rank, Integer current, Integer size) throws UnknownHostException {
        if (month == 0) month = TimeUtil.now().getMonthValue();
        Long userId = jwtUtil.getUserId(request);
        Long classId = studentMapper.selectClassIdByUserId(userId);
        return getAppraisalPage(classId, keyword, month, rank, current, size);
    }

    @Override
    public List<AppraisalVo> getClassAppraisal(Long classId, Integer month, Integer year) throws UnknownHostException {
        List<UserMessageBo> userMessageBos = studentMapper.selectUserMessageByClassYear(classId, year);
        return getAppraisals(userMessageBos, month);
    }

    @Override
    public Integer getSignatureCount(HttpServletRequest request, Integer month) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = appraisalTeamMapper.selectClassId(userId);
        return appraisalMapper.selectSignatureCount(classId, month);
    }

    @Override
    public List<Integer> getMonthToStudent(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(request);
        Long classId = studentMapper.selectClassIdByUserId(userId);
        List<Integer> list = appraisalMapper.selectMonths(classId);
        Integer month = TimeUtil.now().getMonthValue();
        int flag = 0;
        for (Integer integer : list) {
            if (integer.equals(month)) {
                flag = 1;
                break;
            }
        }
        if (flag == 0) list.add(month);
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        return list;
    }

    private List<AppraisalVo> getAppraisals(List<UserMessageBo> userMessageBos, Integer month) throws UnknownHostException {
        if(userMessageBos.isEmpty())
            return null;
        List<Appraisal> appraisalList = appraisalMapper.selectAppraisals(userMessageBos, month);
        HashMap<Long, Appraisal> map = new HashMap<>();
        for (Appraisal appraisal : appraisalList) {
            map.put(appraisal.getUserId(), appraisal);
        }
        List<AppraisalVo> appraisalVos = new ArrayList<>();
        for (UserMessageBo userMessageBo : userMessageBos) {
            Long userId = userMessageBo.getUserId();
            String userNumber = userMessageBo.getUserNumber();
            String username = userMessageBo.getUsername();
            Double lastMonthScore = userMessageBo.getAppraisalScore();
            AppraisalVo appraisalVo = new AppraisalVo();
            if (map.get(userId) == null){
                appraisalVo.setMonth(month);
                appraisalVo.setContent(new AppraisalContentVo(userNumber, username, lastMonthScore));
            }else {
                Appraisal appraisal = map.get(userId);
                BeanUtils.copyProperties(appraisal, appraisalVo);
                appraisalVo.setSignature(appraisal.getSignature() != null ? urlUtil.getUrl(appraisal.getSignature()) : null);
                if (appraisal.getContent() != null) {
                    AppraisalContentVo bean = JSONUtil.toBean(appraisal.getContent(), AppraisalContentVo.class);
                    bean.setUsername(username);
                    appraisalVo.setContent(bean);
                }else {
                    AppraisalContentVo appraisalContentVo = new AppraisalContentVo(userNumber, username, lastMonthScore);
                    appraisalVo.setContent(appraisalContentVo);
                }
            }
            appraisalVos.add(appraisalVo);
        }
        return appraisalVos;
    }

    private AppraisalVo getAppraisal(String userNumber, Integer month) throws UnknownHostException {
        Long userId = userMapper.selectUserIdByUserNumber(userNumber);
        Appraisal appraisal = appraisalMapper.selectOne(MybatisPlusUtil.queryWrapperEq("user_id", userId, "month", month));
        AppraisalVo appraisalVo = new AppraisalVo();
        String username = userMapper.selectUserNameByUserNumber(userNumber);
        Double lastMonthScore = getLastMonthScore(userNumber, TimeUtil.now().getMonthValue());
        if (appraisal != null) {
            BeanUtils.copyProperties(appraisal, appraisalVo);
            appraisalVo.setSignature(appraisal.getSignature() != null ? urlUtil.getUrl(appraisal.getSignature()) : null);
            if (appraisal.getContent() != null) {
                AppraisalContentVo bean = JSONUtil.toBean(appraisal.getContent(), AppraisalContentVo.class);
                bean.setUsername(username);
                appraisalVo.setContent(bean);
            }else {
                AppraisalContentVo appraisalContentVo = new AppraisalContentVo(userNumber, username, lastMonthScore);
                appraisalVo.setContent(appraisalContentVo);
            }
        }else {
            appraisalVo.setMonth(month);
            appraisalVo.setContent(new AppraisalContentVo(userNumber, username, lastMonthScore));
        }
        return appraisalVo;
    }

    private IPage<AppraisalVo> getAppraisalPage(Long classId, String keyword, Integer month, Integer rank, Integer current, Integer size) throws UnknownHostException {
        if (month == 0){
            month = TimeUtil.now().getMonthValue();
        }
        List<UserMessageBo> userMessageBos = appraisalMapper.selectUserMessageToTeacher(classId ,keyword, rank, (current - 1) * size, size);
        Long count = studentMapper.selectCountByClassAndKeyword(classId, keyword);
        List<AppraisalVo> appraisalVos = getAppraisals(userMessageBos, month);
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
