package com.social.demo.manager.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 * @author 陈翔
 */

public interface UploadFile {

    /**
     * 上传文件
     * @param file
     */
    String upload(MultipartFile file) throws Exception;
}