#!/bin/bash
# 使用: chmod +x start.sh && ./start.sh

PROJECT_DIR="/workspaces/E-commerce-Logistics-Management-System"

echo "🚀 启动系统..."

# 进入项目根目录
cd "$PROJECT_DIR" || { echo "❌ 项目目录不存在"; exit 1; }

# 1. 启动后端（后台）
cd "$PROJECT_DIR/backend" && mvn spring-boot:run > ../backend.log 2>&1 &
echo "⚙️ 后端已在后台启动（日志：backend.log）"

# 2. 启动前端（前台）
cd "$PROJECT_DIR/frontend" && npm run dev