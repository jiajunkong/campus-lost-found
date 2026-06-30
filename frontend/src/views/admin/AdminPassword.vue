<template>
  <div>
    <div class="page-title">修改密码</div>
    <el-card style="max-width:500px;">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="form.oldPassword" type="password" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="form.newPassword" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { updatePassword } from '@/api'
export default {
  data() {
    return {
      form: { oldPassword: '', newPassword: '' },
      rules: {
        oldPassword: [{ required: true, message: '请输入原密码' }],
        newPassword: [{ required: true, message: '请输入新密码' }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        await updatePassword({
          id: this.$store.state.user.id,
          oldPassword: this.form.oldPassword,
          newPassword: this.form.newPassword
        })
        this.$message.success('修改成功，请重新登录')
        this.$store.dispatch('logout')
        this.$router.push('/admin/login')
      })
    }
  }
}
</script>
