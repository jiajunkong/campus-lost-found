import request from './request'

// 文件上传URL（开发环境走代理，生产环境使用完整地址）
const isProd = process.env.NODE_ENV === 'production'
const baseApi = isProd ? (process.env.VUE_APP_BASE_API || '') : '/api'
export const UPLOAD_URL = baseApi + '/file/upload'

/* ============== 用户 ============== */
export const login = (data) => request.post('/user/login', data)
export const register = (data) => request.post('/user/register', data)
export const getUserInfo = (id) => request.get(`/user/info/${id}`)
export const updateUser = (data) => request.put('/user/update', data)
export const updatePassword = (data) => request.post('/user/updatePassword', data)
export const getUserList = (params) => request.get('/user/list', { params })
export const updateUserStatus = (data) => request.post('/user/updateStatus', data)

/* ============== 分类 ============== */
export const getCategoryList = () => request.get('/category/list')
export const saveCategory = (data) => request.post('/category/save', data)
export const deleteCategory = (id) => request.delete(`/category/delete/${id}`)

/* ============== 失物 ============== */
export const getGoodsList = (params) => request.get('/goods/list', { params })
export const getGoodsDetail = (id) => request.get(`/goods/detail/${id}`)
export const saveGoods = (data) => request.post('/goods/save', data)
export const auditGoods = (data) => request.post('/goods/audit', data)
export const deleteGoods = (id) => request.delete(`/goods/delete/${id}`)

/* ============== 留言 ============== */
export const getCommentList = (goodsId) => request.get(`/comment/list/${goodsId}`)
export const addComment = (data) => request.post('/comment/add', data)
export const deleteComment = (id) => request.delete(`/comment/delete/${id}`)

/* ============== 公告 ============== */
export const getNoticeList = () => request.get('/notice/list')
export const getNoticeDetail = (id) => request.get(`/notice/detail/${id}`)
export const saveNotice = (data) => request.post('/notice/save', data)
export const deleteNotice = (id) => request.delete(`/notice/delete/${id}`)

/* ============== 数据统计 ============== */
export const getStatOverview = () => request.get('/stat/overview')
export const getStatByType = () => request.get('/stat/byType')
export const getStatByCategory = () => request.get('/stat/byCategory')
