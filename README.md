# 校园失物招领平台

基于 SpringBoot + Vue2 + MySQL 的校园失物招领平台，前后端分离架构。

## 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端 | SpringBoot | 2.7.18 |
| 后端 | MyBatis | 2.3.1 |
| 后端 | PageHelper | 1.4.7 |
| 前端 | Vue.js | 2.7.14 |
| 前端 | Element UI | 2.15.14 |
| 前端 | Axios | 0.27.2 |
| 数据库 | MySQL | 5.7+ |

## 项目结构

```
campus-lost-found/
├── backend/          # SpringBoot 后端
│   ├── src/main/java/com/campus/lostfound/
│   │   ├── controller/    # 控制器层
│   │   ├── service/       # 业务逻辑层
│   │   ├── mapper/        # 数据访问层
│   │   ├── entity/        # 实体类
│   │   ├── config/        # 配置类
│   │   └── common/        # 通用工具
│   └── src/main/resources/
│       ├── mapper/        # MyBatis XML
│       └── application.properties
├── frontend/         # Vue2 前端
│   ├── src/
│   │   ├── api/           # API 接口封装
│   │   ├── views/         # 页面组件
│   │   │   ├── front/     # 前台页面
│   │   │   └── admin/     # 后台页面
│   │   ├── router/        # 路由配置
│   │   └── store/         # Vuex 状态管理
│   └── public/
└── sql/              # 数据库脚本
    └── campus_lost_found.sql
```

## 快速开始

### 1. 数据库

```bash
mysql -u root -p < sql/campus_lost_found.sql
```

### 2. 启动后端

```bash
cd backend
# 修改 application.properties 中的数据库密码
mvn spring-boot:run
```

后端运行在 `http://localhost:8080`

### 3. 启动前端

```bash
cd frontend
npm install
npm run serve
```

前端运行在 `http://localhost:8081`

## 默认账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 用户 | zhangsan | 123456 |
| 用户 | lisi | 123456 |

## 功能模块

### 前台用户端
- 用户注册 / 登录
- 失物信息浏览、搜索、筛选
- 发布寻物 / 招领信息（支持图片上传）
- 留言互动
- 个人发布管理

### 后台管理员端
- 用户管理（启用 / 禁用）
- 失物信息审核（通过 / 驳回 / 删除）
- 物品分类管理
- 公告管理
- 数据统计

## 接口规范

所有接口统一返回格式：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

接口前缀：
- `/user/**` - 用户相关
- `/category/**` - 分类管理
- `/goods/**` - 失物信息
- `/comment/**` - 留言管理
- `/notice/**` - 公告管理
- `/stat/**` - 数据统计
- `/file/**` - 文件上传
