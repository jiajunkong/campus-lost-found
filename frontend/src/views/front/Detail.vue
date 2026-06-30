<template>
  <div class="container">
    <el-card v-if="info">
      <div style="display:flex;">
        <el-image :src="getImageUrl(info.image) || 'https://via.placeholder.com/360x240?text=No+Image'"
                  style="width:360px;height:240px;" fit="cover" />
        <div style="flex:1;margin-left:24px;">
          <h2 style="margin:0;">
            <el-tag :type="info.type === 'lost' ? 'danger' : 'success'">
              {{ info.type === 'lost' ? '寻物启事' : '招领启事' }}
            </el-tag>
            {{ info.title }}
          </h2>
          <p>分类：{{ info.categoryName }}</p>
          <p>地点：{{ info.place }}</p>
          <p>时间：{{ info.lostTime }}</p>
          <p>发布人：{{ info.nickname || info.username }}</p>
          <p>发布时间：{{ info.createTime }}</p>
          <el-divider />
          <p><b>描述：</b>{{ info.description }}</p>
        </div>
      </div>
    </el-card>

    <el-card style="margin-top:20px;">
      <div slot="header"><b>留言区</b></div>
      <el-form v-if="user" :inline="true">
        <el-form-item>
          <el-input v-model="newComment" placeholder="说点什么..." style="width:500px;" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitComment">发送留言</el-button>
        </el-form-item>
      </el-form>
      <div v-else style="color:#909399;">请<el-link type="primary" @click="$router.push('/login')">登录</el-link>后留言</div>

      <el-table :data="comments" border style="margin-top:14px;">
        <el-table-column label="留言人" prop="nickname" width="180" />
        <el-table-column label="留言内容" prop="content" />
        <el-table-column label="留言时间" prop="createTime" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getGoodsDetail, getCommentList, addComment } from '@/api'
import { getImageUrl } from '@/api/request'
export default {
  data() {
    return {
      info: null,
      comments: [],
      newComment: ''
    }
  },
  computed: {
    user() { return this.$store.state.user }
  },
  created() {
    this.load()
  },
  methods: {
    async load() {
      const id = this.$route.params.id
      const r1 = await getGoodsDetail(id)
      this.info = r1.data
      const r2 = await getCommentList(id)
      this.comments = r2.data
    },
    async submitComment() {
      if (!this.newComment.trim()) {
        return this.$message.warning('请输入留言内容')
      }
      await addComment({
        goodsId: this.info.id,
        userId: this.user.id,
        content: this.newComment
      })
      this.$message.success('留言成功')
      this.newComment = ''
      this.load()
    }
  }
}
</script>
