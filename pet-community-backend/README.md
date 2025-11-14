# 宠物社区应用后端系统

基于HarmonyOS的宠物社区应用后端服务，采用Spring Boot 3.1.5 + MyBatis Plus + MySQL 8.0架构。

## 项目简介

本项目是一款面向宠物主的综合性社区应用后端系统，提供宠物档案管理、社区互动、健康管理、商城购物、服务预约等功能。

### 技术栈

- **开发语言**: Java 17
- **开发框架**: Spring Boot 3.1.5
- **ORM框架**: MyBatis Plus 3.5.3
- **数据库**: MySQL 8.0
- **缓存**: Redis 7.0
- **安全框架**: Spring Security + JWT
- **API文档**: Knife4j 4.1.0
- **工具库**: Hutool 5.8.20

## 项目结构

```
pet-community-backend/
├── src/main/java/com/petcommunity/
│   ├── PetCommunityApplication.java    # 启动类
│   ├── config/                          # 配置类
│   │   ├── MyBatisPlusConfig.java      # MyBatis Plus配置
│   │   ├── Knife4jConfig.java          # API文档配置
│   │   ├── SecurityConfig.java         # 安全配置
│   │   └── WebMvcConfig.java           # Web配置
│   ├── common/                          # 公共模块
│   │   ├── constant/                   # 常量
│   │   ├── enums/                      # 枚举
│   │   ├── exception/                  # 异常处理
│   │   └── result/                     # 统一响应
│   ├── controller/                      # 控制器
│   │   ├── UserController.java         # 用户接口
│   │   └── PetController.java          # 宠物接口
│   ├── service/                         # 服务层
│   │   ├── UserService.java
│   │   ├── PetService.java
│   │   └── impl/                       # 实现类
│   ├── mapper/                          # 数据访问层
│   │   ├── UserMapper.java
│   │   └── PetMapper.java
│   ├── entity/                          # 实体类
│   │   ├── User.java
│   │   └── Pet.java
│   ├── dto/                             # 数据传输对象
│   │   ├── LoginDTO.java
│   │   ├── RegisterDTO.java
│   │   └── PetDTO.java
│   └── utils/                           # 工具类
│       ├── JwtUtil.java                # JWT工具
│       ├── PasswordUtil.java           # 密码工具
│       └── SecurityUtils.java          # 安全工具
├── src/main/resources/
│   ├── application.yml                  # 主配置文件
│   ├── application-dev.yml              # 开发环境配置
│   └── application-prod.yml             # 生产环境配置
├── db/
│   └── init.sql                         # 数据库初始化脚本
├── Dockerfile                           # Docker镜像构建文件
├── docker-compose.yml                   # Docker编排文件
└── pom.xml                              # Maven配置文件
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+ (可选)

### 本地开发

#### 1. 克隆项目

```bash
git clone <repository-url>
cd pet-community-backend
```

#### 2. 配置数据库

修改 `src/main/resources/application-dev.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/pet_community?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码
```

#### 3. 初始化数据库

```bash
# 登录MySQL
mysql -u root -p

# 执行初始化脚本
source db/init.sql
```

#### 4. 启动项目

```bash
# 使用Maven启动
mvn spring-boot:run

# 或者打包后启动
mvn clean package
java -jar target/pet-community-backend-1.0.0.jar
```

#### 5. 访问接口文档

启动成功后，访问：http://localhost:8080/doc.html

## Docker部署

### 使用Docker Compose一键部署

```bash
# 构建并启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f backend

# 停止服务
docker-compose down
```

### 单独构建Docker镜像

```bash
# 先打包项目
mvn clean package

# 构建镜像
docker build -t pet-community-backend:1.0.0 .

# 运行容器
docker run -d \
  --name pet-backend \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e MYSQL_HOST=your-mysql-host \
  -e MYSQL_PASSWORD=your-password \
  pet-community-backend:1.0.0
```

## API接口

### 用户管理

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 用户注册 | POST | /api/user/register | 新用户注册 |
| 用户登录 | POST | /api/user/login | 用户登录，返回Token |
| 获取用户信息 | GET | /api/user/info | 获取当前用户信息 |
| 更新用户信息 | PUT | /api/user/info | 更新个人信息 |
| 修改密码 | PUT | /api/user/password | 修改登录密码 |
| 退出登录 | POST | /api/user/logout | 退出登录 |

### 宠物档案

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 添加宠物 | POST | /api/pet | 添加宠物档案 |
| 获取宠物列表 | GET | /api/pet/list | 获取用户的宠物列表 |
| 获取宠物详情 | GET | /api/pet/{petId} | 获取宠物详细信息 |
| 更新宠物信息 | PUT | /api/pet/{petId} | 更新宠物档案 |
| 删除宠物 | DELETE | /api/pet/{petId} | 删除宠物档案 |

## 配置说明

### JWT配置

在 `application.yml` 中配置JWT参数：

```yaml
jwt:
  secret: your-secret-key              # JWT密钥
  expiration: 604800000                # 过期时间(7天)
  header: Authorization                # Header名称
  prefix: Bearer                       # Token前缀
```

### 文件上传配置

```yaml
file:
  upload:
    path: /data/upload/                # 文件存储路径
    url-prefix: /files/                # 访问URL前缀
```

## 测试账号

系统初始化时会创建以下测试账号：

- **管理员账号**
  - 用户名: `admin`
  - 密码: `123456`

- **测试用户**
  - 用户名: `testuser`
  - 密码: `123456`

## 常见问题

### 1. 启动失败：数据库连接错误

检查MySQL是否启动，数据库配置是否正确。

### 2. JWT验证失败

确保请求头中包含正确的Token：
```
Authorization: Bearer <your-token>
```

### 3. 文件上传失败

检查上传目录是否存在且有写权限：
```bash
mkdir -p /data/upload
chmod 755 /data/upload
```

## 开发规范

### 代码规范

- 遵循阿里巴巴Java开发手册
- 使用Lombok简化代码
- 统一异常处理
- 统一响应格式

### Git提交规范

```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试
chore: 构建/工具变动
```

## 项目规划

### 已完成功能

- ✅ 用户注册登录
- ✅ 宠物档案管理
- ✅ JWT认证
- ✅ API文档
- ✅ Docker部署

### 待开发功能

- ⏳ 社区动态发布
- ⏳ 评论点赞功能
- ⏳ 健康记录管理
- ⏳ 商城购物功能
- ⏳ 服务预约功能
- ⏳ 消息通知
- ⏳ 紧急求助

## 技术支持

- 项目地址: https://github.com/your-repo/pet-community
- 问题反馈: https://github.com/your-repo/pet-community/issues
- 邮箱: support@petcommunity.com

## 开源协议

本项目采用 Apache 2.0 开源协议。

---

**Pet Community Team**
2025-11-14
