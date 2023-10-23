package com.social.demo.manager.file.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.social.demo.manager.file.UploadFile;
import com.social.demo.manager.file.oss.config.OssPropertiesConfig;
import com.social.demo.util.TimeUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
 
/**
 * @author 陈翔
 */
@Component
public class OssImpl implements UploadFile {

    /**
     * 阿里云上传文件
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public String upload(MultipartFile file)  throws Exception{
        String endpoint = OssPropertiesConfig.END_POINT;
        String accessKeyId = OssPropertiesConfig.ACCESS_KEY_ID;
        String accessKeySecret = OssPropertiesConfig.ACCESS_KEY_SECRET;
        String bucketName = OssPropertiesConfig.BUCKET_NAME;
        String url = null;
 
        //创建OSSClient实例。
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
 
        //获取上传文件输入流
        InputStream inputStream = file.getInputStream();
        //获取文件名称
        String fileName = file.getOriginalFilename();
 
        //保证文件名唯一，去掉uuid中的'-'
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = uuid + fileName;
 
        //把文件按日期分类，构建日期路径：avatar/2019/02/26/文件名
        String datePath = TimeUtil.getCurrentDateDirectory();
        //拼接
        fileName = datePath + "/" + fileName;
 
        //调用oss方法上传到阿里云
        //第一个参数：Bucket名称
        //第二个参数：上传到oss文件路径和文件名称
        //第三个参数：上传文件输入流
        ossClient.putObject(bucketName, fileName, inputStream);
 
        //把上传后把文件url返回
        //https://xppll.oss-cn-beijing.aliyuncs.com/01.jpg
        url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        //关闭OSSClient
        ossClient.shutdown();
        return url;
    }
}