<template>
  <div style="display:flex;justify-content:center;align-items:center;height:100vh;background:#f0f2f5;">
    <el-card style="width:400px;">
      <div slot="header" style="text-align:center;font-size:18px;font-weight:bold;">用户登录</div>
      <el-form :model="form" :rules="rules" ref="form" label-width="70px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%;" @click="submit">登录</el-button>
        </el-form-item>
        <div style="text-align:center;">
          <router-link to="/register">还没有账号？注册</router-link>
          <span style="margin:0 10px;">|</span>
          <router-link to="/admin/login">管理员入口</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { login } from '@/api'
export default {
  data() {
    return {
      form: { username: '', password: '' },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        try {
          const res = await login(this.form)
          this.$store.dispatch('login', res.data)
          this.$message.success('登录成功')
          this.$router.push('/home')
        } catch (e) {
          // 请求拦截器已经弹出错误提示，这里捕获异常防止页面红屏
        }
      })
    }
  }
}
</script>
