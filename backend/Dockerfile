# 多阶段构建：在云端自动编译 Java 后端，无需本地安装 Java/Maven
# 你本地只需要 Git，其他全部在云端完成

# ===== 第一阶段：编译 =====
FROM maven:3.8-openjdk-8 AS builder
WORKDIR /build
# 先复制 pom.xml 和源码，利用 Docker 缓存加速重复部署
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -q

# ===== 第二阶段：运行 =====
FROM openjdk:8-jdk-slim
WORKDIR /app

# 创建上传文件目录（Render 容器内使用 /tmp/uploads）
RUN mkdir -p /tmp/uploads

# 从第一阶段复制编译好的 JAR
COPY --from=builder /build/target/lostfound-1.0.0.jar app.jar

# 暴露端口（Render 会使用 PORT 环境变量）
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
