#!/bin/bash
# 使用: chmod +x start.sh && ./start.sh

PROJECT_DIR="/workspaces/E-commerce-Logistics-Management-System"

echo "🚀 启动系统..."

# 进入项目根目录（确保 docker-compose.yml 能被找到）
cd "$PROJECT_DIR" || { echo "❌ 项目目录不存在"; exit 1; }

# 1. 启动数据库
docker-compose up -d
echo "📦 数据库容器已启动："
docker ps --filter "name=.*logistics.*" --format "table {{.Names}}\t{{.Status}}"

# 2. 启动后端（后台）
cd "$PROJECT_DIR/backend" && mvn spring-boot:run > ../backend.log 2>&1 &
echo "⚙️ 后端已在后台启动（日志：backend.log）"

# 3. 启动前端（前台）
cd "$PROJECT_DIR/frontend" && npm run dev