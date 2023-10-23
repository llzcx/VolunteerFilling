CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       gender VARCHAR(10) comment '性别',
                       nickname VARCHAR(50) COMMENT '昵称',
                       avatar_url VARCHAR(100) COMMENT  '头像url',
                       age INT,
                       wechat_id VARCHAR(50) COMMENT  '微信的id',
                       hometown VARCHAR(50) COMMENT '家乡',
                       location VARCHAR(50) COMMENT '地址',
                       height VARCHAR(10) COMMENT '身高',
                       marital_status VARCHAR(20) COMMENT '婚姻状态',
                       zodiac_sign VARCHAR(20) comment '星座',
                       personality_type VARCHAR(20) comment '性格类型',
                       occupation VARCHAR(50) COMMENT '职业'
);
