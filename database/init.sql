-- 创建数据库
CREATE DATABASE IF NOT EXISTS `ecommerce_logistics` DEFAULT CHARSET utf8mb4;

-- 创建user表
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `role` ENUM('merchant','driver','consumer','admin') NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入初始数据（不写 id，避免主键冲突）
INSERT INTO `users` (`username`,`password`,`role`) VALUES
('刘天赐','123','merchant'),
('刘天赐','123','driver'),
('刘天赐','123','consumer'),
('刘天赐','123','admin');

-- 创建库存表
CREATE TABLE IF NOT EXISTS `inventory` (
  `product_id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_name` VARCHAR(100) NOT NULL,
  `quantity` INT NOT NULL DEFAULT 0,
  `stock_in_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `image_url` VARCHAR(255),
  `is_published` TINYINT(1) NOT NULL DEFAULT 0,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建商城表
CREATE TABLE IF NOT EXISTS `mall` (
  `product_id` INT PRIMARY KEY,
  `product_name` VARCHAR(100) NOT NULL,
  `description` TEXT,
  `available_quantity` INT NOT NULL DEFAULT 0,
  `price` DECIMAL(10, 2) NOT NULL,
  `is_published` TINYINT(1) NOT NULL DEFAULT 1,
  `publish_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `image_url` VARCHAR(255),
  FOREIGN KEY (`product_id`) REFERENCES `inventory`(`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建订单表
CREATE TABLE `orders` (
  `order_id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
  `product_id` INT NOT NULL COMMENT '商品ID',
  `customer_id` INT NOT NULL COMMENT '顾客ID',
  `merchant_id` INT NOT NULL COMMENT '商户ID',
  `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `quantity` INT NOT NULL COMMENT '商品数量',
  `unit_price` DECIMAL(10, 2) NOT NULL COMMENT '商品单价',
  `total_amount` DECIMAL(10, 2) NOT NULL COMMENT '总金额',
  `image_url` VARCHAR(255) COMMENT '商品图片URL',
  `status` INT NOT NULL DEFAULT 0 COMMENT '订单状态：-1=取消订单，0=未发货，1=已发货，2=已揽收，3=运输中，4=已到达，5=已收货，6=待评价，7=已评价',
  `order_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `ship_time` DATETIME COMMENT '发货时间',
  `pickup_time` DATETIME COMMENT '揽收时间',
  `delivery_time` DATETIME COMMENT '送达时间',
  `receive_time` DATETIME COMMENT '收货时间',
  `review_time` DATETIME COMMENT '评价时间',
  FOREIGN KEY (`product_id`) REFERENCES `mall`(`product_id`),
  FOREIGN KEY (`customer_id`) REFERENCES `users`(`id`),
  FOREIGN KEY (`merchant_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';