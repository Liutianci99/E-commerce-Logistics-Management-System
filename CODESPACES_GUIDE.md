# 🚀 Codespaces 启动指南

欢迎使用电商物流管理系统！本指南将帮助你在 GitHub Codespaces 中快速启动项目。

## 📋 前置说明

Codespaces 已经为你准备好了开发环境：
- ✅ JDK 17
- ✅ Node.js 18+
- ✅ Maven
- ✅ Docker

你只需要按照以下步骤启动服务即可。

---

## 🎯 完整启动流程

### 第一步：启动 MySQL 数据库

打开终端（Terminal），执行以下命令：

```bash
docker-compose up -d
```

**等待 10-15 秒**，让 MySQL 完全启动并初始化数据库。

验证 MySQL 是否启动成功：

```bash
docker-compose ps
```

你应该看到类似的输出：
```
NAME                IMAGE       STATUS          PORTS
logistics-mysql     mysql:8.0   Up 10 seconds   0.0.0.0:3306->3306/tcp
```

查看初始化日志（可选）：

```bash
docker-compose logs mysql | tail -20
```

看到 `ready for connections` 表示数据库已经准备就绪。

---

### 第二步：启动后端服务

**打开新的终端**（点击终端旁边的 `+` 号，或使用快捷键 `Ctrl + Shift + ~`）

```bash
cd backend
mvn spring-boot:run
```

**⏱️ 首次启动约需 2-3 分钟**（Maven 需要下载依赖）

启动成功后，你会看到：
```
=== E-commerce Logistics Management System Started Successfully ===
```

> 💡 **提示**：保持这个终端打开，让后端服务持续运行。

---

### 第三步：启动前端服务

**再打开一个新终端**

```bash
cd frontend
npm install
npm run dev
```

**⏱️ 首次安装约需 1-2 分钟**

启动成功后，你会看到：
```
  VITE v5.x.x  ready in xxx ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
```

此时 Codespaces 会自动弹出通知：

![Port Forward Notification](https://docs.github.com/assets/images/help/codespaces/port-forwarding-notification.png)

点击 **"在浏览器中打开"** 或 **"Open in Browser"** 即可访问系统！

---

### 第四步：访问系统

#### 方式 1：通过端口转发通知（推荐）

当前端启动后，Codespaces 会自动弹出通知，直接点击即可。

#### 方式 2：通过端口面板

1. 点击编辑器底部的 **"端口"（PORTS）** 标签
2. 找到端口 `5173`
3. 点击右侧的 **地球图标** 🌐 在浏览器中打开

#### 方式 3：通过命令面板

按 `F1` 或 `Ctrl+Shift+P`，输入 `Ports: Focus on Ports View`

---

## 🔐 登录测试

打开前端页面后，可以使用以下账号登录：

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | `admin` | `123456` |
| 普通用户 | `user1` | `123456` |

---

## 🧪 测试后端连接

在前端首页，点击 **"测试后端连接"** 按钮，如果看到成功提示，说明前后端已经联通！

或者在终端中直接测试：

```bash
curl http://localhost:8080/api/test/hello
```

成功响应示例：
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

---

## 📍 服务地址总览

| 服务 | 地址 | 说明 |
|------|------|------|
| 前端 | `http://localhost:5173` | Vue 3 + Vite |
| 后端 | `http://localhost:8080/api` | Spring Boot |
| 数据库 | `localhost:3306` | MySQL 8.0 |
| 测试接口 | `http://localhost:8080/api/test/hello` | 健康检查 |

---

## 🛠️ 常见问题

### ❓ MySQL 启动失败

```bash
# 查看详细日志
docker-compose logs mysql

# 重新启动
docker-compose restart mysql
```

### ❓ 后端启动报错：无法连接数据库

确保 MySQL 已经完全启动（等待 15-20 秒），然后重新启动后端：

```bash
cd backend
mvn spring-boot:run
```

### ❓ 前端无法访问后端

1. 确认后端服务正在运行（终端中有 Spring Boot 日志）
2. 检查端口 `8080` 是否在 **"端口"** 面板中
3. 查看浏览器控制台的错误信息

### ❓ 端口被占用

```bash
# 查看端口占用
lsof -i :8080
lsof -i :5173

# 停止占用的进程
kill -9 <PID>
```

### ❓ 重置数据库

```bash
# 停止并删除数据卷
docker-compose down -v

# 重新启动
docker-compose up -d
```

---

## 🔧 实用命令速查

### Docker 相关

```bash
# 查看容器状态
docker-compose ps

# 查看实时日志
docker-compose logs -f mysql

# 重启MySQL
docker-compose restart mysql

# 停止所有服务
docker-compose down

# 完全清理（删除数据卷）
docker-compose down -v

# 进入MySQL容器
docker exec -it logistics-mysql mysql -uroot -proot123
```

### 后端相关

```bash
# 编译项目
cd backend
mvn clean install

# 跳过测试编译
mvn clean install -DskipTests

# 运行项目
mvn spring-boot:run

# 清理并重新编译
mvn clean compile
```

### 前端相关

```bash
# 安装依赖
cd frontend
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build

# 预览生产版本
npm run preview

# 清理 node_modules
rm -rf node_modules
npm install
```

---

## 📊 项目架构

```
┌─────────────────┐
│   浏览器访问     │
│  localhost:5173  │
└────────┬────────┘
         │
    ┌────▼────┐
    │  Vue 3  │  前端
    │  Vite   │  localhost:5173
    └────┬────┘
         │ Proxy
         │ /api → localhost:8080
    ┌────▼────────┐
    │ Spring Boot │  后端
    │   8080      │  
    └────┬────────┘
         │ JDBC
    ┌────▼────┐
    │  MySQL  │  数据库
    │  3306   │  (Docker)
    └─────────┘
```

---

## 🎓 下一步

项目启动成功后，你可以：

1. 📖 查看 [README.md](README.md) 了解项目详情
2. 🔍 浏览 `database/init.sql` 了解数据库结构
3. 💻 开始开发新功能
4. 📝 查看 API 文档（即将推出）

---

## 💬 需要帮助？

- 查看项目 [README.md](README.md)
- 检查终端日志输出
- 查看浏览器控制台错误信息

---

**祝开发愉快！ Happy Coding! 🎉**
