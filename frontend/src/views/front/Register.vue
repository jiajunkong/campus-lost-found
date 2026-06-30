<template>
  <div style="display:flex;justify-content:center;align-items:center;height:100vh;background:#f0f2f5;">
    <el-card style="width:420px;">
      <div slot="header" style="text-align:center;font-size:18px;font-weight:bold;">用户注册</div>
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%;" @click="submit">注册</el-button>
        </el-form-item>
        <div style="text-align:center;">
          <router-link to="/login">已有账号？去登录</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { register } from '@/api'
export default {
  data() {
    return {
      form: { username: '', password: '', nickname: '', phone: '' },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        await register(this.form)
        this.$message.success('注册成功，请登录')
        this.$router.push('/login')
      })
    }
  }
}
</script>
