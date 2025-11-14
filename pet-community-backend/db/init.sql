-- ====================================
-- 宠物社区应用数据库初始化脚本
-- 数据库版本: MySQL 8.0
-- 创建时间: 2025-11-14
-- ====================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS pet_community DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE pet_community;

-- ====================================
-- 1. 用户表 (user)
-- ====================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(加密)',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `gender` TINYINT DEFAULT 0 COMMENT '性别(0未知 1男 2女)',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `bio` VARCHAR(500) DEFAULT NULL COMMENT '个人简介',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0禁用 1正常)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_phone` (`phone`),
    KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ====================================
-- 2. 宠物档案表 (pet)
-- ====================================
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet` (
    `pet_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '宠物ID',
    `user_id` BIGINT NOT NULL COMMENT '所属用户ID',
    `pet_name` VARCHAR(50) NOT NULL COMMENT '宠物名称',
    `species` VARCHAR(20) NOT NULL COMMENT '物种(猫/狗等)',
    `breed` VARCHAR(50) DEFAULT NULL COMMENT '品种',
    `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
    `gender` TINYINT DEFAULT 0 COMMENT '性别(0未知 1公 2母)',
    `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重(kg)',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '宠物照片',
    `color` VARCHAR(50) DEFAULT NULL COMMENT '毛色',
    `chip_no` VARCHAR(50) DEFAULT NULL COMMENT '芯片号',
    `description` TEXT DEFAULT NULL COMMENT '描述信息',
    `is_sterilized` TINYINT DEFAULT 0 COMMENT '是否绝育(0否 1是)',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0删除 1正常)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`pet_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_species` (`species`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宠物档案表';

-- ====================================
-- 3. 社区动态表 (post)
-- ====================================
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
    `post_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '动态ID',
    `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
    `pet_id` BIGINT DEFAULT NULL COMMENT '关联宠物ID',
    `title` VARCHAR(100) DEFAULT NULL COMMENT '标题',
    `content` TEXT NOT NULL COMMENT '内容',
    `images` TEXT DEFAULT NULL COMMENT '图片URLs(JSON)',
    `video` VARCHAR(255) DEFAULT NULL COMMENT '视频URL',
    `topic` VARCHAR(50) DEFAULT NULL COMMENT '话题标签',
    `location` VARCHAR(100) DEFAULT NULL COMMENT '位置信息',
    `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
    `comment_count` INT NOT NULL DEFAULT 0 COMMENT '评论数',
    `share_count` INT NOT NULL DEFAULT 0 COMMENT '分享数',
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览量',
    `is_top` TINYINT NOT NULL DEFAULT 0 COMMENT '是否置顶(0否 1是)',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0删除 1正常 2审核中)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`post_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_pet_id` (`pet_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_status` (`status`),
    KEY `idx_topic` (`topic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社区动态表';

-- ====================================
-- 4. 评论表 (comment)
-- ====================================
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
    `comment_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `post_id` BIGINT NOT NULL COMMENT '动态ID',
    `user_id` BIGINT NOT NULL COMMENT '评论用户ID',
    `parent_id` BIGINT DEFAULT NULL COMMENT '父评论ID(回复)',
    `reply_to_user_id` BIGINT DEFAULT NULL COMMENT '回复目标用户ID',
    `content` TEXT NOT NULL COMMENT '评论内容',
    `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0删除 1正常)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
    PRIMARY KEY (`comment_id`),
    KEY `idx_post_id` (`post_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ====================================
-- 5. 点赞表 (like_record)
-- ====================================
DROP TABLE IF EXISTS `like_record`;
CREATE TABLE `like_record` (
    `like_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `target_id` BIGINT NOT NULL COMMENT '目标ID(动态/评论)',
    `target_type` TINYINT NOT NULL COMMENT '类型(1动态 2评论)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
    PRIMARY KEY (`like_id`),
    UNIQUE KEY `uk_user_target` (`user_id`, `target_id`, `target_type`),
    KEY `idx_target` (`target_id`, `target_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点赞表';

-- ====================================
-- 6. 健康记录表 (health_record)
-- ====================================
DROP TABLE IF EXISTS `health_record`;
CREATE TABLE `health_record` (
    `record_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `pet_id` BIGINT NOT NULL COMMENT '宠物ID',
    `record_date` DATE NOT NULL COMMENT '记录日期',
    `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重(kg)',
    `temperature` DECIMAL(4,1) DEFAULT NULL COMMENT '体温(℃)',
    `diet_info` VARCHAR(200) DEFAULT NULL COMMENT '饮食情况',
    `exercise_duration` INT DEFAULT NULL COMMENT '运动时长(分钟)',
    `exercise_type` VARCHAR(50) DEFAULT NULL COMMENT '运动类型',
    `mental_state` TINYINT DEFAULT NULL COMMENT '精神状态(1差 2一般 3好)',
    `appetite` TINYINT DEFAULT NULL COMMENT '食欲(1差 2一般 3好)',
    `excretion` VARCHAR(100) DEFAULT NULL COMMENT '排泄情况',
    `note` TEXT DEFAULT NULL COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`record_id`),
    KEY `idx_pet_id` (`pet_id`),
    KEY `idx_record_date` (`record_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康记录表';

-- ====================================
-- 7. 疫苗记录表 (vaccine_record)
-- ====================================
DROP TABLE IF EXISTS `vaccine_record`;
CREATE TABLE `vaccine_record` (
    `vaccine_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '疫苗ID',
    `pet_id` BIGINT NOT NULL COMMENT '宠物ID',
    `vaccine_name` VARCHAR(100) NOT NULL COMMENT '疫苗名称',
    `vaccine_type` VARCHAR(50) DEFAULT NULL COMMENT '疫苗类型',
    `vaccine_date` DATE NOT NULL COMMENT '接种日期',
    `next_date` DATE DEFAULT NULL COMMENT '下次接种日期',
    `hospital` VARCHAR(100) DEFAULT NULL COMMENT '接种医院',
    `doctor` VARCHAR(50) DEFAULT NULL COMMENT '接种医生',
    `batch_no` VARCHAR(50) DEFAULT NULL COMMENT '批次号',
    `note` TEXT DEFAULT NULL COMMENT '备注',
    `is_reminded` TINYINT NOT NULL DEFAULT 0 COMMENT '是否已提醒(0否 1是)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`vaccine_id`),
    KEY `idx_pet_id` (`pet_id`),
    KEY `idx_vaccine_date` (`vaccine_date`),
    KEY `idx_next_date` (`next_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='疫苗记录表';

-- ====================================
-- 8. 商品分类表 (product_category)
-- ====================================
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
    `category_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `icon` VARCHAR(255) DEFAULT NULL COMMENT '图标',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0禁用 1启用)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`category_id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- ====================================
-- 9. 商品表 (product)
-- ====================================
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    `product_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
    `description` TEXT DEFAULT NULL COMMENT '商品描述',
    `images` TEXT DEFAULT NULL COMMENT '商品图片(JSON)',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `stock` INT NOT NULL DEFAULT 0 COMMENT '库存',
    `sales` INT NOT NULL DEFAULT 0 COMMENT '销量',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0下架 1上架)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`product_id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- ====================================
-- 10. 订单表 (order_table)
-- ====================================
DROP TABLE IF EXISTS `order_table`;
CREATE TABLE `order_table` (
    `order_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_type` TINYINT NOT NULL COMMENT '类型(1商品 2服务)',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
    `pay_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    `pay_method` TINYINT DEFAULT NULL COMMENT '支付方式(1微信 2支付宝)',
    `receiver_name` VARCHAR(50) DEFAULT NULL COMMENT '收货人',
    `receiver_phone` VARCHAR(20) DEFAULT NULL COMMENT '收货电话',
    `receiver_address` VARCHAR(255) DEFAULT NULL COMMENT '收货地址',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0取消 1待付款 2待发货 3已发货 4已完成)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `deliver_time` DATETIME DEFAULT NULL COMMENT '发货时间',
    `finish_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ====================================
-- 11. 订单明细表 (order_item)
-- ====================================
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
    `item_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '明细ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
    `product_image` VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
    `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `quantity` INT NOT NULL COMMENT '数量',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '小计',
    PRIMARY KEY (`item_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

-- ====================================
-- 12. 服务预约表 (appointment)
-- ====================================
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
    `appointment_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '预约ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `pet_id` BIGINT NOT NULL COMMENT '宠物ID',
    `service_type` TINYINT NOT NULL COMMENT '服务类型(1洗护 2医疗 3美容)',
    `service_name` VARCHAR(100) NOT NULL COMMENT '服务名称',
    `appointment_date` DATE NOT NULL COMMENT '预约日期',
    `appointment_time` TIME NOT NULL COMMENT '预约时间',
    `shop_name` VARCHAR(100) DEFAULT NULL COMMENT '店铺名称',
    `shop_address` VARCHAR(255) DEFAULT NULL COMMENT '店铺地址',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `price` DECIMAL(10,2) DEFAULT NULL COMMENT '服务价格',
    `note` TEXT DEFAULT NULL COMMENT '备注',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0取消 1待确认 2已确认 3已完成)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`appointment_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_pet_id` (`pet_id`),
    KEY `idx_appointment_date` (`appointment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务预约表';

-- ====================================
-- 13. 消息通知表 (notification)
-- ====================================
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
    `notification_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '通知ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `type` TINYINT NOT NULL COMMENT '类型(1系统 2互动 3订单 4健康)',
    `title` VARCHAR(100) NOT NULL COMMENT '标题',
    `content` TEXT NOT NULL COMMENT '内容',
    `related_id` BIGINT DEFAULT NULL COMMENT '关联ID',
    `is_read` TINYINT NOT NULL DEFAULT 0 COMMENT '是否已读(0否 1是)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`notification_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_read` (`is_read`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息通知表';

-- ====================================
-- 14. 紧急求助表 (help_request)
-- ====================================
DROP TABLE IF EXISTS `help_request`;
CREATE TABLE `help_request` (
    `request_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '求助ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `pet_id` BIGINT DEFAULT NULL COMMENT '宠物ID',
    `type` TINYINT NOT NULL COMMENT '类型(1走失 2紧急)',
    `title` VARCHAR(100) NOT NULL COMMENT '标题',
    `description` TEXT NOT NULL COMMENT '描述',
    `images` TEXT DEFAULT NULL COMMENT '图片(JSON)',
    `location` VARCHAR(200) DEFAULT NULL COMMENT '位置',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `contact_phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0关闭 1进行中 2已解决)',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`request_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_pet_id` (`pet_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='紧急求助表';

-- ====================================
-- 15. 用户关注表 (user_follow)
-- ====================================
DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow` (
    `follow_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关注ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `follow_user_id` BIGINT NOT NULL COMMENT '被关注用户ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
    PRIMARY KEY (`follow_id`),
    UNIQUE KEY `uk_user_follow` (`user_id`, `follow_user_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_follow_user_id` (`follow_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户关注表';

-- ====================================
-- 16. 收藏表 (favorite)
-- ====================================
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
    `favorite_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `post_id` BIGINT NOT NULL COMMENT '动态ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`favorite_id`),
    UNIQUE KEY `uk_user_post` (`user_id`, `post_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_post_id` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';

-- ====================================
-- 17. 购物车表 (shopping_cart)
-- ====================================
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
    `cart_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `quantity` INT NOT NULL DEFAULT 1 COMMENT '数量',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`cart_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表';

-- ====================================
-- 初始化数据
-- ====================================

-- 插入测试用户 (密码: 123456, 使用BCrypt加密)
INSERT INTO `user` (`username`, `password`, `phone`, `email`, `nickname`, `gender`, `status`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '13800138000', 'admin@petcommunity.com', '管理员', 1, 1),
('testuser', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '13900139000', 'test@petcommunity.com', '测试用户', 0, 1);

-- 插入商品分类
INSERT INTO `product_category` (`category_name`, `parent_id`, `sort_order`) VALUES
('宠物食品', 0, 1),
('宠物用品', 0, 2),
('宠物玩具', 0, 3),
('医疗保健', 0, 4),
('狗粮', 1, 1),
('猫粮', 1, 2),
('零食', 1, 3);

-- ====================================
-- 完成初始化
-- ====================================
