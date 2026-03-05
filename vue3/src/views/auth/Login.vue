<template>
  <Auth 
    :formData="loginForm" 
    :rules="rules" 
    :loading="loading"
    submitText="登录系统"
    @submit="handleSubmit"
  >
    <template #form-items>
      <el-form-item prop="username">
        <el-input 
          v-model="loginForm.username"
          :prefix-icon="User"
          placeholder="请输入账号">
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input 
          v-model="loginForm.password"
          :prefix-icon="Lock"
          type="password"
          placeholder="请输入密码">
        </el-input>
      </el-form-item>
    </template>

    <template #auth-links>
      <div class="auth-links-container">
   
        <router-link to="/register" class="register-link">注册账号</router-link>
      </div>
    </template>
  </Auth>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {useUserStore} from '@/store/user'
import request from '@/utils/request'
import {Lock, User} from '@element-plus/icons-vue'

import Auth from './Auth.vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const formRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// 在组件挂载时检查store是否正确初始化
onMounted(() => {
  // 确保store正确初始化，清除可能存在的缓存问题
  if (!userStore.setUserInfo || typeof userStore.setUserInfo !== 'function') {
    console.warn('UserStore 初始化异常，正在尝试修复...')

    // 检查是否已经尝试过修复，避免无限循环
    const fixAttempted = sessionStorage.getItem('store-fix-attempted')
    if (fixAttempted) {
      console.error('Store修复失败，请刷新页面或联系管理员')
      return
    }

    // 标记已尝试修复
    sessionStorage.setItem('store-fix-attempted', 'true')

    // 清除localStorage中可能导致问题的数据
    localStorage.removeItem('userInfo')
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    localStorage.removeItem('menus')
    localStorage.removeItem('teacherInfo')
    localStorage.removeItem('studentInfo')

    // 延迟重新加载，避免立即循环
    setTimeout(() => {
      location.reload()
    }, 1000)
  } else {
    // Store正常，清除修复标记
    sessionStorage.removeItem('store-fix-attempted')
  }
})

const handleSubmit = (form) => {
  formRef.value = form.value
  loginFormRef.value = form.value
  handleLogin()
}

const loginFormRef = ref(null)

const handleLogin = () => {
  loginFormRef.value.validate(async valid => {
    if (valid) {
      loading.value = true
      try {
        // 登录前先清除可能存在的缓存问题
        userStore.clearUserInfo()
        
        // 统一使用用户登录接口
        await request.post("/user/login", loginForm, {
          successMsg: "登录成功",
          showDefaultMsg: true,
          onSuccess: async (data) => {
            // 确保userStore.setUserInfo是一个函数
            if (typeof userStore.setUserInfo === 'function') {
              userStore.setUserInfo(data)
           
              // await router.isReady()
              
              // 等待教师信息加载完成（如果是教师）
              if (data.roleCode === 'TEACHER') {
                try {
                  await userStore.fetchTeacherInfo(data.id)
                } catch (teacherError) {
                  console.warn('获取教师信息失败，但不影响登录:', teacherError)
                }
              }

              // 等待学生信息加载完成（如果是学生）
              if (data.roleCode === 'STUDENT') {
                try {
                  await userStore.fetchStudentInfo(data.id)
                } catch (studentError) {
                  console.warn('获取学生信息失败，但不影响登录:', studentError)
                }
              }
              
              router.push(route.query.redirect || '/back/dashboard')
            } else {
              console.error('userStore.setUserInfo不是一个函数，正在尝试修复...')
              localStorage.clear()
              location.reload()
            }
          },
          onError: (error) => {
            console.error('登录失败:', error)
          }
        })
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.auth-links-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  
  a {
    color: #64748b;
    transition: color 0.2s;
    
    &:hover {
      color: #3b82f6;
    }
  }
}
</style> 