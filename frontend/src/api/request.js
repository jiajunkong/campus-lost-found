import axios from 'axios'
import { Message } from 'element-ui'

// 全局后端地址
// 开发环境使用 /api 走 Vue 开发服务器代理
// 生产环境使用 VUE_APP_BASE_API 环境变量指向真实后端地址
const isProd = process.env.NODE_ENV === 'production'
const baseURL = isProd ? (process.env.VUE_APP_BASE_API || '') : '/api'

const service = axios.create({
  baseURL,
  timeout: 10000
})

// 请求拦截器
service.interceptors.request.use(config => {
  // 携带登录用户信息（可选）
  const user = JSON.parse(localStorage.getItem('user') || 'null')
  if (user) {
    config.headers['userId'] = user.id
    config.headers['role'] = user.role
  }
  return config
})

// 响应拦截器
service.interceptors.response.use(
  res => {
    const data = res.data
    if (data.code !== undefined && data.code !== 200) {
      Message.error(data.msg || '请求失败')
      return Promise.reject(data)
    }
    return data
  },
  err => {
    Message.error('网络异常，请稍后重试')
    return Promise.reject(err)
  }
)
export default service
