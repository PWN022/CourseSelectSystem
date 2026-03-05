import {defineStore} from 'pinia'
import request from '@/utils/request'
// import { setToken, removeToken } from '@/utils/auth'

// 安全地从localStorage获取数据的函数
const safeGetItem = (key, defaultValue = null) => {
  try {
    const item = localStorage.getItem(key)
    if (!item) return defaultValue
    return JSON.parse(item)
  } catch (e) {
    console.error(`Error parsing localStorage item: ${key}`, e)
    return defaultValue
  }
}

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: safeGetItem('userInfo'),
    token: localStorage.getItem('token') || '',
    menus: safeGetItem('menus', []),
    teacherInfo: safeGetItem('teacherInfo'),
    studentInfo: safeGetItem('studentInfo')
  }),

  getters: {
    // 判断是否登录
    isLoggedIn: (state) => !!state.token,
    // 获取用户角色
    role: (state) => state.userInfo?.roleCode || '',
    // 判断是否是管理员
    isAdmin: (state) => state.userInfo?.roleCode === 'ADMIN',
    // 判断是否是普通用户
    isTeacher: (state) => state.userInfo?.roleCode === 'TEACHER',
    isStudent: (state) => state.userInfo?.roleCode === 'STUDENT'
  },

  actions: {
    // 更新用户信息
    updateUserInfo(data) {
      if (!data) return
      this.userInfo = data
      localStorage.setItem('userInfo', JSON.stringify(data))
    },
    
    setUserInfo(data) {
      if (!data) return

      this.userInfo = data.userInfo || data
      this.token = data.token

      // 存储到 LocalStorage
      try {
        localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
        localStorage.setItem('token', this.token || '')
        // role不需要单独存储，因为它是从userInfo.roleCode派生的
      } catch (e) {
        console.error('Error saving user info to localStorage', e)
      }

      // If user is a teacher, fetch teacher information
      if (this.userInfo?.roleCode === 'TEACHER' && this.userInfo?.id) {
        this.fetchTeacherInfo(this.userInfo.id)
      }

      // If user is a student, fetch student information
      if (this.userInfo?.roleCode === 'STUDENT' && this.userInfo?.id) {
        this.fetchStudentInfo(this.userInfo.id)
      }
    },
    clearUserInfo() {
      this.userInfo = null
      this.token = ''
      this.menus = []
      this.teacherInfo = null
      this.studentInfo = null
      // 清除 LocalStorage
      try {
        localStorage.removeItem('userInfo')
        localStorage.removeItem('token')
        localStorage.removeItem('role') // 保留这个清除操作，以防之前存储过
        localStorage.removeItem('menus')
        localStorage.removeItem('teacherInfo')
        localStorage.removeItem('studentInfo')
        if (typeof window !== 'undefined') {
          window.isRoutesInitialized = false
        }
      } catch (e) {
        console.error('Error clearing localStorage', e)
        // 如果发生错误，尝试清除所有localStorage
        localStorage.clear()
      }
      // 移除动态路由相关逻辑
    },
    setMenus(menus) {
      if (!menus) return
      this.menus = menus
      try {
        localStorage.setItem('menus', JSON.stringify(menus))
      } catch (e) {
        console.error('Error saving menus to localStorage', e)
      }
    },
    // 获取用户信息和菜单 - 从localStorage恢复
    async getUserInfo() {
      try {
        const userInfo = safeGetItem('userInfo')
        const token = localStorage.getItem('token')
        const menus = safeGetItem('menus', [])
        const teacherInfo = safeGetItem('teacherInfo')
        const studentInfo = safeGetItem('studentInfo')

        if (userInfo && token) {
          this.userInfo = userInfo
          this.token = token
          this.menus = menus || []

          // 从userInfo中获取role，而不是从localStorage
          const role = userInfo.roleCode

          // 如果是教师，恢复教师信息
          if (role === 'TEACHER' && teacherInfo) {
            this.teacherInfo = teacherInfo
          }

          // 如果是学生，恢复学生信息
          if (role === 'STUDENT' && studentInfo) {
            this.studentInfo = studentInfo
          }

          return {
            userInfo,
            token,
            role,
            menus: this.menus
          }
        }
      } catch (e) {
        console.error('Error getting user info from localStorage', e)
      }
      
      // 如果没有缓存的数据或发生错误，清除状态并抛出错误
      this.clearUserInfo()
      throw new Error('No cached user info')
    },
    // 登录
    async login(loginForm) {
      try {
        const res = await request.post('/user/login', loginForm)
        this.setUserInfo(res)
        return res
      } catch (error) {
        this.clearUserInfo()
        throw error
      }
    },
    // 注册

    // 退出登录
    async logout() {
      this.clearUserInfo()
    },
    // 检查登录状态
    checkLoginStatus() {
      return !!this.token
    },
    // Add method to fetch teacher information
    async fetchTeacherInfo(userId) {
      try {
        await request.get(`/teacher/userId/${userId}`, {}, {
          onSuccess: (res) => {
            this.teacherInfo = res
            console.log("教师信息", this.teacherInfo)
            try {
              localStorage.setItem('teacherInfo', JSON.stringify(res))
            } catch (e) {
              console.error('Error saving teacher info to localStorage', e)
            }
          }
        })
      } catch (error) {
        console.error('Failed to fetch teacher information:', error)
        throw error
      }
    },
    // Add method to fetch student information
    async fetchStudentInfo(userId) {
      try {
        await request.get(`/student/userId/${userId}`, {}, {
          onSuccess: (res) => {
            this.studentInfo = res
            console.log("学生信息", this.studentInfo)
            try {
              localStorage.setItem('studentInfo', JSON.stringify(res))
            } catch (e) {
              console.error('Error saving student info to localStorage', e)
            }
          }
        })
      } catch (error) {
        console.error('Failed to fetch student information:', error)
        throw error
      }
    }
  }
})