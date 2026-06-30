<template>
  <el-container style="height:100vh;">
    <el-aside width="220px" style="background:#304156;color:#fff;">
      <div style="height:60px;line-height:60px;text-align:center;font-size:18px;font-weight:bold;border-bottom:1px solid #1f2d3d;">
        失物招领·后台
      </div>
      <el-menu :default-active="$route.path" router background-color="#304156"
               text-color="#fff" active-text-color="#FFD04B">
        <el-menu-item index="/admin/dashboard">
          <i class="el-icon-data-line"></i>数据统计
        </el-menu-item>
        <el-menu-item index="/admin/users">
          <i class="el-icon-user"></i>用户管理
        </el-menu-item>
        <el-menu-item index="/admin/goods">
          <i class="el-icon-document-checked"></i>信息审核
        </el-menu-item>
        <el-menu-item index="/admin/category">
          <i class="el-icon-menu"></i>分类管理
        </el-menu-item>
        <el-menu-item index="/admin/notice">
          <i class="el-icon-bell"></i>公告管理
        </el-menu-item>
        <el-menu-item index="/admin/password">
          <i class="el-icon-lock"></i>修改密码
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background:#fff;border-bottom:1px solid #eee;display:flex;align-items:center;justify-content:space-between;">
        <div>欢迎使用，{{ user && user.nickname }}</div>
        <el-button size="mini" @click="logout">退出登录</el-button>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  computed: { user() { return this.$store.state.user } },
  methods: {
    logout() {
      this.$store.dispatch('logout')
      this.$router.push('/admin/login')
    }
  }
}
</script>
