package com.social.demo.manager.file.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

import com.social.demo.common.ResultCode;
import com.social.demo.common.SystemException;
import com.social.demo.constant.DevAndProd;
import com.social.demo.manager.file.UploadFile;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author 陈翔
 */
@Slf4j
@Service("local")
public class LocalImpl implements UploadFile {

    @Value("${file-picture.address.path}")
    private String path;

    @Autowired
    DevAndProd devAndProd;

    @Override
    public String upload(MultipartFile file, String filesName, String filename) throws Exception {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的类型
        String type = FileUtil.extName(originalFilename);
        log.info("文件类型是：" + type);

        //获取文件
        File uploadParentFile = new File(path + filesName);
        //判断文件目录是否存在
        if(!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        String fileName = filename+ StrUtil.DOT + type;
        File uploadFile = new File(path + filesName + devAndProd.getFileSplit() + fileName);
        //将临时文件转存到指定磁盘位置
        file.transferTo(uploadFile);
        return "/image/" + filesName + "/" + fileName;
    }

    @Override
    public Boolean download(HttpServletResponse response, String filesName, String filename) throws Exception {
        File file = new File(path + filesName + "\\" + filename);
        if (!file.exists()){
           throw new SystemException(ResultCode.HEADSHOT_NOT_EXISTS);
        }
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType("application/octet-stream");
        os.write(FileUtil.readBytes(file));
        os.flush();
        os.close();

        return true;
    }
}
