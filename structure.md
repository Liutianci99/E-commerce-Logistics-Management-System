# 项目结构概览（从仓库根目录）

```
E-commerce-Logistics-Management-System/
├─ backend/
│  ├─ pom.xml
│  └─ src/main/
│     ├─ java/com/logistics/
│     │  ├─ LogisticsApplication.java
│     │  ├─ common/Result.java
│     │  ├─ config/CorsConfig.java
│     │  ├─ controller/
│     │  │  ├─ AuthController.java
│     │  │  └─ TestController.java
│     │  ├─ dto/
│     │  │  ├─ LoginRequest.java
│     │  │  └─ LoginResponse.java
│     │  ├─ entity/
│     │  │  ├─ BaseEntity.java
│     │  │  └─ User.java
│     │  ├─ mapper/UserMapper.java
│     │  ├─ service/
│     │  │  ├─ UserService.java
│     │  │  └─ impl/UserServiceImpl.java
│     │  └─ user/UserRole.java
│     └─ resources/application.yml
│
├─ database/
│  └─ init.sql
│
├─ frontend/
│  ├─ index.html
│  ├─ package.json
│  ├─ package-lock.json
│  ├─ vite.config.js
│  └─ src/
│     ├─ App.vue
│     ├─ main.js
│     ├─ router/index.js
│     ├─ utils/request.js
│     └─ views/
│        ├─ Home.vue
│        └─ Login.vue
│
├─ devcontainer/
│  └─ devcontainer.json
│
├─ .github/            # CI/配置（若有）
├─ .vscode/            # 编辑器配置
├─ docker-compose.yml
├─ README.md
├─ procedure.md
├─ CODESPACES_GUIDE.md
├─ LICENSE
├─ package-lock.json
└─ structure.md
```
