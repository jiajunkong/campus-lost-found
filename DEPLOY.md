# 校园失物招领平台 - 零成本云部署指南

## 部署架构

```
用户浏览器
    |
    v
Render Static Site (前端 Vue2)  https://campus-lost-found-frontend.onrender.com
    |
    v
Render Web Service (后端 SpringBoot)  https://campus-lost-found-backend.onrender.com
    |
    v
Aiven Free MySQL (数据库)  mysql-xxxx.aivencloud.com
```

---

## 一、准备工作

### 1. 注册账号（全部免费，无需信用卡）

| 平台 | 用途 | 注册地址 |
|------|------|----------|
| **Aiven** | 免费 MySQL 数据库 | https://aiven.io |
| **Render** | 免费托管前后端 | https://render.com |
| **GitHub** | 代码仓库 | https://github.com |

### 2. 你本地只需要什么？

只需要 **Git**（你说你电脑上已经有了）。

不需要安装 Java、Maven、Node.js、MySQL —— **编译和构建全部在云端自动完成**。

---

## 二、创建免费 MySQL 数据库（Aiven）

### 步骤 1：创建 Aiven 账号
1. 访问 https://aiven.io 注册账号（可用 GitHub 账号直接登录）
2. 不需要绑定信用卡

### 步骤 2：创建 MySQL 服务
1. 登录 Aiven Console → 点击 **"Create Service"**
2. 选择 **MySQL**
3. 选择 **Free Plan**（免费层：1GB 存储，永久免费）
4. 选择离你近的 Region（如 `Asia Pacific` → `Singapore`）
5. 设置 Service Name：`campus-mysql`
6. 点击 **"Create Service"**，等待 2-3 分钟部署完成

### 步骤 3：获取数据库连接信息
1. 进入服务详情页 → 点击 **"Overview"**
2. 记录以下信息（后面要用）：
   - **Host**: `mysql-xxxx.aivencloud.com`
   - **Port**: `xxxx`
   - **User**: `avnadmin`
   - **Password**: 点击 "Show" 查看
   - **Database Name**: `defaultdb`

### 步骤 4：导入数据库表结构
1. 在 Aiven Console → 点击 **"Services"** → 选择你刚创建的 MySQL 服务
2. 点击左侧 **"SQL Editor"**（或 "Query Editor"）
3. 把 `sql/campus_lost_found.sql` 文件的**全部内容**复制粘贴进去
4. 点击 **"Execute"** 执行，创建所有表

> **备选方案**：如果没有 SQL Editor，可以用 Navicat / DBeaver / TablePlus 连接后导入 SQL 文件。

### 步骤 5：组合 JDBC URL

将以下信息组合成 JDBC URL：

```
jdbc:mysql://<Host>:<Port>/<Database>?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=true&requireSSL=true
```

例如：
```
jdbc:mysql://mysql-1234.aivencloud.com:12345/defaultdb?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=true&requireSSL=true
```

**保存好这个 URL，后面部署后端时需要用到！**

---

## 三、部署后端到 Render

### 步骤 1：上传代码到 GitHub

1. 在项目根目录打开 **Git Bash**
2. 执行以下命令：

```bash
git init
git add .
git commit -m "init: ready for cloud deploy"
```

3. 去 GitHub 网页创建一个新仓库（名字如 `campus-lost-found`），**不要勾选** README / .gitignore / License
4. 回到 Git Bash，关联远程仓库并推送：

```bash
git remote add origin https://github.com/你的用户名/campus-lost-found.git
git branch -M main
git push -u origin main
```

### 步骤 2：在 Render 创建后端服务

1. 登录 https://render.com（用 GitHub 账号登录）
2. 点击 **"New +"** → **"Web Service"**
3. 选择你的 GitHub 仓库 `campus-lost-found`
4. 配置如下：

| 配置项 | 值 |
|--------|-----|
| Name | `campus-lost-found-backend` |
| Environment | **Docker** |
| Dockerfile Path | `./backend/Dockerfile` |
| Branch | `main` |

5. 展开 **"Advanced"** → 点击 **"Add Environment Variable"**，逐个添加：

| 环境变量 | 值 | 说明 |
|----------|-----|------|
| `MYSQL_URL` | `jdbc:mysql://...` | 第二步组合的 JDBC URL |
| `MYSQL_USER` | `avnadmin` | Aiven 用户名 |
| `MYSQL_PASSWORD` | `你的密码` | Aiven 密码 |
| `UPLOAD_PATH` | `/tmp/uploads/` | 容器内上传路径 |
| `LOG_LEVEL` | `info` | 日志级别 |

6. 点击 **"Create Web Service"**
7. Render 会自动拉取代码 → Docker 多阶段构建（自动下载 Maven + 编译 Java + 打包）→ 启动
8. 等待 3-5 分钟，看到状态变为 **"Live"** 就成功了
9. 记录页面上显示的域名，如：
   `https://campus-lost-found-backend.onrender.com`

**记录这个后端地址，下一步要用！**

---

## 四、部署前端到 Render

### 步骤 1：修改前端生产环境配置

用记事本打开 `frontend/.env.production` 文件：

```bash
# 把 REPLACE_WITH_BACKEND_URL 替换为你的后端地址
VUE_APP_BASE_API=https://campus-lost-found-backend.onrender.com
```

然后提交到 Git：

```bash
git add frontend/.env.production
git commit -m "config: set production backend URL"
git push
```

### 步骤 2：在 Render 创建前端静态站点

1. 在 Render Dashboard → 点击 **"New +"** → **"Static Site"**
2. 选择同一个 GitHub 仓库 `campus-lost-found`
3. 配置如下：

| 配置项 | 值 |
|--------|-----|
| Name | `campus-lost-found-frontend` |
| Branch | `main` |
| Build Command | `cd frontend && npm install && npm run build` |
| Publish Directory | `./frontend/dist` |

4. 点击 **"Create Static Site"**
5. Render 会自动执行 `npm install` + `npm run build`（云端安装 Node.js 并构建前端）
6. 等待 2-3 分钟，状态变为 **"Live"**
7. 访问分配的域名：
   `https://campus-lost-found-frontend.onrender.com`

**大功告成！打开这个地址就是你的线上项目！**

---

## 五、部署完成检查清单

- [ ] Aiven MySQL 服务运行中（状态为 Running）
- [ ] 数据库表已导入（user, category, lost_goods, comment, notice）
- [ ] 代码已推送到 GitHub
- [ ] Render 后端服务部署成功（状态为 Live）
- [ ] 后端环境变量配置正确（MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD）
- [ ] 前端 `.env.production` 中的 `VUE_APP_BASE_API` 已替换为后端地址
- [ ] Render 前端静态站点构建成功（状态为 Live）
- [ ] 打开前端地址能正常访问页面
- [ ] 注册/登录功能正常

---

## 六、免费额度说明

| 平台 | 免费额度 | 限制 |
|------|----------|------|
| **Aiven MySQL** | 1GB 存储，**永久免费** | 无高可用，无静态 IP |
| **Render Web Service** | 750 小时/月 | 15 分钟无访问后休眠，下次访问需等 30 秒唤醒 |
| **Render Static Site** | 100GB 带宽/月 | 无休眠限制，始终在线 |

> 免费额度对课程项目演示完全够用。

---

## 七、常见问题

### Q1: Render 后端部署失败，Build 超时
**原因**：Docker 多阶段构建需要下载 Maven 依赖，首次可能较慢。
**解决**：等待 5-8 分钟，如果仍然失败，在 Render 手动触发 **"Manual Deploy" → "Clear build cache & deploy"**。

### Q2: 数据库连接报错 "SSL required"
**原因**：Aiven MySQL 强制要求 SSL 连接。
**解决**：确保 JDBC URL 中包含 `useSSL=true&requireSSL=true`。

### Q3: 前端页面打开是空白
**原因**：可能 `VUE_APP_BASE_API` 没改对，或后端还没部署完成。
**解决**：打开浏览器 F12 控制台看 Network 面板，检查 API 请求地址是否正确。

### Q4: 图片上传后刷新页面图片消失了
**原因**：Render 免费 Web Service 磁盘是临时的，重启后上传文件丢失。
**解决**：免费层正常限制。可接入免费图床（如 Cloudinary 免费层）来持久化图片。

### Q5: 后端服务休眠后第一次访问很慢
**原因**：Render 免费 Web Service 15 分钟无访问会自动休眠。
**解决**：正常现象，等待 30 秒左右唤醒即可。

### Q6: Aiven SQL Editor 找不到
**原因**：Aiven 界面更新，入口可能换了位置。
**解决**：在服务详情页左侧菜单找 "SQL editor" 或 "Query editor"，或者用本地数据库工具（Navicat/DBeaver）连接后导入 SQL。

---

## 八、使用 Blueprint 一键部署（推荐）

项目根目录有 `render.yaml`，可以用 Render Blueprint 一键部署：

1. 在 Render Dashboard → 点击 **"Blueprints"** → **"New Blueprint Instance"**
2. 选择你的 GitHub 仓库
3. Render 会自动读取 `render.yaml` 并创建前后端两个服务
4. 在后端服务中填入 Aiven 数据库的环境变量
