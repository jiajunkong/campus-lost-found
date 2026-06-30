<template>
  <div>
    <div class="page-title">数据统计</div>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card><div style="font-size:14px;color:#909399;">注册用户数</div>
          <div style="font-size:32px;font-weight:bold;color:#409EFF;">{{ overview.userCount || 0 }}</div></el-card>
      </el-col>
      <el-col :span="6">
        <el-card><div style="font-size:14px;color:#909399;">失物信息总数</div>
          <div style="font-size:32px;font-weight:bold;color:#67C23A;">{{ overview.goodsCount || 0 }}</div></el-card>
      </el-col>
      <el-col :span="6">
        <el-card><div style="font-size:14px;color:#909399;">留言总数</div>
          <div style="font-size:32px;font-weight:bold;color:#E6A23C;">{{ overview.commentCount || 0 }}</div></el-card>
      </el-col>
      <el-col :span="6">
        <el-card><div style="font-size:14px;color:#909399;">待审核数量</div>
          <div style="font-size:32px;font-weight:bold;color:#F56C6C;">{{ overview.pendingCount || 0 }}</div></el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top:20px;">
      <el-col :span="12">
        <el-card>
          <div slot="header"><b>按类型统计</b></div>
          <el-table :data="byType" border>
            <el-table-column label="类型">
              <template slot-scope="s">{{ s.row.name === 'lost' ? '寻物启事' : '招领启事' }}</template>
            </el-table-column>
            <el-table-column label="数量" prop="value" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header"><b>按分类统计</b></div>
          <el-table :data="byCategory" border>
            <el-table-column label="分类" prop="name" />
            <el-table-column label="数量" prop="value" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getStatOverview, getStatByType, getStatByCategory } from '@/api'
export default {
  data() {
    return { overview: {}, byType: [], byCategory: [] }
  },
  async created() {
    this.overview = (await getStatOverview()).data
    this.byType = (await getStatByType()).data
    this.byCategory = (await getStatByCategory()).data
  }
}
</script>
