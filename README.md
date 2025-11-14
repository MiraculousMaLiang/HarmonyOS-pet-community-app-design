# 基于HarmonyOS的宠物社区应用系统

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![HarmonyOS](https://img.shields.io/badge/HarmonyOS-4.0-red.svg)](https://www.harmonyos.com/)

一款基于HarmonyOS系统的宠物社区应用，提供宠物档案管理、社区互动、健康管理、商城购物等功能的综合性平台。

## 📖 项目简介

随着宠物经济的快速发展和数字化生活方式的普及，宠物主对于智能化、便捷化的宠物管理和社交需求日益增长。本项目旨在设计与实现一款基于HarmonyOS的宠物社区应用，构建集宠物信息管理、交流互动、服务推荐与健康管理于一体的智能化平台。

### 核心功能

- 🐕 **宠物档案管理** - 完善的宠物信息管理，包括基本档案、健康记录、疫苗管理等
- 💬 **社区互动** - 发布动态、评论点赞、话题讨论，打造活跃的宠物社交平台
- 🏥 **健康管理** - 健康数据记录、趋势分析、智能提醒，科学管理宠物健康
- 🛒 **商城购物** - 宠物用品商城，在线购买食品、玩具、用品等
- 📅 **服务预约** - 预约宠物洗护、医疗、美容等服务
- 🔔 **消息通知** - 系统通知、互动消息、健康提醒等
- 🆘 **紧急求助** - 宠物走失、紧急情况快速求助功能

### 技术特色

- ✨ 基于HarmonyOS分布式架构，实现多设备数据同步
- 🔐 完善的安全机制，JWT认证 + Spring Security
- 📊 数据可视化与智能分析，提供科学的健康管理建议
- 🚀 前后端分离设计，保证系统的可扩展性和可维护性
- 🐳 Docker容器化部署，支持一键启动

## 🏗️ 系统架构

### 总体架构

```
┌─────────────────────────────────────────┐
│          HarmonyOS客户端(ArkTS)          │
│  ┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐  │
│  │用户  │ │宠物  │ │社区  │ │健康  │  │
│  │管理  │ │档案  │ │互动  │ │管理  │  │
│  └──────┘ └──────┘ └──────┘ └──────┘  │
└─────────────────────────────────────────┘
                    │ HTTPS/RESTful API
┌─────────────────────────────────────────┐
│         Spring Boot后端服务层            │
│  ┌────────────────────────────────────┐ │
│  │   Controller层(接口控制)            │ │
│  ├────────────────────────────────────┤ │
│  │   Service层(业务逻辑处理)           │ │
│  ├────────────────────────────────────┤ │
│  │   MyBatis Plus(数据访问)           │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
                    │
┌─────────────────────────────────────────┐
│            MySQL 8.0数据库               │
│  ┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐  │
│  │用户表│ │宠物表│ │动态表│ │订单表│  │
│  └──────┘ └──────┘ └──────┘ └──────┘  │
└─────────────────────────────────────────┘
```

### 技术栈

#### 后端技术
- **开发语言**: Java 17
- **开发框架**: Spring Boot 3.1.5
- **ORM框架**: MyBatis Plus 3.5.3
- **数据库**: MySQL 8.0
- **缓存**: Redis 7.0
- **安全框架**: Spring Security + JWT
- **API文档**: Knife4j 4.1.0

#### 前端技术
- **开发语言**: ArkTS
- **开发框架**: HarmonyOS Stage模型
- **系统版本**: HarmonyOS 4.0+
- **UI组件**: ArkUI声明式开发范式

## 📁 项目结构

```
HarmonyOS-pet-community-app-design/
├── pet-community-backend/          # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/petcommunity/
│   │   │   │   ├── config/        # 配置类
│   │   │   │   ├── common/        # 公共模块
│   │   │   │   ├── controller/    # 控制器
│   │   │   │   ├── service/       # 服务层
│   │   │   │   ├── mapper/        # 数据访问层
│   │   │   │   ├── entity/        # 实体类
│   │   │   │   ├── dto/           # 数据传输对象
│   │   │   │   └── utils/         # 工具类
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── mapper/
│   │   └── test/
│   ├── db/                         # 数据库脚本
│   │   └── init.sql
│   ├── docker-compose.yml          # Docker编排
│   ├── Dockerfile                  # Docker镜像
│   ├── pom.xml                     # Maven配置
│   └── README.md                   # 后端说明文档
└── README.md                       # 项目总体说明
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+ (可选)
- Docker & Docker Compose (可选)

### 本地开发

#### 1. 克隆项目

```bash
git clone <repository-url>
cd HarmonyOS-pet-community-app-design/pet-community-backend
```

#### 2. 初始化数据库

```bash
# 登录MySQL
mysql -u root -p

# 执行初始化脚本
source db/init.sql
```

#### 3. 修改配置

编辑 `src/main/resources/application-dev.yml`，修改数据库连接信息。

#### 4. 启动项目

```bash
# 方式1: 使用Maven
mvn spring-boot:run

# 方式2: 使用启动脚本
./start.sh
```

#### 5. 访问系统

- API文档: http://localhost:8080/doc.html
- 后端服务: http://localhost:8080/api

### Docker部署

```bash
cd pet-community-backend

# 一键启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f backend
```

## 📊 数据库设计

系统包含17张核心数据表，涵盖用户、宠物、社区、健康、商城等模块：

- **用户模块**: user, user_follow
- **宠物模块**: pet, health_record, vaccine_record
- **社区模块**: post, comment, like_record, favorite
- **商城模块**: product_category, product, order_table, order_item, shopping_cart
- **服务模块**: appointment
- **消息模块**: notification, help_request

详细的数据库设计请查看 [db/init.sql](pet-community-backend/db/init.sql)

## 🔌 API接口

### 用户管理

```http
POST   /api/user/register      # 用户注册
POST   /api/user/login         # 用户登录
GET    /api/user/info          # 获取用户信息
PUT    /api/user/info          # 更新用户信息
PUT    /api/user/password      # 修改密码
POST   /api/user/logout        # 退出登录
```

### 宠物档案

```http
POST   /api/pet                # 添加宠物
GET    /api/pet/list           # 获取宠物列表
GET    /api/pet/{petId}        # 获取宠物详情
PUT    /api/pet/{petId}        # 更新宠物信息
DELETE /api/pet/{petId}        # 删除宠物
```

更多接口请访问 Knife4j 文档: http://localhost:8080/doc.html

## 🧪 测试账号

系统初始化时会创建以下测试账号：

- **管理员**
  - 用户名: `admin`
  - 密码: `123456`

- **普通用户**
  - 用户名: `testuser`
  - 密码: `123456`

## 📈 开发计划

### 已完成功能 ✅

- [x] 用户注册登录
- [x] 宠物档案管理
- [x] JWT认证机制
- [x] API文档生成
- [x] Docker部署支持
- [x] 数据库设计

### 进行中 🚧

- [ ] 社区动态发布
- [ ] 评论点赞功能
- [ ] 健康记录管理

### 待开发 📝

- [ ] 商城购物功能
- [ ] 服务预约功能
- [ ] 消息推送通知
- [ ] 紧急求助功能
- [ ] HarmonyOS客户端开发
- [ ] AI智能识别
- [ ] 数据分析报表

## 🤝 贡献指南

欢迎贡献代码、提出问题和建议！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📄 开源协议

本项目采用 [Apache 2.0](LICENSE) 开源协议。

## 👥 开发团队

**Pet Community Team**

- 项目负责人: [@YourName]
- 后端开发: [@YourName]
- 前端开发: [@YourName]
- UI设计: [@YourName]

## 📧 联系我们

- 项目地址: https://github.com/your-repo/HarmonyOS-pet-community
- 问题反馈: https://github.com/your-repo/HarmonyOS-pet-community/issues
- 邮箱: support@petcommunity.com

## 🙏 致谢

感谢所有为本项目做出贡献的开发者！

---

**Copyright © 2025 Pet Community Team. All rights reserved.**
