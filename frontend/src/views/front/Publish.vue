<template>
  <div class="container">
    <el-card>
      <div slot="header"><b>{{ form.id ? '编辑信息' : '发布失物/招领信息' }}</b></div>
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="max-width:700px;">
        <el-form-item label="信息类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio label="lost">寻物启事</el-radio>
            <el-radio label="found">招领启事</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="物品分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width:100%;">
            <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="地点" prop="place">
          <el-input v-model="form.place" placeholder="例：图书馆三楼" />
        </el-form-item>
        <el-form-item label="时间" prop="lostTime">
          <el-date-picker v-model="form.lostTime" type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="选择时间" style="width:100%;" />
        </el-form-item>
        <el-form-item label="物品图片">
          <el-upload
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="onUploadSuccess"
            accept="image/*">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" style="color:#909399;font-size:12px;">支持 jpg/png，单张 ≤ 10MB</div>
          </el-upload>
          <img v-if="form.image" :src="form.image" style="width:160px;height:120px;margin-top:10px;object-fit:cover;" />
        </el-form-item>
        <el-form-item label="详细描述" prop="description">
          <el-input type="textarea" :rows="4" v-model="form.description" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">提交</el-button>
          <el-button @click="$router.push('/my-publish')">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getCategoryList, saveGoods, getGoodsDetail, UPLOAD_URL } from '@/api'
export default {
  data() {
    return {
      uploadUrl: UPLOAD_URL,
      form: { id: null, type: 'lost', title: '', categoryId: null, place: '', lostTime: '', description: '', image: '' },
      categories: [],
      rules: {
        type: [{ required: true, message: '请选择类型', trigger: 'change' }],
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
        place: [{ required: true, message: '请输入地点', trigger: 'blur' }],
        description: [{ required: true, message: '请输入描述', trigger: 'blur' }]
      }
    }
  },
  computed: {
    user() { return this.$store.state.user }
  },
  async created() {
    const r = await getCategoryList()
    this.categories = r.data
    // 编辑模式
    if (this.$route.query.id) {
      const res = await getGoodsDetail(this.$route.query.id)
      this.form = { ...res.data }
    }
  },
  methods: {
    onUploadSuccess(res) {
      if (res.code === 200) {
        this.form.image = res.data
        this.$message.success('上传成功')
      } else {
        this.$message.error(res.msg || '上传失败')
      }
    },
    submit() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        const data = { ...this.form, userId: this.user.id }
        await saveGoods(data)
        this.$message.success('提交成功，等待管理员审核')
        this.$router.push('/my-publish')
      })
    }
  }
}
</script>
