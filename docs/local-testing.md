# 本地开发与测试指南

## 启动应用

```bash
cd D:/project/java/VolunteerFilling
mvn spring-boot:run -Dspring-boot.run.profiles=local -q
```

- 端口: **8081**
- 数据库: H2 embedded (`./data/localdb.mv.db`)
- H2 Console: http://localhost:8081/h2-console (JDBC URL: `jdbc:h2:file:./data/localdb`, 用户名 sa, 无密码)

### 前置条件

Redis 必须在 localhost:6379 运行且**无密码**：

```bash
redis-server
```

## 种子数据

本地 H2 数据库初始为空，需要灌入测试用户。

### 一键脚本

```bash
# 1. 复制 H2 jar
mvn dependency:copy-dependencies -DincludeArtifactIds=h2 -DoutputDirectory=/tmp/h2jar -q

# 2. 生成种子 SQL（schema + 5 个测试用户）
cat > /tmp/seed.sql << 'SQLEOF'
CREATE SCHEMA IF NOT EXISTS WISH;
SET SCHEMA WISH;
SQLEOF
cat src/main/bin/schema-h2.sql >> /tmp/seed.sql
cat >> /tmp/seed.sql << 'SQLEOF'
INSERT INTO "user" (user_number, username, sex, politics_status, nation, phone, password, created, last_ddl_time, identity)
VALUES
('super01', 'Super Admin', 'M', 'Party', 'Han', '13800000001', '1e0a55d2ab93ff0fec2dc379284f05b3', NOW(), NOW(), 4),
('adviser01', 'Class Adviser', 'M', 'Party', 'Han', '13800000002', '30d925171b39454c503505d83a6086e5', NOW(), NOW(), 3),
('team01', 'Appraisal Team', 'F', 'League', 'Han', '13800000003', '8f4d38c15d48ec79d6a09ffc05733558', NOW(), NOW(), 2),
('student01', 'Test Student', 'M', 'League', 'Han', '13800000004', 'cd41287b93a9317b6b2d1da8bec1def1', NOW(), NOW(), 0),
('teacher01', 'Test Teacher', 'F', 'Party', 'Han', '13800000005', 'ea62920343f2ea175f749d7da6ab3792', NOW(), NOW(), 1);
SQLEOF

# 3. 执行（注意 JDBC URL 用绝对路径）
java -cp /tmp/h2jar/h2-2.1.214.jar org.h2.tools.RunScript \
  -url "jdbc:h2:file:$(pwd | sed 's|^/d|D:|')/data/localdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE;NON_KEYWORDS=USER,SECOND,FIRST,MONTH,YEAR,VALUE" \
  -user sa -password "" \
  -script /tmp/seed.sql
```

密码用 hutool `DigestUtil.md5Hex()` 即标准 MD5：
```
printf 'Super@123' | md5sum   # → 1e0a55d2ab93ff0fec2dc379284f05b3
```

### 测试账号

| 账号 | 密码 | 角色 | code |
|------|------|------|------|
| super01 | Super@123 | SUPER (超级管理员) | 4 |
| adviser01 | Adviser@123 | CLASS_ADVISER (班主任) | 3 |
| team01 | Team@123 | APPRAISAL_TEAM (综测小组) | 2 |
| student01 | Student@123 | STUDENT (学生) | 0 |
| teacher01 | Teacher@123 | TEACHER (老师) | 1 |

## 登录与 Token

```bash
# 登录
curl -s -X POST http://localhost:8081/user/login \
  -H "Content-Type: application/json" \
  -d '{"type":1,"userNumber":"super01","password":"Super@123"}'

# 提取 token
TOKEN=$(curl -s ... | grep -o '"accessToken":"[^"]*"' | sed 's/"accessToken":"//' | tr -d '"')
```

## 调用接口

**重要**: Authorization 头**不带 "Bearer " 前缀**：

```bash
curl -s "http://localhost:8081/some/endpoint" -H "Authorization: $TOKEN"
```

## 判断结果

所有响应 HTTP 200，检查 JSON 的 `code` 字段：

| code | 含义 |
|------|------|
| 200 | 成功（权限通过） |
| 2009 | "您的身份无权访问" — 权限拒绝 |
| 2005 | "token解码失败" — token 无效或过期 |
| 2037 | "禁止匿名请求" — 没传 token |
| 其他 | 业务逻辑错误（权限已通过，后面环节出的错） |

## 调试

- H2 Console: http://localhost:8081/h2-console
- 权限日志: `用户为：{id}，本次访问的身份是：{role}`
- "Database may be already in use": `taskkill //F //IM java.exe` 然后删 `./data/localdb.lock.db`
- 不想每次灌数据: 保留 `./data/localdb.mv.db` 不删，重启后数据还在
