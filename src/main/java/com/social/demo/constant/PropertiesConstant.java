package com.social.demo.constant;

public interface PropertiesConstant {
    //初始密码
    String PASSWORD = "123456";

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
}
