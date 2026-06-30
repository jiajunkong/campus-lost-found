<template>
  <div class="container">
    <!-- 搜索筛选 -->
    <el-card class="search-bar">
      <el-form :inline="true" :model="query">
        <el-form-item label="类型">
          <el-select v-model="query.type" placeholder="全部" clearable style="width:120px;">
            <el-option label="寻物启事" value="lost" />
            <el-option label="招领启事" value="found" />
          </el-select>
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="query.categoryId" placeholder="全部" clearable style="width:140px;">
            <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="关键词">
          <el-input v-model="query.keyword" placeholder="标题/描述/地点" style="width:200px;" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 失物列表（卡片网格） -->
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in list" :key="item.id">
        <el-card shadow="hover" style="margin-bottom:16px;cursor:pointer;" @click.native="goDetail(item.id)">
          <el-image
            :src="getImageUrl(item.image) || 'https://via.placeholder.com/300x180?text=No+Image'"
            style="width:100%;height:160px;" fit="cover" />
          <div style="margin-top:10px;">
            <el-tag :type="item.type === 'lost' ? 'danger' : 'success'" size="mini">
              {{ item.type === 'lost' ? '寻物' : '招领' }}
            </el-tag>
            <span style="margin-left:6px;font-weight:bold;">{{ item.title }}</span>
          </div>
          <div style="color:#909399;font-size:13px;margin-top:6px;">
            分类：{{ item.categoryName || '其他' }}
          </div>
          <div style="color:#909399;font-size:13px;">地点：{{ item.place }}</div>
          <div style="color:#909399;font-size:12px;margin-top:6px;">
            发布人：{{ item.nickname || item.username }} | {{ item.createTime }}
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 分页 -->
    <div class="pagination-bar">
      <el-pagination
        background
        layout="total, prev, pager, next"
        :total="total"
        :page-size="query.pageSize"
        :current-page.sync="query.pageNum"
        @current-change="loadData" />
    </div>
  </div>
</template>

<script>
import { getGoodsList, getCategoryList } from '@/api'
import { getImageUrl } from '@/api/request'
export default {
  data() {
    return {
      query: { pageNum: 1, pageSize: 8, type: '', categoryId: null, keyword: '', auditStatus: 1 },
      list: [],
      total: 0,
      categories: []
    }
  },
  created() {
    this.loadCategories()
    this.loadData()
  },
  methods: {
    async loadCategories() {
      const res = await getCategoryList()
      this.categories = res.data
    },
    async loadData() {
      // 课程示例：使用 Axios GET 请求获取数据
      const res = await getGoodsList(this.query)
      this.list = res.data.list
      this.total = res.data.total
    },
    search() {
      this.query.pageNum = 1
      this.loadData()
    },
    reset() {
      this.query = { pageNum: 1, pageSize: 8, type: '', categoryId: null, keyword: '', auditStatus: 1 }
      this.loadData()
    },
    goDetail(id) {
      this.$router.push('/detail/' + id)
    }
  }
}
</script>
