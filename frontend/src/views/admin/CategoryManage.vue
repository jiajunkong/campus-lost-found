<template>
  <div>
    <div class="page-title">分类管理</div>
    <el-card>
      <div class="search-bar">
        <el-button type="primary" icon="el-icon-plus" @click="showAdd">新增分类</el-button>
      </div>
      <el-table :data="list" border>
        <el-table-column label="ID" prop="id" width="80" />
        <el-table-column label="分类名称" prop="name" />
        <el-table-column label="排序" prop="sort" width="120" />
        <el-table-column label="操作" width="200">
          <template slot-scope="s">
            <el-button size="mini" @click="showEdit(s.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="del(s.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 弹窗：新增/编辑 -->
    <el-dialog :title="form.id ? '编辑分类' : '新增分类'" :visible.sync="visible" width="400px">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
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
import { getCategoryList, saveCategory, deleteCategory } from '@/api'
export default {
  data() {
    return {
      list: [], visible: false,
      form: { id: null, name: '', sort: 0 },
      rules: { name: [{ required: true, message: '请输入分类名称' }] }
    }
  },
  created() { this.load() },
  methods: {
    async load() {
      const res = await getCategoryList()
      this.list = res.data
    },
    showAdd() {
      this.form = { id: null, name: '', sort: 0 }
      this.visible = true
    },
    showEdit(row) {
      this.form = { ...row }
      this.visible = true
    },
    save() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        await saveCategory(this.form)
        this.$message.success('保存成功')
        this.visible = false
        this.load()
      })
    },
    async del(id) {
      await this.$confirm('确定删除？', '提示', { type: 'warning' })
      await deleteCategory(id)
      this.$message.success('删除成功')
      this.load()
    }
  }
}
</script>
