<template>
  <div style="display:flex;justify-content:center;align-items:center;height:100vh;background:#304156;">
    <el-card style="width:380px;">
      <div slot="header" style="text-align:center;font-size:18px;font-weight:bold;">后台管理系统登录</div>
      <el-form :model="form" :rules="rules" ref="form" label-width="70px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%;" @click="submit">登录</el-button>
        </el-form-item>
      </el-form>
      <div style="text-align:center;color:#909399;font-size:12px;">
        默认账号 admin / 密码 123456
      </div>
    </el-card>
  </div>
</template>

<script>
import { login } from '@/api'
export default {
  data() {
    return {
      form: { username: 'admin', password: '' },
      rules: {
        username: [{ required: true, message: '请输入用户名' }],
        password: [{ required: true, message: '请输入密码' }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        try {
          const res = await login(this.form)
          if (res.data.role !== 'admin') {
            return this.$message.error('该账号无管理员权限')
          }
          this.$store.dispatch('login', res.data)
          this.$message.success('登录成功')
          this.$router.push('/admin/dashboard')
        } catch (e) {
          // 请求拦截器已经弹出错误提示，这里捕获异常防止页面红屏
        }
      })
    }
  }
}
</script>
