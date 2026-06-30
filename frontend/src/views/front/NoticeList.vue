<template>
  <div class="container">
    <el-card>
      <div slot="header"><b>平台公告</b></div>
      <el-table :data="list" border>
        <el-table-column label="ID" prop="id" width="60" />
        <el-table-column label="标题" prop="title" />
        <el-table-column label="发布时间" prop="createTime" width="200" />
        <el-table-column label="操作" width="100">
          <template slot-scope="s">
            <el-button size="mini" @click="view(s.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="公告详情" :visible.sync="visible" width="600px">
      <h3>{{ detail.title }}</h3>
      <p style="color:#909399;">{{ detail.createTime }}</p>
      <div style="white-space:pre-wrap;">{{ detail.content }}</div>
    </el-dialog>
  </div>
</template>

<script>
import { getNoticeList } from '@/api'
export default {
  data() {
    return { list: [], visible: false, detail: {} }
  },
  async created() {
    const res = await getNoticeList()
    this.list = res.data
  },
  methods: {
    view(row) { this.detail = row; this.visible = true }
  }
}
</script>
