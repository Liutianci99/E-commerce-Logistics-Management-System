-- 创建数据库
CREATE DATABASE IF NOT EXISTS `ecommerce_logistics` DEFAULT CHARSET utf8mb4;

-- 创建表
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `role` ENUM('merchant','driver','consumer','admin') NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 清空表数据
TRUNCATE TABLE `users`;

-- 插入初始数据（不写 id，避免主键冲突）
INSERT INTO `users` (`username`,`password`,`role`) VALUES
('刘天赐','123','merchant'),
('刘天赐','123','driver'),
('刘天赐','123','consumer'),
('刘天赐','123','admin');