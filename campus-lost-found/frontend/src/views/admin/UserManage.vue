<template>
  <div>
    <div class="page-title">用户管理</div>
    <el-card>
      <div class="search-bar">
        <el-input v-model="query.keyword" placeholder="用户名/昵称/手机号" style="width:240px;" />
        <el-button type="primary" icon="el-icon-search" style="margin-left:10px;" @click="search">搜索</el-button>
      </div>

      <!-- 课程示例：el-table 表格组件 -->
      <el-table :data="list" border>
        <el-table-column label="ID" prop="id" width="60" />
        <el-table-column label="用户名" prop="username" />
        <el-table-column label="昵称" prop="nickname" />
        <el-table-column label="手机号" prop="phone" />
        <el-table-column label="角色" prop="role" width="100" />
        <el-table-column label="状态" width="100">
          <template slot-scope="s">
            <el-tag :type="s.row.status === 1 ? 'success' : 'danger'" size="mini">
              {{ s.row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180" />
        <el-table-column label="操作" width="120">
          <template slot-scope="s">
            <el-button v-if="s.row.role !== 'admin'" size="mini"
                       :type="s.row.status === 1 ? 'danger' : 'success'"
                       @click="toggle(s.row)">
              {{ s.row.status === 1 ? '禁用' : '启用' }}
            </el-button>
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
import { getUserList, updateUserStatus } from '@/api'
export default {
  data() {
    return {
      query: { pageNum: 1, pageSize: 10, keyword: '' },
      list: [], total: 0
    }
  },
  created() { this.load() },
  methods: {
    async load() {
      const res = await getUserList(this.query)
      this.list = res.data.list
      this.total = res.data.total
    },
    search() { this.query.pageNum = 1; this.load() },
    async toggle(row) {
      const newStatus = row.status === 1 ? 0 : 1
      await updateUserStatus({ id: row.id, status: newStatus })
      this.$message.success('操作成功')
      this.load()
    }
  }
}
</script>
