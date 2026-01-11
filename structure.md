# 项目结构概览（从仓库根目录）

```
E-commerce-Logistics-Management-System/
├─ .devcontainer/
│  └─ devcontainer.json          # 开发容器配置
│
├─ .vscode/                      # VSCode 编辑器配置
│
├─ backend/                      # 后端 Spring Boot 项目
│  ├─ pom.xml                    # Maven 依赖配置
│  ├─ src/main/
│  │  ├─ java/com/logistics/
│  │  │  ├─ LogisticsApplication.java    # Spring Boot 启动类
│  │  │  ├─ common/
│  │  │  │  └─ Result.java               # 统一返回结果封装
│  │  │  ├─ config/
│  │  │  │  └─ CorsConfig.java           # 跨域配置
│  │  │  ├─ controller/
│  │  │  │  └─ AuthController.java       # 认证控制器（登录接口）
│  │  │  ├─ dto/
│  │  │  │  ├─ LoginRequest.java         # 登录请求 DTO
│  │  │  │  └─ LoginResponse.java        # 登录响应 DTO（含 UserInfo）
│  │  │  ├─ entity/
│  │  │  │  ├─ BaseEntity.java           # 实体基类
│  │  │  │  └─ User.java                 # 用户实体
│  │  │  ├─ mapper/
│  │  │  │  └─ UserMapper.java           # MyBatis-Plus Mapper
│  │  │  ├─ service/
│  │  │  │  ├─ UserService.java          # 用户服务接口
│  │  │  │  └─ impl/
│  │  │  │     └─ UserServiceImpl.java   # 用户服务实现
│  │  │  └─ user/
│  │  │     └─ UserRole.java             # 用户角色枚举
│  │  └─ resources/
│  │     └─ application.yml              # Spring Boot 配置文件
│  └─ target/                            # 编译输出目录
│
├─ backend.log                   # 后端日志文件
│
├─ database/
│  └─ init.sql                   # 数据库初始化脚本（users 表及初始数据）
│
├─ frontend/                     # 前端 Vue3 + Vite 项目
│  ├─ index.html                 # HTML 入口文件
│  ├─ package.json               # npm 依赖配置（含 lucide-vue-next）
│  ├─ package-lock.json          # npm 依赖锁定文件
│  ├─ vite.config.js             # Vite 构建配置（含代理配置）
│  └─ src/
│     ├─ App.vue                 # 根组件
│     ├─ main.js                 # Vue 应用入口
│     ├─ router/
│     │  └─ index.js             # Vue Router 路由配置（含所有子路由）
│     ├─ utils/
│     │  └─ request.js           # Axios 请求封装
│     └─ views/                  # 页面组件
│        ├─ Login.vue            # 登录页面（角色选择、用户认证）
│        ├─ demo.vue             # 主布局页面（可折叠侧边栏、面包屑、路由视图）
│        ├─ Home.vue             # 首页（备用）
│        ├─ admin/               # 管理员页面目录
│        │  ├─ UserManagement.vue        # 用户管理
│        │  ├─ OrderManagement.vue       # 订单管理
│        │  └─ DataAnalysis.vue          # 数据分析
│        ├─ merchant/            # 商户页面目录
│        │  ├─ InventoryManagement.vue   # 库存管理（含状态筛选）
│        │  ├─ ProductListing.vue        # 商品上架（未上架商品）
│        │  ├─ ProductDelisting.vue      # 商品下架（已上架商品）
│        │  ├─ StockIn.vue               # 商品入库（含图片上传）
│        │  ├─ OrderManagement.vue       # 订单管理
│        │  └─ LogisticsQuery.vue        # 物流查询
│        ├─ general/             # 通用页面目录
│        │  └─ Mall.vue                  # 商城（含商品下架入口）
│        ├─ consumer/            # 顾客页面目录
│        │  ├─ MyOrders.vue              # 我的订单
│        │  └─ LogisticsQuery.vue        # 物流查询
│        └─ delivery/            # 配送员页面目录
│           ├─ PendingPickup.vue         # 待揽收
│           ├─ PendingDelivery.vue       # 待送货
│           └─ HistoryTasks.vue          # 历史任务
│
├─ start.sh                      # 启动脚本（后端 + 前端）
├─ stop.sh                       # 停止脚本
├─ README.md                     # 项目说明文档
├─ LICENSE                       # 开源许可证
├─ package-lock.json             # 根目录 npm 锁定文件
└─ structure.md                  # 本文件 - 项目结构说明
```
