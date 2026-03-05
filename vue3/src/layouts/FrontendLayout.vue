<template>
    <div class="frontend-layout">
      <!-- 顶部导航栏 -->
      <header class="header">
        <div class="logo">
          <router-link to="/">选课系统</router-link>
        </div>
        <nav>
          <router-link to="/">首页</router-link>
          
          <!-- 根据角色显示不同的导航链接 -->
          <template v-if="isLoggedIn">
            <!-- 管理员和教师可以进入后台 -->
            <router-link to="/back/dashboard" v-if="['ADMIN', 'TEACHER'].includes(userStore.role)">
              进入后台
            </router-link>
            
            <!-- 学生可以查看自己的课程和成绩 -->
            <template v-if="userStore.role === 'STUDENT'">
              <router-link to="/courses">我的课程</router-link>
              <router-link to="/scores">我的成绩</router-link>
            </template>
            
            <router-link to="/profile">个人中心</router-link>
            <button @click="handleLogout">退出登录</button>
          </template>
          <template v-else>
            <router-link to="/login">登录</router-link>
            <router-link to="/register">注册</router-link>
          </template>
        </nav>
      </header>
  
      <!-- 主要内容区域 -->
      <main class="main-content">
        <router-view />
      </main>
  
      <!-- 页脚 -->
      <footer class="footer">
        <p>&copy; 2026 选课系统</p>
      </footer>
    </div>
  </template>
  
  <script setup>
  import { computed } from 'vue'
  import { useUserStore } from '@/store/user'
  import { useRouter } from 'vue-router'
  
  const userStore = useUserStore()
  const router = useRouter()
  
  const isLoggedIn = computed(() => !!userStore.token)
  
  const handleLogout = () => {
    userStore.clearUserInfo()
    router.push('/login')
  }
  </script>
  
  <style scoped>
  .frontend-layout {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }
  
  .header {
    background-color: #333;
    color: white;
    padding: 1rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .logo a {
    color: white;
    font-size: 1.5rem;
    font-weight: bold;
    text-decoration: none;
  }
  
  .header nav {
    display: flex;
    gap: 1rem;
    align-items: center;
  }
  
  .header a {
    color: white;
    text-decoration: none;
  }
  
  .header button {
    background: none;
    border: 1px solid white;
    color: white;
    padding: 0.25rem 0.75rem;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .header button:hover {
    background-color: rgba(255, 255, 255, 0.1);
  }
  
  .main-content {
    flex: 1;
    padding: 1rem;
  }
  
  .footer {
    background-color: #333;
    color: white;
    text-align: center;
    padding: 1rem;
  }
  </style>