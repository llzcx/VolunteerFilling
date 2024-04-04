package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.common.ApiResp;
import com.social.demo.common.JsonUtil;
import com.social.demo.common.ResultCode;
import com.social.demo.constant.IdentityEnum;
import com.social.demo.dao.mapper.AreaMapper;
import com.social.demo.dao.repository.IAreaService;
import com.social.demo.dao.repository.IMajorService;
import com.social.demo.data.vo.MajorVo;
import com.social.demo.data.vo.SubjectRuleVo;
import com.social.demo.entity.Area;
import com.social.demo.entity.Major;
import com.social.demo.manager.security.identity.Identity;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 专业接口
 * @author 周威宇
 */
@RestController
@RequestMapping("/major")
@Validated
public class MajorController {
    @Autowired
    private IMajorService majorService;
    @Autowired
    private IAreaService areaService;

    /**
     * 添加专业
     */
    @PostMapping("/addMajor")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> addMajor(@RequestBody List<MajorVo> majorVos){
          List<Major> majors = new ArrayList<>();

        for (MajorVo majorVo : majorVos) {
            Major major = MajorVoMajor(majorVo);
            majors.add(major);
        }
        return ApiResp.success(majorService.addMajor(majors));
    }

    /**
     * 修改专业
     * @param majorVo
     * @return
     */
    @PutMapping("/modifyMajor")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<MajorVo> modifyMajor(@RequestBody MajorVo majorVo){
        if(majorVo.getSubjectRule()!=null){
            majorVo = SubjectScopeCount(majorVo);
        }
        List<SubjectRuleVo> subjectRuleVos = majorVo.getSubjectRule();
        List<List<String>> list = new ArrayList<>();
        List<Long> list1 = new ArrayList<>();
        for (SubjectRuleVo subjectRuleVo:subjectRuleVos){
             list1.add(subjectRuleVo.getAreaId());
        }
        list = areaService.getAreaProvinces(list1);
        for(int i=0;i<list.size();i++){
            for(int j=i+1;j<list.size();j++){
                List<String> combinedList = new ArrayList<>(list.get(i));
                combinedList.addAll(list.get(j));
                Set<String> set = new HashSet<>();
                for (String item : combinedList) {
                    if (!set.add(item)) {
                        return ApiResp.fail(ResultCode.REGIONAL_DUPLICATION);
                    }
                }
            }
        }
        Major major = MajorVoMajor(majorVo);
        major = majorService.modifyMajor(major);
        MajorVo majorVo1 = MajorMajorVo(major);
        return ApiResp.success(majorVo1);
    }

    /**
     *查看学校专业
     */
    @GetMapping("/selectSchoolMajor")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<IPage<MajorVo>> selectSchoolMajor(@RequestParam("schoolId")Long schoolId,@RequestParam("type") String type,
                                                     @RequestParam("word") String word,@RequestParam("current")Long current,
                                                     @RequestParam("size")Long size){
        new Page<>();
        IPage<Major> majors = switch (type) {
            case "" -> majorService.getSchoolMajors(schoolId, current, size);
            case "学院" -> majorService.getCollegeMajors(schoolId, word, current, size);
            case "专业名称" -> majorService.getMajors(schoolId, word, current, size);
            default -> new Page<>();
        };
        List<MajorVo> majorVos = new ArrayList<>();
          for(Major major:majors.getRecords()){
              MajorVo majorVo = MajorMajorVo(major);
              majorVos.add(majorVo);
          }
          IPage<MajorVo> majorVoIPage = new Page<>();
          majorVoIPage.setRecords(majorVos);
          majorVoIPage.setCurrent(current);
          majorVoIPage.setSize(size);
          majorVoIPage.setTotal(majors.getTotal());
          majorVoIPage.setPages(majors.getPages());
          return ApiResp.success(majorVoIPage);
    }
    /**
     *查看学校专业
     */
    @GetMapping("/selectSchoolMajor1")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<List<MajorVo>> selectSchoolMajor1(@RequestParam("schoolId")Long schoolId){
        List<Major> majors = majorService.getSchoolMajor(schoolId);
        List<MajorVo> majorVos = new ArrayList<>();
        for(Major major:majors){
            MajorVo majorVo = MajorMajorVo(major);
            majorVos.add(majorVo);
        }
        return ApiResp.success(majorVos);
    }
    /**
     * 删除专业
     * @param majorId
     * @return
     */
    @DeleteMapping("/deleteMajor")
    @Identity(IdentityEnum.SUPER)
    public ApiResp<Boolean> deleteMajor(@RequestParam("MajorId")Long majorId) {
        Boolean deleteArea = majorService.deleteMajor(majorId);
        return ApiResp.success(deleteArea);
    }
    public static  MajorVo MajorMajorVo(Major major){
        MajorVo majorVo =new MajorVo();
        majorVo.setName(major.getName());
        majorVo.setEnrollmentNumber(major.getEnrollmentNumber());
        majorVo.setSchoolId(major.getSchoolId());
        majorVo.setMajorId(major.getMajorId());
        majorVo.setCollege(major.getCollege());
        List<SubjectRuleVo> subjectRuleVos = JsonUtil.ListJson(major.getSubjectRule(),SubjectRuleVo.class);
        majorVo.setSubjectRule(subjectRuleVos);
        return majorVo;
    }
    private static  Major MajorVoMajor(MajorVo majorVo){
        Major major = new Major();
        major.setName(majorVo.getName());
        major.setEnrollmentNumber(majorVo.getEnrollmentNumber());
        major.setSchoolId(majorVo.getSchoolId());
        major.setMajorId(majorVo.getMajorId());
        major.setCollege(majorVo.getCollege());
        String subjectRuleVos = JsonUtil.object2StringSlice(majorVo.getSubjectRule());
        major.setSubjectRule(subjectRuleVos);
        return major;
    }
    public MajorVo SubjectScopeCount(MajorVo majorVo){
        List<SubjectRuleVo> subjectRuleVos = majorVo.getSubjectRule();
        for (SubjectRuleVo subjectRuleVo : subjectRuleVos) {
            Area area = areaService.getArea1(subjectRuleVo.getAreaId());
            List<List<String>> subjectScopes = new ArrayList<>();
            backtrack(subjectRuleVo.getOptionalSubjects().getOptionalSubjectScope(),
                    subjectRuleVo.getOptionalSubjects().getSubjectNumber(),0,new ArrayList<>(), subjectScopes);
            List<Integer> hashCodes = new ArrayList<>();
            for(List<String> subjectScope:subjectScopes){
                subjectScope.addAll(subjectRuleVo.getRequiredSubjects());
                List<List<String>> subjectScopes1 = new ArrayList<>();
                int number = area.getSubjectNumber()-subjectScope.size();
                List<String>subjectScope2 = JsonUtil.ListJson(area.getSubjectScope(),String.class);
                subjectScope2.removeAll(subjectScope);
                backtrack(subjectScope2,number,0,new ArrayList<>(),subjectScopes1);
                if(subjectScopes1.isEmpty()){
                    int hashCode = 0;
                    for (String string : subjectScope){
                        hashCode += string.hashCode();
                    }
                    if(!hashCodes.contains(hashCode)){
                        hashCodes.add(hashCode);
                    }
                }else {
                    for(List<String> subject:subjectScopes1){
                        List<String> subjectScope1=new ArrayList<>();
                        subjectScope1.addAll(subjectScope);
                        subjectScope1.addAll(subject);
                        int hashCode = 0;
                        for (String string : subjectScope1){
                            hashCode += string.hashCode();
                        }
                        if(!hashCodes.contains(hashCode)){
                            hashCodes.add(hashCode);
                        }
                    }
                }
                subjectRuleVo.setSubjectGroups(hashCodes);
            }
        }
        majorVo.setSubjectRule(subjectRuleVos);
        return majorVo;
    }
    private static void backtrack(List<String> list, int num, int start, List<String> current, List<List<String>> permutations){
        if (current.size() == num) {
            permutations.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < list.size(); i++){
            current.add(list.get(i));
            backtrack(list, num, i + 1, current, permutations);
            current.remove(current.size() - 1);
        }
    }

}
