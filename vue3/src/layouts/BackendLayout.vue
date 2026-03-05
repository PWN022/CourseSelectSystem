<template>
  <div class="backend-layout">
    <!-- 使用侧边栏组件 -->
    <Sidebar />
    
    <!-- 主内容区 -->
    <div class="main-container">
      <!-- 顶部导航 -->
      <div class="header">
        <div class="left">
          <el-button type="text" @click="toggleSidebar">
            <el-icon :size="20">
              <el-icon-fold v-if="!isCollapsed" />
              <el-icon-expand v-else />
            </el-icon>
          </el-button>
          <breadcrumb />
        </div>
        
        <div class="right">
          <el-dropdown trigger="click" @command="handleCommand">
            <div class="avatar-container">
              <el-avatar :size="30" :src="userAvatar"></el-avatar>
              <span class="username">{{ userStore.userInfo?.name || userStore.userInfo?.username }}</span>
              <el-icon><el-icon-arrow-down /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      
      <!-- 内容区 -->
      <div class="content">
        <router-view v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, h } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { useAppStore } from '@/store/app'
import { ElMessageBox } from 'element-plus'
import Sidebar from '@/components/backend/Sidebar.vue'
import { 
  Fold as ElIconFold,
  Expand as ElIconExpand,
  ArrowDown as ElIconArrowDown,
} from '@element-plus/icons-vue'

// 组件占位，实际项目中需要实现
const Breadcrumb = {
  render() {
    return h('div', { class: 'breadcrumb' }, '当前位置')
  }
}

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const appStore = useAppStore()

// 计算属性
const isCollapsed = computed(() => appStore.sidebarCollapsed)

// 用户头像
const baseAPI = import.meta.env.VITE_BASE_API || '/api'
const userAvatar = computed(() => {
  return userStore.userInfo?.avatar ? baseAPI + userStore.userInfo.avatar : '';
})

// 切换侧边栏
const toggleSidebar = () => {
  appStore.toggleSidebar()
}

// 下拉菜单命令处理
const handleCommand = (command) => {
  if (command === 'profile') {
    router.push('/back/profile')
  } else if (command === 'logout') {
    ElMessageBox.confirm(
      '确定要退出登录吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    ).then(async () => {
      await userStore.logout()
      router.push('/login')
    }).catch(() => {})
  }
}
</script>

<style lang="scss" scoped>
.backend-layout {
  display: flex;
  height: 100vh;
  
  .main-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    
    .header {
      height: 60px;
      background-color: #fff;
      box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 20px;
      
      .left {
        display: flex;
        align-items: center;
        
        .breadcrumb {
          margin-left: 20px;
        }
      }
      
      .right {
        .avatar-container {
          display: flex;
          align-items: center;
          cursor: pointer;
          
          .username {
            margin: 0 10px;
            color: #606266;
          }
        }
      }
    }
    
    .content {
      flex: 1;
      padding: 20px;
      overflow-y: auto;
      background-color: #f0f2f5;
    }
  }
}


</style>