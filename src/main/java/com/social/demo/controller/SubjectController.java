package com.social.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.common.ApiResp;
import com.social.demo.common.JsonUtil;
import com.social.demo.dao.repository.IAreaService;
import com.social.demo.dao.repository.ISubjectService;
import com.social.demo.data.vo.AreaVo;
import com.social.demo.entity.Area;
import com.social.demo.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * 科目接口
 *
 * @author 周威宇
 */
@RestController
@RequestMapping("/subject")
@Validated
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;
    /**
     * 添加科目
     */
    @PostMapping("/addSubject")
    public ApiResp<Boolean> addSubject(@RequestBody Subject subject){
         return ApiResp.success(subjectService.addSubject(subject));
    }

    /**
     *查看科目
     */
    @GetMapping("/selectSubject")
    public ApiResp<List<Subject>> selectSubject(){
      List<Subject> subjects = subjectService.getSubject();
      return ApiResp.success(subjects);
    }

    /**
     * 删除科目
     * @param subjectIds
     * @return
     */
    @DeleteMapping("/delete")
    public ApiResp<Boolean> deleteSubject(@RequestParam("subjectIds")List<Long> subjectIds){
        Boolean deleteSubject = null;
        for(int i=0;i<subjectIds.size();i++){
            deleteSubject = subjectService.deleteSubject(subjectIds.get(i));
        }
        return ApiResp.success(deleteSubject);
    }
}
