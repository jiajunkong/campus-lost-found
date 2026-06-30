package com.campus.lostfound.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * 数据库自动初始化：后端启动时自动创建表和初始数据
 * 解决 Aiven 免费 MySQL 没有 SQL 编辑器的问题
 */
@Configuration
public class DatabaseInitializer {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private DataSource dataSource;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                Statement stmt = conn.createStatement();

                // 检查是否已经初始化过（如果 user 表存在说明已建表）
                boolean tableExists = false;
                try {
                    stmt.executeQuery("SELECT 1 FROM `user` LIMIT 1");
                    tableExists = true;
                } catch (Exception e) {
                    // 表不存在，正常
                }

                if (tableExists) {
                    log.info("数据库已初始化，跳过自动建表");
                    return;
                }

                log.info("开始自动创建数据库表和初始数据...");

                // 1. 创建用户表
                stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `user` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`username` VARCHAR(50) NOT NULL," +
                    "`password` VARCHAR(100) NOT NULL," +
                    "`nickname` VARCHAR(50) DEFAULT NULL," +
                    "`phone` VARCHAR(20) DEFAULT NULL," +
                    "`role` VARCHAR(20) NOT NULL DEFAULT 'user'," +
                    "`status` INT NOT NULL DEFAULT 1," +
                    "`create_time` DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (`id`)," +
                    "UNIQUE KEY `uk_username` (`username`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
                );

                // 2. 创建分类表
                stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `category` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(50) NOT NULL," +
                    "`sort` INT DEFAULT 0," +
                    "PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
                );

                // 3. 创建失物表
                stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `lost_goods` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`title` VARCHAR(100) NOT NULL," +
                    "`image` VARCHAR(255) DEFAULT NULL," +
                    "`category_id` INT DEFAULT NULL," +
                    "`place` VARCHAR(100) DEFAULT NULL," +
                    "`lost_time` DATETIME DEFAULT NULL," +
                    "`description` TEXT," +
                    "`type` VARCHAR(20) NOT NULL," +
                    "`user_id` INT NOT NULL," +
                    "`audit_status` INT NOT NULL DEFAULT 0," +
                    "`create_time` DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
                );

                // 4. 创建留言表
                stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `comment` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`goods_id` INT NOT NULL," +
                    "`content` VARCHAR(500) NOT NULL," +
                    "`user_id` INT NOT NULL," +
                    "`create_time` DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
                );

                // 5. 创建公告表
                stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS `notice` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT," +
                    "`title` VARCHAR(100) NOT NULL," +
                    "`content` TEXT," +
                    "`create_time` DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"
                );

                log.info("数据库表创建成功！");

                // 6. 插入初始数据
                stmt.executeUpdate(
                    "INSERT INTO `user` (`username`, `password`, `nickname`, `phone`, `role`, `status`) VALUES " +
                    "('admin', '123456', '系统管理员', '13800000000', 'admin', 1), " +
                    "('zhangsan', '123456', '张三', '13800000001', 'user', 1), " +
                    "('lisi', '123456', '李四', '13800000002', 'user', 1)"
                );

                stmt.executeUpdate(
                    "INSERT INTO `category` (`name`, `sort`) VALUES " +
                    "('电子产品', 1), ('证件卡片', 2), ('钥匙', 3), " +
                    "('书籍资料', 4), ('衣物服饰', 5), ('其他物品', 99)"
                );

                stmt.executeUpdate(
                    "INSERT INTO `notice` (`title`, `content`) VALUES " +
                    "('平台上线公告', '欢迎使用校园失物招领平台！'), " +
                    "('使用须知', '发布信息需要等待管理员审核通过后才会展示。')"
                );

                log.info("初始数据插入成功！数据库初始化完成。");

            } catch (Exception e) {
                log.error("数据库自动初始化失败：", e);
            }
        };
    }
}
