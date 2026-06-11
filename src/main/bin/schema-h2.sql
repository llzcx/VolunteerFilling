-- H2 Schema for local development (MySQL compatibility mode)
CREATE SCHEMA IF NOT EXISTS WISH;

CREATE TABLE IF NOT EXISTS wish.admissions_major (
    major_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    college VARCHAR(255),
    enrollment_number INT,
    mate_way INT,
    time_id BIGINT,
    classification VARCHAR(255),
    surplus_number INT,
    first INT,
    second INT,
    third INT
);

CREATE TABLE IF NOT EXISTS wish.appeal (
    appeal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    class_id BIGINT,
    content VARCHAR(512),
    created TIMESTAMP,
    state INT,
    last_ddl_time TIMESTAMP,
    type TINYINT,
    record_delete TINYINT
);

CREATE TABLE IF NOT EXISTS wish.appraisal (
    appraisal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    month INT,
    score DOUBLE,
    signature VARCHAR(255),
    content VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS wish.appraisal_signature (
    signature_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    class_id BIGINT,
    signature VARCHAR(255),
    month INT,
    user_id BIGINT
);

CREATE TABLE IF NOT EXISTS wish.appraisal_team (
    class_id BIGINT NOT NULL,
    team_user_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS wish.area (
    area_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    including_provinces VARCHAR(512),
    update_time TIMESTAMP,
    subject_scope VARCHAR(255),
    subject_number INT
);

CREATE TABLE IF NOT EXISTS wish.autograph (
    autograph_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    signature VARCHAR(255),
    frequency INT,
    first_name VARCHAR(255),
    second_name VARCHAR(255),
    third_name VARCHAR(255),
    time_id BIGINT,
    update_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS wish.class (
    class_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    class_name VARCHAR(255),
    year INT
);

CREATE TABLE IF NOT EXISTS wish.consignee (
    user_id BIGINT PRIMARY KEY,
    username VARCHAR(255),
    phone VARCHAR(255),
    address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wish.grade_subject (
    grade_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    grade_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wish.major (
    major_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id BIGINT,
    name VARCHAR(255),
    college VARCHAR(255),
    subject_rule VARCHAR(255),
    enrollment_number INT
);

CREATE TABLE IF NOT EXISTS wish.mate (
    mate_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    major_id BIGINT,
    major_name VARCHAR(255),
    mate_way INT,
    time_id BIGINT,
    college VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wish.school (
    school_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number BIGINT,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wish.student (
    user_id BIGINT PRIMARY KEY,
    class_id BIGINT,
    school_id BIGINT,
    province VARCHAR(255),
    plan VARCHAR(255),
    parent_phone VARCHAR(255),
    address VARCHAR(255),
    enrollment_year INT,
    hashcode INT,
    id_card VARCHAR(255),
    state INT,
    score DOUBLE,
    grade VARCHAR(512),
    appraisal_score DOUBLE
);

CREATE TABLE IF NOT EXISTS wish.subject_group (
    group_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subjects VARCHAR(512),
    hashcode INT
);

CREATE TABLE IF NOT EXISTS wish.subject (
    subject_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wish.sys_api (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pattern VARCHAR(255),
    description VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS wish.sys_role_api (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    api_id BIGINT,
    role_id INT
);

CREATE TABLE IF NOT EXISTS wish.user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_number VARCHAR(255),
    username VARCHAR(255),
    sex VARCHAR(10),
    politics_status VARCHAR(50),
    nation VARCHAR(50),
    phone VARCHAR(255),
    password VARCHAR(255),
    created TIMESTAMP,
    last_ddl_time TIMESTAMP,
    identity INT,
    headshot VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wish.user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    role_id BIGINT
);

CREATE TABLE IF NOT EXISTS wish.wish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    first BIGINT,
    first_name VARCHAR(255),
    second BIGINT,
    second_name VARCHAR(255),
    third BIGINT,
    third_name VARCHAR(255),
    time_id BIGINT,
    frequency INT,
    admission_result_id BIGINT,
    admission_result_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wish.wish_time (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type TINYINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    school_id BIGINT,
    ago INT
);

CREATE TABLE IF NOT EXISTS wish.user_subject (
    user_id BIGINT,
    subject_name VARCHAR(255)
);
