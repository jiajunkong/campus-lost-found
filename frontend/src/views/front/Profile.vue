<template>
  <div class="container">
    <el-card>
      <div slot="header"><b>个人中心</b></div>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="修改资料" name="info">
          <el-form :model="info" label-width="80px" style="max-width:500px;">
            <el-form-item label="用户名">
              <el-input v-model="info.username" disabled />
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="info.nickname" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="info.phone" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveInfo">保存</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="修改密码" name="pwd">
          <el-form :model="pwd" label-width="100px" style="max-width:500px;">
            <el-form-item label="原密码">
              <el-input v-model="pwd.oldPassword" type="password" />
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="pwd.newPassword" type="password" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="savePwd">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import { getUserInfo, updateUser, updatePassword } from '@/api'
export default {
  data() {
    return {
      activeTab: 'info',
      info: {},
      pwd: { oldPassword: '', newPassword: '' }
    }
  },
  computed: {
    user() { return this.$store.state.user }
  },
  async created() {
    const res = await getUserInfo(this.user.id)
    this.info = res.data
  },
  methods: {
    async saveInfo() {
      await updateUser({ id: this.info.id, nickname: this.info.nickname, phone: this.info.phone })
      this.$message.success('保存成功')
      // 同步到本地
      this.$store.dispatch('login', { ...this.user, nickname: this.info.nickname })
    },
    async savePwd() {
      if (!this.pwd.oldPassword || !this.pwd.newPassword) {
        return this.$message.warning('请填写完整')
      }
      await updatePassword({
        id: this.user.id,
        oldPassword: this.pwd.oldPassword,
        newPassword: this.pwd.newPassword
      })
      this.$message.success('修改成功，请重新登录')
      this.$store.dispatch('logout')
      this.$router.push('/login')
    }
  }
}
</script>
