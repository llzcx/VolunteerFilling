# VolunteerFilling — 预科志愿填报系统

## 技术栈

| 层级 | 技术 |
|------|------|
| 框架 | Spring Boot 3.0.6 |
| ORM | MyBatis-Plus 3.5.3.1 |
| 数据库 | MySQL (dev/prod) / H2 (local) |
| 缓存 | Redis (JWT token 存储) |
| 认证 | auth0 java-jwt 3.8.2 (HMAC256) |
| 工具 | Hutool, Lombok |

## 分层架构

```
controller/     — REST 接口层，仅做参数校验和路由
  ├── auth/     — 认证相关（SysRoleController, SysApiController, TestController）
  └── *.java    — 业务 Controller（User, Student, ClassAdviser, AppraisalTeam, Wish, ...）

dao/
  ├── repository/       — Service 接口（IUserService, ...）
  │   └── impl/         — Service 实现（UserServiceImpl, ...）
  └── mapper/           — MyBatis Mapper 接口 + XML

entity/                 — 数据库实体（User, Student, Wish, Appraisal, ...）
data/
  ├── dto/              — 入参对象（LoginDto, AppealDto, ...）
  ├── vo/               — 出参对象（StudentVo, AppraisalVo, ...）
  └── bo/               — 内部传递对象（TokenPair, LoginBo, ...）

manager/
  ├── mvc/handlerInterceptor/ — RequestInterceptor（权限入口）
  ├── security/
  │   ├── identity/     — StaticIdentityImpl（静态RBAC）, DynamicIdentityImpl（动态RBAC，未启用）
  │   ├── jwt/          — JwtUtil（token 生成/校验/Redis 存取）
  │   └── config/       — PathConfig（放行路径）
  ├── file/             — 文件上传
  └── ratelimit/        — API 限流

common/                 — 全局组件（ApiResp, ResultCode, SystemException）
config/                 — Spring 配置（LocalDbInitializer, WebImgConfig）
constant/               — 枚举与常量（IdentityEnum, PropertiesConstant）
util/                   — 工具类（RedisUtil, URLUtil, TimeUtil）
```

## 五种角色

| 枚举 | code | 说明 |
|------|------|------|
| `STUDENT` | 0 | 学生 — 查看/修改个人信息、综测、志愿 |
| `TEACHER` | 1 | 老师 — 几乎无独立功能，可变更为班主任 |
| `APPRAISAL_TEAM` | 2 | 综测小组 — 管理综测成绩、签名、申诉 |
| `CLASS_ADVISER` | 3 | 班主任 — 管理班级学生、综测、申诉 |
| `SUPER` | 4 | 超级管理员 — 全部管理权限 |

## 权限体系

### 静态 RBAC（当前生效）

Controller 方法上加 `@Identity({IdentityEnum...})`，由 `StaticIdentityImpl` 校验：

```
RequestInterceptor.preHandle()
  → OPTIONS 放行
  → Release 路径放行（/user/login, /user/refresh/*, ...）
  → @Release 注解放行
  → Token 校验（JWT 解码 → Redis 比对 → 防重放）
  → AUTHORIZATION_CLOSE 开关（false = 开启权限校验）
  → StaticIdentityImpl.check()
      → 取 @Identity 注解的 value()
      → 遍历比对当前用户身份
      → 匹配 → pass，不匹配 → code 2009
```

### 动态 RBAC（未启用）

`DynamicIdentityImpl` 查 `sys_api` 表做 URL 模式匹配 (`AntPathMatcher`)，当前未被 Spring 托管。

### 权限绕过

- `@Release` — 完全跳过认证
- `@Excluded` — 标记该角色不可被动态分配（与静态 RBAC 无关）
- `AUTHORIZATION_CLOSE = true` — 全局关闭权限校验（`PropertiesConstant`，当前为 false）

## Profile 体系

| Profile | 数据库 | 用途 |
|---------|--------|------|
| `local` | H2 embedded | 本地开发，无需 MySQL/Docker |
| `dev` | MySQL | 开发联调 |
| `prod` | MySQL | 生产环境 |

`DevAndProd` 启动时检测 profile、操作系统、输出权限开关状态。

### local 环境特殊配置

- `application-local.yml` — H2 数据源 + 本地 Redis（无密码）
- `LocalDbInitializer` — 启动时自动执行 `schema-h2.sql` 建表
- `schema-h2.sql` — H2 兼容的 DDL（MySQL 语法转 H2）

## 认证流程

1. `POST /user/login` — 放行路径，MD5 密码校验 → 生成 JWT → 存 Redis
2. 后续请求 — `Authorization: <accessToken>`（注意：**不带 "Bearer " 前缀**）
3. Interceptor 解码 JWT → 从 Redis 取对应 token 比对 → 防重放
4. 提取用户 identity → StaticIdentityImpl 校验

JWT 结构：`{type:"Jwt", alg:"HS256"}.{exp, userId}.signature`

## 关键约定

- 所有响应 HTTP 200，通过 JSON `code` 字段判断成败
- 统一响应体: `ApiResp<T>` (`success`, `code`, `msg`, `data`)
- 密码: hutool `DigestUtil.md5Hex()` = 标准 MD5
- 初始密码: `123456`（`PropertiesConstant.PASSWORD`）
- 用户表在 `WISH` schema 下，H2 用反引号/双引号引用 `"user"` 表名

## 本地测试

详见 [docs/local-testing.md](docs/local-testing.md) — 应用启动、种子数据、登录、curl 调用、结果判断、常见问题。
