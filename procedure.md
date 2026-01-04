# 后端登录实现步骤（支持商家 / 司机 / 消费者三种身份）

1. **数据库就绪**  
   - 运行 `database/init.sql`，创建库 `ecommerce_logistics`、表 `users`，并插入示例账号（username/password/role）。  
   - 确认 MySQL 已启动，执行：`mysql -u root -p < database/init.sql`

2. **角色枚举**  
   - 新增 `backend/src/main/java/com/logistics/user/UserRole.java`（值：MERCHANT、DRIVER、CONSUMER），或直接用小写常量与库表保持一致。

3. **实体类**  
   - 更新 `backend/src/main/java/com/logistics/entity/User.java`，字段与表对齐：`id`、`username`、`password`、`role`。

4. **Mapper**  
   - `backend/src/main/java/com/logistics/mapper/UserMapper.java` 继承 `BaseMapper<User>`；提供按 `username + role` 查询（QueryWrapper）。

5. **DTO**  
   - 新建 `LoginRequest`（username, password, role）和 `LoginResponse`（或直接用通用 Result 包装）。

6. **Service**  
   - 登录流程：按 username+role 查询；查无则返回“用户或角色不存在”；比对密码（演示可明文）；成功返回用户信息/role。

7. **Controller**  
   - 新建 `backend/src/main/java/com/logistics/controller/AuthController.java`：POST `/auth/login`，接收 `LoginRequest`，调用 Service，返回 Result。

8. **前端对接**  
   - `frontend/src/views/Login.vue`：POST `/api/auth/login`，body `{ username, password, role }`，成功后 `router.push('/home')`。

9. **运行与测试**  
   - 启动后端：`cd backend && mvn spring-boot:run`。  
   - 用 Postman/curl 测试：`POST http://localhost:8080/api/auth/login`，覆盖三种角色。  
   - 启动前端：`cd frontend && npm run dev`；登录后确认跳转 `/home`。
