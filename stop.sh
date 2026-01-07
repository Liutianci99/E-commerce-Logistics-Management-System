#!/bin/bash
# 使用: chmod +x stop.sh && ./stop.sh

PROJECT_DIR="/workspaces/E-commerce-Logistics-Management-System"

echo "🛑 停止系统..."

# 进入项目根目录
cd "$PROJECT_DIR" || { echo "❌ 项目目录不存在"; exit 1; }

# 1. 停止前端（杀掉 vite 进程）
echo "⏹️ 停止前端服务..."
pkill -f "vite" && echo "✓ 前端已停止" || echo "前端未运行"

# 2. 停止后端（杀掉 spring-boot 进程）
echo "⏹️ 停止后端服务..."
pkill -f "spring-boot:run" && echo "✓ 后端已停止" || echo "后端未运行"

# 3. 停止数据库容器
echo "⏹️ 停止数据库容器..."
docker-compose down && echo "✓ 数据库已停止" || echo "数据库未运行"

echo "✅ 系统已停止"
