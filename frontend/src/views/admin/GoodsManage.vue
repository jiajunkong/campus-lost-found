<template>
  <div>
    <div class="page-title">信息审核管理</div>
    <el-card>
      <div class="search-bar">
        <el-select v-model="query.auditStatus" placeholder="审核状态" clearable style="width:140px;" @change="search">
          <el-option label="待审核" :value="0" />
          <el-option label="已通过" :value="1" />
          <el-option label="已驳回" :value="2" />
          <el-option label="已下架" :value="3" />
        </el-select>
        <el-select v-model="query.type" placeholder="类型" clearable style="width:140px;margin-left:10px;" @change="search">
          <el-option label="寻物" value="lost" />
          <el-option label="招领" value="found" />
        </el-select>
        <el-input v-model="query.keyword" placeholder="关键词" style="width:200px;margin-left:10px;" />
        <el-button type="primary" style="margin-left:10px;" @click="search">搜索</el-button>
      </div>

      <el-table :data="list" border>
        <el-table-column label="ID" prop="id" width="60" />
        <el-table-column label="标题" prop="title" />
        <el-table-column label="类型" width="80">
          <template slot-scope="s">
            <el-tag :type="s.row.type === 'lost' ? 'danger' : 'success'" size="mini">
              {{ s.row.type === 'lost' ? '寻物' : '招领' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="分类" prop="categoryName" width="100" />
        <el-table-column label="发布人" prop="nickname" width="100" />
        <el-table-column label="地点" prop="place" width="160" />
        <el-table-column label="审核状态" width="100">
          <template slot-scope="s">
            <el-tag :type="['info','success','danger','warning'][s.row.auditStatus]" size="mini">
              {{ ['待审核','已通过','已驳回','已下架'][s.row.auditStatus] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="发布时间" prop="createTime" width="170" />
        <el-table-column label="操作" width="260">
          <template slot-scope="s">
            <el-button size="mini" @click="view(s.row)">详情</el-button>
            <el-button size="mini" type="success" @click="audit(s.row, 1)">通过</el-button>
            <el-button size="mini" type="warning" @click="audit(s.row, 2)">驳回</el-button>
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

    <!-- 详情弹窗 -->
    <el-dialog title="信息详情" :visible.sync="visible" width="600px">
      <el-descriptions :column="2" border v-if="detail">
        <el-descriptions-item label="标题">{{ detail.title }}</el-descriptions-item>
        <el-descriptions-item label="类型">{{ detail.type === 'lost' ? '寻物' : '招领' }}</el-descriptions-item>
        <el-descriptions-item label="分类">{{ detail.categoryName }}</el-descriptions-item>
        <el-descriptions-item label="地点">{{ detail.place }}</el-descriptions-item>
        <el-descriptions-item label="时间" :span="2">{{ detail.lostTime }}</el-descriptions-item>
        <el-descriptions-item label="描述" :span="2">{{ detail.description }}</el-descriptions-item>
        <el-descriptions-item label="图片" :span="2">
          <img v-if="detail.image" :src="getImageUrl(detail.image)" style="max-width:300px;" />
          <span v-else>无</span>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import { getGoodsList, auditGoods, deleteGoods, getGoodsDetail } from '@/api'
import { getImageUrl } from '@/api/request'
export default {
  data() {
    return {
      query: { pageNum: 1, pageSize: 10, auditStatus: 0, type: '', keyword: '' },
      list: [], total: 0,
      visible: false, detail: null
    }
  },
  created() { this.load() },
  methods: {
    async load() {
      const res = await getGoodsList(this.query)
      this.list = res.data.list
      this.total = res.data.total
    },
    search() { this.query.pageNum = 1; this.load() },
    async audit(row, status) {
      await auditGoods({ id: row.id, auditStatus: status })
      this.$message.success('操作成功')
      this.load()
    },
    async del(id) {
      await this.$confirm('确定删除？', '提示', { type: 'warning' })
      await deleteGoods(id)
      this.$message.success('删除成功')
      this.load()
    },
    async view(row) {
      const res = await getGoodsDetail(row.id)
      this.detail = res.data
      this.visible = true
    }
  }
}
</script>
