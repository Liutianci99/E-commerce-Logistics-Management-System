-- 创建数据库
CREATE DATABASE IF NOT EXISTS logistics_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE logistics_db;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `email` VARCHAR(100) COMMENT '邮箱',
  `phone` VARCHAR(20) COMMENT '手机号',
  `role` VARCHAR(20) DEFAULT 'USER' COMMENT '角色：ADMIN, USER',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  INDEX idx_username (`username`),
  INDEX idx_phone (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 订单表
CREATE TABLE IF NOT EXISTS `orders` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
  `order_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '订单编号',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
  `status` VARCHAR(20) DEFAULT 'PENDING' COMMENT '订单状态：PENDING, PAID, SHIPPED, DELIVERED, CANCELLED',
  `receiver_name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
  `receiver_phone` VARCHAR(20) NOT NULL COMMENT '收货人电话',
  `receiver_address` VARCHAR(200) NOT NULL COMMENT '收货地址',
  `remark` TEXT COMMENT '备注',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  INDEX idx_order_no (`order_no`),
  INDEX idx_user_id (`user_id`),
  INDEX idx_status (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 物流信息表
CREATE TABLE IF NOT EXISTS `logistics` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '物流ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `tracking_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '物流单号',
  `company` VARCHAR(50) NOT NULL COMMENT '物流公司',
  `status` VARCHAR(20) DEFAULT 'IN_TRANSIT' COMMENT '物流状态：IN_TRANSIT, DELIVERED, RETURNED',
  `current_location` VARCHAR(200) COMMENT '当前位置',
  `estimated_delivery` DATE COMMENT '预计送达日期',
  `actual_delivery` DATETIME COMMENT '实际送达时间',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  INDEX idx_order_id (`order_id`),
  INDEX idx_tracking_no (`tracking_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流信息表';

-- 物流轨迹表
CREATE TABLE IF NOT EXISTS `logistics_track` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '轨迹ID',
  `logistics_id` BIGINT NOT NULL COMMENT '物流ID',
  `location` VARCHAR(200) NOT NULL COMMENT '位置',
  `description` VARCHAR(500) NOT NULL COMMENT '描述',
  `track_time` DATETIME NOT NULL COMMENT '轨迹时间',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX idx_logistics_id (`logistics_id`),
  INDEX idx_track_time (`track_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流轨迹表';

-- 插入测试数据
INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `phone`, `role`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '管理员', 'admin@example.com', '13800138000', 'ADMIN'),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '普通用户', 'user1@example.com', '13800138001', 'USER');
-- 默认密码：123456
