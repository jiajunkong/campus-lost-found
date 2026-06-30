-- ============================================================
-- 校园失物招领平台 数据库脚本
-- 数据库：MySQL 5.7+
-- Aiven 云部署版：直接在默认数据库(defaultdb)中建表
-- ============================================================

-- ----------------------------
-- 1. 用户表 user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名（登录用）',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `role` VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT '角色：user普通用户 admin管理员',
  `status` INT NOT NULL DEFAULT 1 COMMENT '账号状态：1正常 0禁用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- 2. 物品分类表 category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `sort` INT DEFAULT 0 COMMENT '排序字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物品分类表';

-- ----------------------------
-- 3. 失物信息表 lost_goods（核心表）
-- ----------------------------
DROP TABLE IF EXISTS `lost_goods`;
CREATE TABLE `lost_goods` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(100) NOT NULL COMMENT '标题',
  `image` VARCHAR(255) DEFAULT NULL COMMENT '物品图片',
  `category_id` INT DEFAULT NULL COMMENT '分类id',
  `place` VARCHAR(100) DEFAULT NULL COMMENT '地点',
  `lost_time` DATETIME DEFAULT NULL COMMENT '物品时间（丢失/拾到时间）',
  `description` TEXT COMMENT '描述',
  `type` VARCHAR(20) NOT NULL COMMENT '类型：lost寻物启事 found招领启事',
  `user_id` INT NOT NULL COMMENT '发布人id',
  `audit_status` INT NOT NULL DEFAULT 0 COMMENT '审核状态：0待审核 1通过 2驳回 3下架',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='失物信息表';

-- ----------------------------
-- 4. 留言表 comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goods_id` INT NOT NULL COMMENT '关联失物id',
  `content` VARCHAR(500) NOT NULL COMMENT '留言内容',
  `user_id` INT NOT NULL COMMENT '留言人id',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言表';

-- ----------------------------
-- 5. 公告表 notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` VARCHAR(100) NOT NULL COMMENT '公告标题',
  `content` TEXT COMMENT '公告内容',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

-- ============================================================
-- 初始化数据
-- ============================================================

-- 默认管理员账号 / 测试用户账号 (密码均为 123456)
INSERT INTO `user` (`username`, `password`, `nickname`, `phone`, `role`, `status`) VALUES
('admin', '123456', '系统管理员', '13800000000', 'admin', 1),
('zhangsan', '123456', '张三', '13800000001', 'user', 1),
('lisi', '123456', '李四', '13800000002', 'user', 1);

-- 默认分类
INSERT INTO `category` (`name`, `sort`) VALUES
('电子产品', 1),
('证件卡片', 2),
('钥匙', 3),
('书籍资料', 4),
('衣物服饰', 5),
('其他物品', 99);

-- 示例失物信息
INSERT INTO `lost_goods` (`title`, `image`, `category_id`, `place`, `lost_time`, `description`, `type`, `user_id`, `audit_status`) VALUES
('丢失一部黑色iPhone手机', '/api/upload/lost_black_iphone.jpg', 1, '图书馆三楼自习区', '2026-06-10 18:30:00', '黑色iPhone 13，屏幕右下角有划痕，捡到请联系。', 'lost', 2, 1),
('捡到一串钥匙', '/api/upload/found_keys_blue_keychain.jpg', 3, '一食堂门口', '2026-06-12 12:00:00', '一串带蓝色挂坠的钥匙，约5把。', 'found', 3, 1),
('丢失校园卡一张', '', 2, '体育馆篮球场', '2026-06-13 20:00:00', '校园卡丢失，姓名张三，麻烦拾到的同学联系我。', 'lost', 2, 0);

-- 示例留言
INSERT INTO `comment` (`goods_id`, `content`, `user_id`) VALUES
(1, '我在图书馆好像见过类似的手机！', 3),
(2, '请问钥匙现在在哪里？', 2);

-- 示例公告
INSERT INTO `notice` (`title`, `content`) VALUES
('平台上线公告', '欢迎使用校园失物招领平台！请大家文明发帖、互帮互助。'),
('使用须知', '发布信息需要等待管理员审核通过后才会展示，请耐心等待。');
