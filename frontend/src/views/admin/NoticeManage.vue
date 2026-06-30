<template>
  <div>
    <div class="page-title">公告管理</div>
    <el-card>
      <div class="search-bar">
        <el-button type="primary" icon="el-icon-plus" @click="showAdd">新增公告</el-button>
      </div>
      <el-table :data="list" border>
        <el-table-column label="ID" prop="id" width="80" />
        <el-table-column label="标题" prop="title" />
        <el-table-column label="发布时间" prop="createTime" width="180" />
        <el-table-column label="操作" width="200">
          <template slot-scope="s">
            <el-button size="mini" @click="showEdit(s.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="del(s.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="form.id ? '编辑公告' : '新增公告'" :visible.sync="visible" width="500px">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input type="textarea" :rows="6" v-model="form.content" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getNoticeList, saveNotice, deleteNotice } from '@/api'
export default {
  data() {
    return {
      list: [], visible: false,
      form: { id: null, title: '', content: '' },
      rules: {
        title: [{ required: true, message: '请输入标题' }],
        content: [{ required: true, message: '请输入内容' }]
      }
    }
  },
  created() { this.load() },
  methods: {
    async load() {
      const res = await getNoticeList()
      this.list = res.data
    },
    showAdd() {
      this.form = { id: null, title: '', content: '' }
      this.visible = true
    },
    showEdit(row) {
      this.form = { ...row }
      this.visible = true
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        await saveNotice(this.form)
        this.$message.success('保存成功')
        this.visible = false
        this.load()
      })
    },
    async del(id) {
      await this.$confirm('确定删除？', '提示', { type: 'warning' })
      await deleteNotice(id)
      this.$message.success('删除成功')
      this.load()
    }
  }
}
</script>
