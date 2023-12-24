package com.social.demo.manager.file;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 * @author 陈翔
 */

public interface UploadFile {

    /**
     * 上传文件
     *
     * @param file
     * @param filesName
     * @param filename
     */
    String upload(MultipartFile file, String filesName ,String filename) throws Exception;

    Boolean download(HttpServletResponse response, String filesName, String filename) throws Exception;
}