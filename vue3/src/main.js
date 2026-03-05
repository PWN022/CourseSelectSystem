import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
// 导入 Element Plus
import ElementPlus from 'element-plus'
// 导入自定义主题色配置
import './styles/element-theme.scss'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 导入初始化样式
import './assets/global.css'
// 中文
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import 'element-plus/dist/index.css'

// 检查localStorage中的store数据是否有效
const checkLocalStorageValidity = () => {
  try {
    const userInfo = localStorage.getItem('userInfo')
    // 如果localStorage中有数据但格式不正确，清除所有缓存
    if (userInfo && typeof JSON.parse(userInfo) !== 'object') {
      console.warn('检测到无效的localStorage数据，正在清除...')
      localStorage.clear()
    }
  } catch (e) {
    console.error('localStorage数据解析错误，正在清除所有缓存', e)
    localStorage.clear()
  }
}

// 应用启动时检查本地存储
checkLocalStorageValidity()

// 添加全局错误处理器来抑制ResizeObserver警告
const originalConsoleError = console.error
console.error = (...args) => {
  if (args[0] && args[0].includes && args[0].includes('ResizeObserver loop')) {
    return
  }
  originalConsoleError(...args)
}

const app = createApp(App)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(`ElIcon${key}`, component)
}

const pinia = createPinia()

app.use(pinia)
app.use(router)
app.use(ElementPlus, {
  locale: zhCn,
  size: 'default'
})

// 全局错误处理
app.config.errorHandler = (err, vm, info) => {
  console.error('Vue Error:', err, info)
  // 如果出现store方法未定义的错误，尝试修复
  if (err.toString().includes('is not a function') && 
      err.toString().includes('Store')) {
    console.warn('检测到Store相关错误，尝试修复...')
    localStorage.clear()
    location.reload()
  }
}

app.mount('#app')
