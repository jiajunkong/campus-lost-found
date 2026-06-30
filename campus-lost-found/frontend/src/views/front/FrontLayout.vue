<template>
  <el-container style="height: 100vh;">
    <el-header style="background:#409EFF;color:#fff;display:flex;align-items:center;justify-content:space-between;">
      <div style="font-size:20px;font-weight:bold;">校园失物招领平台</div>
      <el-menu mode="horizontal" background-color="#409EFF" text-color="#fff" active-text-color="#FFD04B"
               :default-active="$route.path" router style="border-bottom:none;">
        <el-menu-item index="/home">首页</el-menu-item>
        <el-menu-item index="/notice">公告</el-menu-item>
        <el-menu-item index="/publish">发布信息</el-menu-item>
        <el-menu-item index="/my-publish">我的发布</el-menu-item>
        <el-menu-item index="/profile">个人中心</el-menu-item>
      </el-menu>
      <div>
        <template v-if="user">
          <span style="margin-right:10px;">你好，{{ user.nickname || user.username }}</span>
          <el-button size="mini" type="warning" @click="logout">退出登录</el-button>
        </template>
        <template v-else>
          <el-button size="mini" @click="$router.push('/login')">登录</el-button>
          <el-button size="mini" type="success" @click="$router.push('/register')">注册</el-button>
        </template>
      </div>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
    <el-footer style="text-align:center;color:#999;">
      © {{ new Date().getFullYear() }} 校园失物招领平台 — Web框架与项目实训
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: 'FrontLayout',
  computed: {
    user() { return this.$store.state.user }
  },
  methods: {
    logout() {
      this.$store.dispatch('logout')
      this.$message.success('已退出登录')
      this.$router.push('/home')
    }
  }
}
</script>
