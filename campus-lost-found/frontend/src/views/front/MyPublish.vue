<template>
  <div class="container">
    <el-card>
      <div slot="header" style="display:flex;justify-content:space-between;">
        <b>我的发布</b>
        <el-button type="primary" size="small" @click="$router.push('/publish')">+ 发布信息</el-button>
      </div>
      <el-table :data="list" border>
        <el-table-column label="ID" prop="id" width="60" />
        <el-table-column label="类型" width="100">
          <template slot-scope="s">
            <el-tag :type="s.row.type === 'lost' ? 'danger' : 'success'" size="mini">
              {{ s.row.type === 'lost' ? '寻物' : '招领' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="标题" prop="title" />
        <el-table-column label="分类" prop="categoryName" width="120" />
        <el-table-column label="地点" prop="place" width="160" />
        <el-table-column label="审核状态" width="100">
          <template slot-scope="s">
            <el-tag :type="auditTag(s.row.auditStatus)" size="mini">
              {{ auditText(s.row.auditStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="发布时间" prop="createTime" width="170" />
        <el-table-column label="操作" width="220">
          <template slot-scope="s">
            <el-button size="mini" @click="edit(s.row)">编辑</el-button>
            <el-button size="mini" type="warning" @click="offline(s.row)">下架</el-button>
            <el-button size="mini" type="danger" @click="del(s.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-bar">
        <el-pagination background layout="total, prev, pager, next"
                       :total="total" :page-size="query.pageSize"
                       :current-page.sync="query.pageNum" @current-change="load" />
      </div>
    </el-card>
  </div>
</template>

<script>
import { getGoodsList, deleteGoods, auditGoods } from '@/api'
export default {
  data() {
    return {
      query: { pageNum: 1, pageSize: 10 },
      list: [],
      total: 0
    }
  },
  computed: {
    user() { return this.$store.state.user }
  },
  created() { this.load() },
  methods: {
    async load() {
      const res = await getGoodsList({ ...this.query, userId: this.user.id })
      this.list = res.data.list
      this.total = res.data.total
    },
    auditTag(s) {
      return ['info', 'success', 'danger', 'warning'][s] || 'info'
    },
    auditText(s) {
      return ['待审核', '已通过', '已驳回', '已下架'][s] || '未知'
    },
    edit(row) {
      this.$router.push({ path: '/publish', query: { id: row.id } })
    },
    async offline(row) {
      await this.$confirm('确定下架该信息？', '提示', { type: 'warning' })
      await auditGoods({ id: row.id, auditStatus: 3 })
      this.$message.success('已下架')
      this.load()
    },
    async del(id) {
      await this.$confirm('确定删除？', '提示', { type: 'warning' })
      await deleteGoods(id)
      this.$message.success('删除成功')
      this.load()
    }
  }
}
</script>
