package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.social.demo.common.ApiResp;
import com.social.demo.common.JsonUtil;
import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.dao.repository.*;
import com.social.demo.data.vo.*;
import com.social.demo.entity.*;
import com.social.demo.entity.Class;
import com.social.demo.manager.security.context.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.social.demo.controller.MajorController.MajorMajorVo;

/**
 * 志愿接口
 *
 * @author 周威宇
 */
@RestController
@RequestMapping("/wish")
@Validated
public class WishController {
    @Autowired
    private IWishService wishService;
    @Autowired
    private IMajorService majorService;
    @Autowired
    private IAreaService areaService;
    @Autowired
    private IClassService classService;
     @Autowired
    private IStudentService studentService;
    /**
     * 填写志愿接口
     */
    @PostMapping("/addWish")
    public ApiResp<Boolean> addWish(@RequestBody Wish wish){
        Long userId = SecurityContext.get().getUserId();
        wish.setUserId(userId);
        return ApiResp.success(wishService.addWish(wish));
    }
    /**
     * 修改个人志愿接口
     */
    @PutMapping("modifyWish")
    public ApiResp<Boolean> modifyWish(@RequestBody WishVo1 wishVo1){
        System.out.println(wishVo1);
        Long userId = SecurityContext.get().getUserId();
        Wish wish = wishVo1Wish(wishVo1);
        WishVo wishVo =wishService.selectWish(userId,wishVo1.getTimeId());
        wish.setUserId(userId);
        wish.setFrequency(wishVo.getFrequency()-1);
        wish.setId(wishVo.getId());
        if(wish.getFrequency()<0){
            throw new SystemException(ResultCode.EXCEEDED_THE_LIMIT_OF);
        }
        return ApiResp.success(wishService.modifyWish(wish));
    }

    /**
     *个人查看志愿接口
     */
    @GetMapping("selectWish")
    public ApiResp<WishVo> selectWish(@RequestParam("timeId")Long timeId)
    {
        Long userId = SecurityContext.get().getUserId();
        WishVo wishVo =wishService.selectWish(userId,timeId);
        return ApiResp.success(wishVo);
    }
    /**
     * 查看一个班的学生志愿
     */
    @GetMapping("selectClassWish")
    public ApiResp<IPage<WishClass>> selectClassWish(@RequestParam("timeId") Long timeId, @RequestParam("current") Long current,
                                                     @RequestParam("size") Long size){
        Long userId = SecurityContext.get().getUserId();
        Class aClass = classService.getClass(userId);
        IPage<WishClass> wishClassIPage = new Page<>();
        wishClassIPage.setRecords(classService.getClassUserId(aClass.getClassId(),timeId,current,size));
        wishClassIPage.setCurrent(current);
        wishClassIPage.setTotal(classService.getClass1(aClass.getClassId(),timeId));
        wishClassIPage.setSize(size);
        return ApiResp.success(wishClassIPage);
    }
    /**
     * 查看学生专业范围
     * @return
     */
    @GetMapping("selectStudentMajor")
    public ApiResp<List<StudentMajorVo>> selectStudentMajor(){
        Long userId = SecurityContext.get().getUserId();
        Student student = studentService.getStudent(userId);
        String  address1 = student.getProvince().substring(0,2);
    List<Area> areas = areaService.getAreas();
    List<Long> areaIds = new ArrayList<>();
    for(Area area:areas){
        List<String> includingProvinces= JsonUtil.ListJson(area.getIncludingProvinces(),String.class);
        if(includingProvinces.contains(address1)){
            areaIds.add(area.getAreaId());
        }
    }

    List<Major> majors = majorService.getSchoolMajor(student.getSchoolId());

    HashMap<String,List<MajorVo1>> map = new HashMap<>();
        List<MajorVo> majorVos = new ArrayList<>();
        for(Major major:majors){
            MajorVo majorVo = MajorMajorVo(major);
            majorVos.add(majorVo);
        }
    for(MajorVo majorVo:majorVos){
        List<MajorVo1> majors1;
        if(map.containsKey(majorVo.getCollege())){
            majors1 = map.get(majorVo.getCollege());
        }else {
            majors1 = new ArrayList<>();
        }
        for(SubjectRuleVo subjectRuleVo : majorVo.getSubjectRule()){
            if(areaIds.contains(subjectRuleVo.getAreaId())){
              if(subjectRuleVo.getSubjectGroups().contains(student.getHashcode())){
                  MajorVo1 majorVo1 = new MajorVo1();
                  majorVo1.setMajorId(majorVo.getMajorId());
                  majorVo1.setName(majorVo.getName());
                  majors1.add(majorVo1);
                  map.put(majorVo.getCollege(),majors1);
              }
            }
        }
    }

    List<StudentMajorVo> studentMajorVos = new ArrayList<>();
    for(Map.Entry<String, List<MajorVo1>> entry : map.entrySet()){
        StudentMajorVo studentMajorVo = new StudentMajorVo();
        studentMajorVo.setMajors(entry.getValue());
        studentMajorVo.setCollege(entry.getKey());
        studentMajorVos.add(studentMajorVo);
    }
    return ApiResp.success(studentMajorVos);
    }
    public Wish wishVoWish(WishVo wishVo){
        Wish wish = new Wish();
        wish.setFirst(wishVo.getFirst());
        wish.setFirstName(wishVo.getFirstName());
        wish.setSecond(wishVo.getSecond());
        wish.setSecondName(wishVo.getSecondName());
        wish.setThird(wishVo.getThird());
        wish.setThirdName(wishVo.getThirdName());
        wish.setId(wishVo.getId());
        wish.setTimeId(wishVo.getTimeId());
        wish.setFrequency(wishVo.getFrequency());
        wish.setUserId(wishVo.getUserId());
        return wish;
    }
    public Wish wishVo1Wish(WishVo1 wishVo){
        Wish wish = new Wish();
        wish.setFirst(wishVo.getFirst());
        wish.setFirstName(wishVo.getFirstName());
        wish.setSecond(wishVo.getSecond());
        wish.setSecondName(wishVo.getSecondName());
        wish.setThird(wishVo.getThird());
        wish.setThirdName(wishVo.getThirdName());
        wish.setTimeId(wishVo.getTimeId());
        return wish;
    }
}
