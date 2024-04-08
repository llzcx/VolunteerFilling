/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.21 : Database - wish
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wish` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `wish`;

/*Table structure for table `admissions_major` */

DROP TABLE IF EXISTS `admissions_major`;

CREATE TABLE `admissions_major` (
                                    `major_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `name` varchar(255) DEFAULT NULL COMMENT '专业名字',
                                    `college` varchar(255) DEFAULT NULL COMMENT '所属学院',
                                    `enrollment_number` int DEFAULT NULL COMMENT '剩余人数',
                                    `time_id` bigint DEFAULT NULL COMMENT '志愿时间段Id',
                                    `mate_way` int DEFAULT NULL COMMENT '匹配方式',
                                    PRIMARY KEY (`major_id`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `appeal` */

DROP TABLE IF EXISTS `appeal`;

CREATE TABLE `appeal` (
                          `appeal_id` bigint NOT NULL AUTO_INCREMENT COMMENT '申诉id',
                          `user_id` bigint NOT NULL COMMENT '申诉人id',
                          `class_id` bigint NOT NULL COMMENT '班级id',
                          `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '申诉内容',
                          `created` datetime NOT NULL COMMENT '申诉创建时间',
                          `state` int NOT NULL COMMENT '申诉状态\r\n0 - 待处理\r\n1- 已处理\r\n2- 已取消',
                          `last_ddl_time` datetime NOT NULL COMMENT '最近修改时间',
                          `type` tinyint(1) NOT NULL COMMENT '申述类型 0-综测 1-志愿',
                          PRIMARY KEY (`appeal_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `appraisal` */

DROP TABLE IF EXISTS `appraisal`;

CREATE TABLE `appraisal` (
                             `appraisal_id` bigint NOT NULL AUTO_INCREMENT COMMENT '综测id',
                             `user_id` bigint NOT NULL COMMENT '用户Id',
                             `month` int NOT NULL COMMENT '月份',
                             `score` double NOT NULL DEFAULT '100' COMMENT '综测总分',
                             `signature` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '是否提交签名',
                             `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内容',
                             PRIMARY KEY (`appraisal_id`) USING BTREE,
                             UNIQUE KEY `user_month_id` (`user_id`,`month`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `appraisal_signature` */

DROP TABLE IF EXISTS `appraisal_signature`;

CREATE TABLE `appraisal_signature` (
                                       `signature_id` int NOT NULL AUTO_INCREMENT,
                                       `class_id` int DEFAULT NULL,
                                       `signature` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                       `month` int NOT NULL,
                                       `user_id` int NOT NULL,
                                       PRIMARY KEY (`signature_id`),
                                       UNIQUE KEY `unique_key` (`month`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `appraisal_team` */

DROP TABLE IF EXISTS `appraisal_team`;

CREATE TABLE `appraisal_team` (
                                  `class_id` bigint NOT NULL COMMENT '班级id',
                                  `team_user_id` bigint NOT NULL COMMENT '小组成员id',
                                  PRIMARY KEY (`class_id`,`team_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `area` */

DROP TABLE IF EXISTS `area`;

CREATE TABLE `area` (
                        `area_id` bigint NOT NULL AUTO_INCREMENT COMMENT '地区id',
                        `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区名称',
                        `including_provinces` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '包含的省份',
                        `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                        `subject_scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科目范围',
                        `subject_number` int NOT NULL COMMENT '科目数量',
                        PRIMARY KEY (`area_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `autograph` */

DROP TABLE IF EXISTS `autograph`;

CREATE TABLE `autograph` (
                             `autograph_id` bigint NOT NULL AUTO_INCREMENT COMMENT '签名id',
                             `user_id` bigint DEFAULT NULL COMMENT '用户id',
                             `signature` varchar(255) DEFAULT NULL COMMENT '图片地址',
                             `frequency` int DEFAULT NULL COMMENT '填报次数',
                             `first_name` varchar(255) DEFAULT NULL COMMENT '第一志愿名字',
                             `second_name` varchar(255) DEFAULT NULL COMMENT '第二志愿名字',
                             `third_name` varchar(255) DEFAULT NULL COMMENT '第三志愿名字',
                             `time_id` bigint DEFAULT NULL COMMENT '时间id',
                             `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                             PRIMARY KEY (`autograph_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1241 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `class` */

DROP TABLE IF EXISTS `class`;

CREATE TABLE `class` (
                         `class_id` bigint NOT NULL AUTO_INCREMENT COMMENT '班级id',
                         `user_id` bigint DEFAULT NULL COMMENT '班主任id',
                         `class_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '班级名',
                         `year` int DEFAULT NULL COMMENT '年份',
                         PRIMARY KEY (`class_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `consignee` */

DROP TABLE IF EXISTS `consignee`;

CREATE TABLE `consignee` (
                             `user_id` bigint NOT NULL COMMENT '用户id',
                             `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '收件人',
                             `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电话号码',
                             `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '收件地址',
                             PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `grade_subject` */

DROP TABLE IF EXISTS `grade_subject`;

CREATE TABLE `grade_subject` (
                                 `grade_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户Id',
                                 `grade_name` varchar(255) NOT NULL COMMENT '分数',
                                 PRIMARY KEY (`grade_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `major` */

DROP TABLE IF EXISTS `major`;

CREATE TABLE `major` (
                         `major_id` bigint NOT NULL AUTO_INCREMENT COMMENT '专业id',
                         `school_id` bigint NOT NULL COMMENT '院校id',
                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '专业名称',
                         `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学院',
                         `subject_rule` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '选科规则',
                         `enrollment_number` int DEFAULT NULL COMMENT '招生人数',
                         PRIMARY KEY (`major_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=710 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `mate` */

DROP TABLE IF EXISTS `mate`;

CREATE TABLE `mate` (
                        `mate_id` bigint NOT NULL AUTO_INCREMENT,
                        `user_id` bigint DEFAULT NULL,
                        `major_name` varchar(255) DEFAULT NULL,
                        `major_id` bigint DEFAULT NULL,
                        `mate_way` int DEFAULT NULL,
                        `time_id` bigint DEFAULT NULL,
                        PRIMARY KEY (`mate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3931 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `operating_data` */

DROP TABLE IF EXISTS `operating_data`;

CREATE TABLE `operating_data` (
                                  `operating_data_id` bigint NOT NULL COMMENT '操作Id',
                                  `operation_time` datetime NOT NULL COMMENT '操作时间',
                                  `operand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作对象',
                                  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作内容',
                                  PRIMARY KEY (`operating_data_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `school` */

DROP TABLE IF EXISTS `school`;

CREATE TABLE `school` (
                          `number` bigint DEFAULT NULL COMMENT '院校编码',
                          `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学校名称',
                          `school_id` bigint NOT NULL AUTO_INCREMENT,
                          PRIMARY KEY (`school_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
                           `user_id` bigint NOT NULL COMMENT '用户Id',
                           `class_id` bigint DEFAULT NULL COMMENT '班级id',
                           `school_id` bigint DEFAULT NULL COMMENT '学校id',
                           `province` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '来源省份',
                           `plan` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性质计划',
                           `parent_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父母电话',
                           `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '家庭地址',
                           `enrollment_year` int DEFAULT NULL COMMENT '入学年份',
                           `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '身份证号码',
                           `state` int DEFAULT NULL COMMENT '状态 0-未填报 1-填报中 2-已填报',
                           `hashcode` int DEFAULT NULL COMMENT '组合哈希',
                           `score` double DEFAULT NULL COMMENT '总成绩',
                           `grade` varchar(255) DEFAULT NULL COMMENT '每科成绩',
                           `appraisal_score` double DEFAULT NULL COMMENT '综测成绩',
                           PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
                           `subject_id` bigint NOT NULL AUTO_INCREMENT COMMENT '学科id',
                           `subject_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学科名字',
                           PRIMARY KEY (`subject_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `subject_group` */

DROP TABLE IF EXISTS `subject_group`;

CREATE TABLE `subject_group` (
                                 `group_id` bigint NOT NULL AUTO_INCREMENT COMMENT '组合id',
                                 `subjects` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学科',
                                 `hashcode` int NOT NULL COMMENT '哈希值',
                                 PRIMARY KEY (`group_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `subject_rule` */

DROP TABLE IF EXISTS `subject_rule`;

CREATE TABLE `subject_rule` (
                                `subject_rule_id` bigint NOT NULL COMMENT '科目规则id',
                                `subject_scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科目范围',
                                `required_subjects` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '必选科目',
                                `optional_subjects` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任选科目',
                                `strings` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '前端字符串',
                                PRIMARY KEY (`subject_rule_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `sys_api` */

DROP TABLE IF EXISTS `sys_api`;

CREATE TABLE `sys_api` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `pattern` varchar(255) DEFAULT NULL,
                           `description` varchar(255) DEFAULT NULL,
                           `roles` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            `status` bit(1) DEFAULT NULL COMMENT '状态',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `sys_role_api` */

DROP TABLE IF EXISTS `sys_role_api`;

CREATE TABLE `sys_role_api` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `role_id` bigint NOT NULL,
                                `api_id` bigint NOT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `UNIQUE` (`role_id`,`api_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `sys_role_vo` */

DROP TABLE IF EXISTS `sys_role_vo`;

CREATE TABLE `sys_role_vo` (
                               `role_id` int DEFAULT NULL,
                               `role_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL,
                                 `role_id` bigint NOT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE KEY `UNIQUE` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
                        `user_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号/工号',
                        `nation` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '民族',
                        `politics_status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '政治面貌',
                        `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
                        `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系电话',
                        `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
                        `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性别',
                        `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
                        `last_ddl_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
                        `identity` int DEFAULT NULL COMMENT '角色',
                        `headshot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '证件照',
                        PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5361 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `wish` */

DROP TABLE IF EXISTS `wish`;

CREATE TABLE `wish` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '志愿填报id',
                        `user_id` bigint NOT NULL COMMENT '用户id',
                        `first` bigint DEFAULT NULL COMMENT '第一志愿',
                        `second` bigint DEFAULT NULL COMMENT '第二志愿',
                        `third` bigint DEFAULT NULL COMMENT '第三志愿',
                        `time_id` bigint DEFAULT NULL COMMENT '志愿填报时间',
                        `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '第一志愿名字',
                        `second_name` varchar(255) DEFAULT NULL COMMENT '第二志愿名字',
                        `third_name` varchar(255) DEFAULT NULL COMMENT '第三志愿名字',
                        `frequency` int DEFAULT NULL COMMENT '填报次数',
                        `admission_result_name` varchar(255) DEFAULT NULL COMMENT '录取结果名字',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1638 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `wish_time` */

DROP TABLE IF EXISTS `wish_time`;

CREATE TABLE `wish_time` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '志愿id',
                             `start_time` datetime NOT NULL COMMENT '开始时间',
                             `end_time` datetime NOT NULL COMMENT '结束时间',
                             `type` tinyint(1) NOT NULL COMMENT '是否为正式填报0否,1是',
                             `school_id` bigint NOT NULL COMMENT '学校名称',
                             `ago` int DEFAULT NULL COMMENT '入学年份',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
