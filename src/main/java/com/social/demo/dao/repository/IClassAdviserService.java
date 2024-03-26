package com.social.demo.dao.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.social.demo.data.vo.ClassUserVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;


public interface IClassAdviserService {

    String uploadSignature(MultipartFile file, Integer month, HttpServletRequest request) throws Exception;

    IPage<ClassUserVo> getStudents(HttpServletRequest request, String keyword, String role, Integer rank, Integer current, Integer size);

}
