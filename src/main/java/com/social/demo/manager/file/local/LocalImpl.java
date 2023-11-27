package com.social.demo.manager.file.local;

import com.social.demo.manager.file.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 陈翔
 */
@Slf4j
@Service("local")
public class LocalImpl implements UploadFile {
    @Override
    public String upload(MultipartFile file) throws Exception {
        return null;
    }
}
