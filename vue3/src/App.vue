<template>
  <router-view />
</template>

<script setup>
import { onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { useRouter } from 'vue-router'


const userStore = useUserStore()
const router = useRouter()

onMounted(async () => {
  // 如果已登录，尝试恢复用户状态
  if (userStore.isLoggedIn) {
    try {
      const { userInfo } = await userStore.getUserInfo()
      
      // 根据角色加载不同的路由
      const role = userInfo.roleCode
      
      // 管理员和教师可以访问后台路由
      if (['ADMIN', 'TEACHER', 'STUDENT'].includes(role)) {
       
      }
      
      // 如果当前路由是登录页，则重定向到合适的页面
      if (router.currentRoute.value.path === '/login') {
        if (['ADMIN', 'TEACHER'].includes(role)) {
          router.push('/back/dashboard')
        } else if (role === 'STUDENT') {
          router.push('/profile')
        } else {
          router.push('/')
        }
      }
    } catch (error) {
      console.error('Failed to restore user session:', error)
      userStore.clearUserInfo()
    }
  }
})
</script>

<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
}

#app {
  height: 100%;
}
</style>
