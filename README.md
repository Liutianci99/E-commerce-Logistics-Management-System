# 电商物流管理系统 (E-commerce Logistics Management System)

一个基于 Spring Boot + Vue 3 + MySQL 的现代化电商物流管理系统。

## 技术栈

### 后端
- **Spring Boot 3.2.0** - 企业级Java应用框架
- **MyBatis Plus 3.5.5** - 增强的ORM框架
- **MySQL 8.0** - 关系型数据库
- **JWT** - 身份认证
- **Hutool** - Java工具类库
- **Maven** - 项目管理工具

### 前端
- **Vue 3** - 渐进式JavaScript框架
- **Vite 5** - 下一代前端构建工具
- **Vue Router 4** - 官方路由管理
- **Pinia** - 状态管理
- **Element Plus** - UI组件库
- **Axios** - HTTP客户端

## 项目结构

```
E-commerce-Logistics-Management-System/
├── backend/                    # 后端项目
│   ├── src/
│   │   └── main/
│   │       ├── java/com/logistics/
│   │       │   ├── LogisticsApplication.java    # 启动类
│   │       │   ├── common/                      # 公共类
│   │       │   │   └── Result.java             # 统一返回结果
│   │       │   ├── config/                      # 配置类
│   │       │   │   └── CorsConfig.java         # 跨域配置
│   │       │   ├── controller/                  # 控制器
│   │       │   │   └── TestController.java
│   │       │   ├── entity/                      # 实体类
│   │       │   │   └── BaseEntity.java
│   │       │   ├── mapper/                      # 数据访问层
│   │       │   └── service/                     # 业务逻辑层
│   │       └── resources/
│   │           └── application.yml              # 配置文件
│   └── pom.xml                                 # Maven配置
│
├── frontend/                   # 前端项目
│   ├── src/
│   │   ├── assets/            # 静态资源
│   │   ├── components/        # 公共组件
│   │   ├── router/            # 路由配置
│   │   │   └── index.js
│   │   ├── utils/             # 工具类
│   │   │   └── request.js     # Axios封装
│   │   ├── views/             # 页面组件
│   │   │   ├── Home.vue
│   │   │   └── Login.vue
│   │   ├── App.vue            # 根组件
│   │   └── main.js            # 入口文件
│   ├── index.html
│   ├── package.json
│   └── vite.config.js         # Vite配置
│
├── database/                   # 数据库脚本
│   └── init.sql               # 初始化SQL
│
├── docker-compose.yml         # Docker编排文件
└── README.md                  # 项目文档
```

## 功能模块

- ✅ 用户管理：用户注册、登录、权限管理
- ✅ 订单管理：订单创建、查询、状态更新
- ✅ 物流管理：物流信息录入、查询、追踪
- ✅ 物流轨迹：实时物流轨迹更新与查询

## 快速开始

### 环境要求

- JDK 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.6+
- Docker (可选)

### 🚀 GitHub Codespaces 快速启动（推荐）

如果你正在使用 GitHub Codespaces，请按照以下步骤操作：

#### 步骤 1️⃣：启动 MySQL 数据库

```bash
# 启动MySQL容器（会自动初始化数据库和表结构）
docker-compose up -d

# 等待10-15秒让MySQL完全启动，然后查看日志确认启动成功
docker-compose logs mysql

# 验证MySQL是否正常运行
docker-compose ps
```

> 💡 **提示**：首次启动需要等待一段时间，MySQL 会自动执行 `database/init.sql` 初始化脚本。

#### 步骤 2️⃣：启动后端服务

在新终端中执行：

```bash
cd backend

# 编译并启动Spring Boot项目
mvn spring-boot:run
```

> ⏱️ **等待时间**：首次启动需要下载依赖，大约需要 2-3 分钟。成功后会看到：
> ```
> === E-commerce Logistics Management System Started Successfully ===
> ```

后端服务将在 `http://localhost:8080` 启动

#### 步骤 3️⃣：启动前端服务

在另一个新终端中执行：

```bash
cd frontend

# 安装依赖
npm install

# 启动Vite开发服务器
npm run dev
```

> ⏱️ **等待时间**：首次安装依赖需要 1-2 分钟。

前端服务将在 `http://localhost:5173` 启动

#### 步骤 4️⃣：访问系统

Codespaces 会自动为你转发端口：

1. 点击编辑器底部的 **"端口"** 标签
2. 找到端口 `5173`（前端）和 `8080`（后端）
3. 点击 **"在浏览器中打开"** 图标访问前端系统

或者，当你运行 `npm run dev` 时，Codespaces 会弹出通知，点击 **"在浏览器中打开"** 即可。

### 🔐 默认测试账号

- 管理员：`admin` / `123456`
- 普通用户：`user1` / `123456`

### � Codespaces 存储与重用说明

### 💾 Codespaces 重启流程

**✅ 所有依赖都会保留，重新启动无需重新下载！**

```bash
# 步骤 1：启动 MySQL
docker-compose up -d

# 步骤 2：启动后端（在新终端执行）
cd backend
mvn spring-boot:run

# 步骤 3：启动前端（在新终端执行）
cd frontend
npm run dev   # ← 直接运行，依赖已缓存，无需 npm install
```

**时间对比：** 首次启动 5-7 分钟 vs 重新启动约 30 秒 ⚡

### 📝 Codespaces 常用命令

### �📝 Codespaces 常用命令

```bash
# 查看MySQL容器状态
docker-compose ps

# 查看MySQL日志
docker-compose logs -f mysql

# 重启MySQL
docker-compose restart mysql

# 停止所有服务
docker-compose down

# 停止并删除数据卷（重置数据库）
docker-compose down -v

# 查看后端日志（如果在后台运行）
cd backend && mvn spring-boot:run

# 测试后端API
curl http://localhost:8080/api/test/hello
```

### 📍 访问地址

- **前端页面**：`http://localhost:5173` （Codespaces会自动转发）
- **后端API**：`http://localhost:8080/api`
- **测试接口**：`http://localhost:8080/api/test/hello`

---

### 💻 本地环境快速启动

如果你在本地开发环境中运行：

#### 1. 数据库初始化

**方式一：使用 Docker（推荐）**

```bash
# 启动MySQL容器（会自动初始化数据库）
docker-compose up -d

# 查看日志
docker-compose logs -f
```

**方式二：手动安装**

1. 安装MySQL 8.0
2. 执行初始化脚本：
```bash
mysql -u root -p < database/init.sql
```

#### 2. 启动后端

```bash
cd backend
mvn spring-boot:run
```

#### 3. 启动前端

```bash
cd frontend
npm install
npm run dev
```

#### 4. 访问系统

直接在浏览器访问：
- 前端地址：http://localhost:5173
- 后端API：http://localhost:8080/api

## 系统架构与工作流程

### 🏗️ 整体架构

```
┌─────────────┐         ┌──────────────┐         ┌──────────────┐
│   浏览器    │  HTTP   │  Vite开发服务 │  Proxy   │ Spring Boot  │
│ localhost   │◄────────►│ :5173        │◄────────►│   :8080      │
│   :5173     │         │  (前端)      │         │   (后端)     │
└─────────────┘         └──────────────┘         └──────┬───────┘
                                                         │ JDBC
                                                         ▼
                                                    ┌──────────────┐
                                                    │   MySQL      │
                                                    │   :3306      │
                                                    │  (数据库)    │
                                                    └──────────────┘
```

### 📊 启动流程

#### 后端启动（Spring Boot）

```
LogisticsApplication.main()
    ↓
@SpringBootApplication 扫描配置
    ↓
CorsConfig - 配置跨域
    ↓
MyBatis Plus - 初始化ORM映射
    ↓
Tomcat 服务器启动 (:8080)
    ↓
TestController 等控制器加载
    ↓
"=== E-commerce Logistics Management System Started Successfully ==="
```

#### 前端启动（Vue 3）

```
npm run dev
    ↓
Vite 启动开发服务器 (:5173)
    ↓
main.js 初始化应用
    ↓
创建 Vue App 实例
    ↓
注册插件：Element Plus, Vue Router, Pinia
    ↓
App.vue 根组件挂载
    ↓
router 加载路由表
    ↓
用户看到首页
```

### 🔄 数据流程详解

#### 用户点击"测试后端连接"的完整流程

```
1. 前端发送请求
   Home.vue → testApi() → request.get('/test/hello')
   
2. Axios 拦截器处理
   - 添加 Authorization header（如果有token）
   - 设置 baseURL = '/api'
   
3. 完整 URL 处理
   前端: http://localhost:5173/api/test/hello
   ↓ (Vite 代理识别 '/api' 前缀)
   后端: http://localhost:8080/api/test/hello
   
4. 后端处理请求
   DispatcherServlet 接收
   ↓
   CorsConfig 过滤器（处理跨域）
   ↓
   路由匹配 @GetMapping("/test/hello")
   ↓
   TestController.hello() 执行
   ↓
   创建 Result 对象并序列化为 JSON
   
5. 前端接收响应
   响应拦截器检查 code 是否等于 200
   ↓
   提取 data 字段
   ↓
   更新 apiResponse 状态
   ↓
   Vue 模板重新渲染显示结果
```

#### 后端代码执行流程

```
HTTP 请求到达
    ↓
TestController.hello() 执行
    ├─ 创建 HashMap 存储数据
    ├─ put("message", "Hello from...")
    ├─ put("version", "1.0.0")
    └─ 调用 Result.success(data)
    ↓
Result.success(T data) 静态工厂方法
    ├─ new Result<T>()
    ├─ setCode(200)
    ├─ setMessage("操作成功")
    ├─ setData(data)
    └─ return result
    ↓
@RestController 自动转为 JSON
    ├─ code: 200
    ├─ message: "操作成功"
    └─ data: {message: "...", version: "1.0.0"}
    ↓
HTTP 200 返回到前端
```

### 🗄️ 数据库操作流程

以查询订单为例的分层架构：

```
Controller 层（接收请求）
    ↓ 业务逻辑委托
Service 层（处理业务）
    ↓ 数据查询委托
Mapper 层（数据访问）
    ↓ SQL 生成和执行
MyBatis Plus ORM
    ↓ JDBC 驱动
MySQL 数据库
    ↓ 返回结果
映射为 Java 对象
    ↓ JSON 序列化
返回给前端
```

**具体代码流程：**

```java
// ① 前端发送请求
GET /api/orders?status=PENDING

// ② Controller 层接收并委托 Service
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public Result<List<Orders>> getOrders(String status) {
        List<Orders> orders = orderService.findByStatus(status);
        return Result.success(orders);
    }
}

// ③ Service 层处理业务逻辑并委托 Mapper
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    
    public List<Orders> findByStatus(String status) {
        return orderMapper.selectByStatus(status);
    }
}

// ④ Mapper 层执行数据库查询
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    List<Orders> selectByStatus(String status);
}

// ⑤ 执行流程
orderMapper.selectByStatus(status)
  → MyBatis Plus 构建 SQL：SELECT * FROM orders WHERE status = ? AND deleted = 0
  → JDBC 执行 SQL
  → MySQL 返回结果集
  → 映射为 Orders 对象列表
  → 返回给 Service
  → 包装成 Result<List<Orders>>
  → 序列化为 JSON
  → HTTP 200 返回前端
```

### ⚙️ 关键配置说明

#### 前端 Vite 代理配置

```javascript
// vite.config.js
server: {
  port: 5173,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',  // 后端地址
      changeOrigin: true                 // 改变请求源
    }
  }
}
```

**作用：** 前端 `/api` 请求自动转发到后端

#### 后端 Spring Boot 配置

```yaml
# application.yml
server:
  port: 8080
  servlet:
    context-path: /api              # 所有 API 前缀

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/logistics_db
    username: root
    password: root123
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss # JSON 日期格式

mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  type-aliases-package: com.logistics.entity
  global-config:
    db-config:
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
```

#### 统一返回结果类设计

```java
// Result.java - 所有 API 统一返回格式
public class Result<T> implements Serializable {
    private Integer code;        // 状态码
    private String message;      // 消息
    private T data;              // 实际数据
    
    // 静态工厂方法确保响应格式一致
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }
}
```

### 📝 完整执行顺序表

| 阶段 | 步骤 | 命令/代码 | 结果 |
|------|------|---------|------|
| 初始化 | 1 | `docker-compose up -d` | MySQL :3306 启动 |
| 初始化 | 2 | `mvn spring-boot:run` | Spring Boot :8080 启动 |
| 初始化 | 3 | `npm run dev` | Vite :5173 启动 |
| 运行时 | 4 | 访问 localhost:5173 | 加载 Vue 应用 |
| 运行时 | 5 | 点击"测试后端连接" | 前端发送 HTTP 请求 |
| 网络传输 | 6 | Vite 代理转发 | 请求到 localhost:8080 |
| 后端处理 | 7 | Controller 处理 | TestController.hello() 执行 |
| 响应返回 | 8 | 返回 JSON | 前端接收并更新 UI |

---

## API 文档

### 测试接口

```
GET /api/test/hello
```

响应示例：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "message": "Hello from E-commerce Logistics Management System!",
    "version": "1.0.0"
  }
}
```

## 开发计划

- [ ] 完善用户认证与授权
- [ ] 实现订单CRUD功能
- [ ] 实现物流信息管理
- [ ] 添加物流轨迹查询
- [ ] 数据统计与报表
- [ ] 系统监控与日志

## 项目配置

### 后端配置 (application.yml)

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/logistics_db
    username: root
    password: root123
```

### 前端配置 (vite.config.js)

```javascript
server: {
  port: 5173,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

## 常见问题

### 后端无法连接数据库
- 检查MySQL是否启动
- 确认数据库配置信息正确
- 检查防火墙设置

### 前端无法访问后端API
- 确认后端服务已启动
- 检查代理配置是否正确
- 查看浏览器控制台错误信息

## 贡献指南

欢迎提交 Issue 和 Pull Request！

## 许可证

MIT License
I write an E-commerce Logistics Management System as my graduation project.
