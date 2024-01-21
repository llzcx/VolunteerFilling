package com.social.demo.constant;

public interface PropertiesConstant {
    //初始密码
    String PASSWORD = "123456";

    //身份：学生
    Integer IDENTITY_STUDENT = 1;

    //身份：老师
    Integer IDENTITY_TEACHER = 0;

    //身份：综测成员
    Integer IDENTITY_APPRAISAL_TEAM = 2;

    //身份：班主任
    Integer IDENTITY_CLASS_ADVISER = 3;

    //文件前缀-综测用户签名
    String APPRAISAL = "appraisal";

    //文件夹前缀-综测用户签名
    String APPRAISALS = "appraisals";

    //文件前缀-用户证件照
    String USER = "user";

    //文件夹前缀-用户证件照
    String USERS = "users";

    //申诉状态 - 待处理
    Integer APPEAL_STATE_PENDING = 0;

    //申诉状态 - 已处理
    Integer APPEAL_STATE_PROCESSED = 1;

    //申诉状态 - 已取消
    Integer APPEAL_STATE_CANCELED = 2;
}
