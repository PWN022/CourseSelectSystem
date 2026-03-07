<template>
  <div class="user-management">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
        </div>
      </template>
      
      <el-tabs v-model="activeTab">
        <el-tab-pane label="用户列表" name="users">
          <UserList />
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import {computed, ref} from 'vue'
import {useUserStore} from '@/store/user'
import UserList from './UserList.vue'

const userStore = useUserStore()
const activeTab = ref('users')

// 判断是否有角色管理权限
computed(() => {
  return userStore.role === 'ADMIN'
});
</script>

<style lang="scss" scoped>
.user-management {
  padding: 20px 0;
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .box-card {
    margin-bottom: 20px;
  }
}
</style> 