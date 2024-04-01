package com.social.demo.constant;

import org.springframework.beans.factory.annotation.Value;

public interface PropertiesConstant {
    //初始密码
    String PASSWORD = "123456";
    //文件夹前缀-用户头像
    @Value("${file.address.headshot}")
    String HEADSHOT = "headshot";

    //文件夹前缀-学生综测签名
    @Value("${file.address.signature.student}")
    String SIGNATURE_STUDENTS = "signature/student";
    //文件夹前缀-志愿签名
    @Value("${file.address.signature.wish}")
    String SIGNATURE_WISH = "signature/wish";
    //文件夹前缀-综测小组综测签名
    @Value("${file.address.signature.team}")
    String SIGNATURE_TEAM = "signature/team";

    //文件夹前缀-班主任综测签名
    @Value("${file.address.signature.teacher}")
    String SIGNATURE_TEACHER = "signature/teacher";

    //申诉状态 - 待处理
    Integer APPEAL_STATE_PENDING = 0;

    //申诉状态 - 已处理
    Integer APPEAL_STATE_PROCESSED = 1;

    //申诉状态 - 已取消
    Integer APPEAL_STATE_CANCELED = 2;

    // 语文科目编号
    Long SUBJECT_CHINESE = 1L;
    // 数学科目编号
    Long SUBJECT_MATH = 2L;
    // 英语科目编号
    Long SUBJECT_ENGLISH = 3L;

    //开发者超管【此超管不允许降级】，开发者专属
    Long DEVELOPER_ADMIN_USER_ID = 100L;

    /**
     * 此开关并不会影响accessToken的验证，只会影响身份验证。
     * 验权开关，false：表示开启权限验证
     *         true：表示关闭权限功能，便于部分接口测试。
     */
    boolean AUTHORIZATION_CLOSE = true;

    //成绩初始分数
    Double SCORE = 100.00;

    //综测成绩初始分数
    Double APPRAISAL_SCORE = 100.00;
    @Value("${file.url}")
    String URL = "http://192.168.50.35:8081";
}
