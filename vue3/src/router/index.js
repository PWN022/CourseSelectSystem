import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'

// 静态路由（合并原 constantRoutes、backendRoutes、frontendRoutes，全部静态声明）
const routes = [
  {
    path: '/login',
    component: () => import('@/views/auth/Login.vue'),
    meta: { title: '登录', requiresAuth: false }
  },
  {
    path: '/register',
    component: () => import('@/views/auth/Register.vue'),
    meta: { title: '注册', requiresAuth: false }
  },
  {
    path: '/',
    redirect: '/login',
    meta: { title: '首页', requiresAuth: false }
  },
  {
    path: '/403',
    component: () => import('@/views/error/403.vue'),
    meta: { title: '无权限访问', requiresAuth: false }
  },
  {
    path: '/404',
    component: () => import('@/views/error/404.vue'),
    meta: { title: '页面不存在', requiresAuth: false }
  },
  // 后台管理路由
  {
    path: '/back',
    component: () => import('@/layouts/BackendLayout.vue'),
    redirect: '/back/dashboard',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/backend/Dashboard.vue'),
        meta: { title: '控制台', icon: 'el-icon-menu', requiresAuth: false }
      },
      {
        path: 'user',
        component: () => import('@/views/backend/user/index.vue'),
        meta: { title: '用户管理', icon: 'el-icon-user', requiresAuth: true, roles: ['ADMIN'] }
      },
      {
        path: 'profile',
        component: () => import('@/views/backend/user/PersonInfo.vue'),
        meta: { title: '个人信息', icon: 'el-icon-setting', requiresAuth: true }
      },
      // 学生管理路由
      {
        path: 'student',
        redirect: '/back/student/list',
        meta: { title: '学生管理', icon: 'el-icon-user', requiresAuth: true, roles: ['ADMIN', 'TEACHER'] },
        children: [
          {
            path: 'list',
            component: () => import('@/views/backend/student/StudentList.vue'),
            meta: { title: '学生列表', requiresAuth: true, roles: ['ADMIN', 'TEACHER'] }
          },
          {
            path: 'class',
            component: () => import('@/views/backend/student/ClassList.vue'),
            meta: { title: '班级管理', requiresAuth: true, roles: ['ADMIN'] }
          }
        ]
      },
      // 教师管理路由
      {
        path: 'teacher',
        redirect: '/back/teacher/list',
        meta: { title: '教师管理', icon: 'el-icon-user', requiresAuth: true, roles: ['ADMIN'] },
        children: [
          {
            path: 'list',
            component: () => import('@/views/backend/teacher/TeacherList.vue'),
            meta: { title: '教师列表', requiresAuth: true, roles: ['ADMIN'] }
          }
        ]
      },
      // 课程管理路由
      {
        path: 'course',
        redirect: '/back/course/list',
        meta: { title: '课程管理', icon: 'el-icon-reading', requiresAuth: true },
        children: [
          {
            path: 'list',
            component: () => import('@/views/backend/course/CourseList.vue'),
            meta: { title: '课程列表', requiresAuth: true, roles: ['ADMIN', 'TEACHER', 'STUDENT'] }
          },
          {
            path: 'teacher-course',
            component: () => import('@/views/backend/course/TeacherCourseList.vue'),
            meta: { title: '教师课程管理', requiresAuth: true, roles: ['ADMIN', 'TEACHER'] }
          },
          {
            path: 'student-course',
            component: () => import('@/views/backend/course/StudentCourseList.vue'),
            meta: { title: '学生选课管理', requiresAuth: true, roles: ['ADMIN', 'STUDENT'] }
          },
          {
            path: 'my-courses',
            component: () => import('@/views/backend/student/MyCourses.vue'),
            meta: { title: '我的课程', requiresAuth: true, roles: ['STUDENT'] }
          },
          {
            path: 'application',
            component: () => import('@/views/backend/student/CourseApplication.vue'),
            meta: { title: '选课申请', requiresAuth: true, roles: ['STUDENT'] }
          },
          {
            path: 'approval',
            component: () => import('@/views/backend/course/CourseApproval.vue'),
            meta: { title: '选课审批', requiresAuth: true, roles: ['ADMIN', 'TEACHER'] }
          }
        ]
      },
      // 成绩管理路由
      {
        path: 'score',
        redirect: '/back/score/list',
        meta: { title: '成绩管理', icon: 'el-icon-document', requiresAuth: true },
        children: [
          {
            path: 'list',
            component: () => import('@/views/backend/score/ScoreList.vue'),
            meta: { title: '成绩列表', requiresAuth: true, roles: ['ADMIN', 'TEACHER'] }
          },
          {
            path: 'analysis',
            component: () => import('@/views/backend/score/ScoreAnalysis.vue'),
            meta: { title: '成绩分析', requiresAuth: true, roles: ['ADMIN', 'TEACHER'] }
          },
          {
            path: 'my-scores',
            component: () => import('@/views/backend/student/MyScores.vue'),
            meta: { title: '我的成绩', requiresAuth: true, roles: ['STUDENT'] }
          }
        ]
      },
      // 考勤管理路由
      {
        path: 'attendance',
        redirect: '/back/attendance/list',
        meta: { title: '考勤管理', icon: 'el-icon-calendar', requiresAuth: true },
        children: [
          {
            path: 'list',
            component: () => import('@/views/backend/attendance/AttendanceList.vue'),
            meta: { title: '考勤记录', requiresAuth: true, roles: ['ADMIN', 'TEACHER'] }
          },
          {
            path: 'statistics',
            component: () => import('@/views/backend/attendance/AttendanceStatistics.vue'),
            meta: { title: '考勤统计', requiresAuth: true, roles: ['ADMIN', 'TEACHER'] }
          },
          {
            path: 'my-attendance',
            component: () => import('@/views/backend/student/MyAttendance.vue'),
            meta: { title: '我的考勤', requiresAuth: true, roles: ['STUDENT'] }
          }
        ]
      }
    ]
  },
  // 前台路由
  {
    path: '/',
    component: () => import('@/layouts/FrontendLayout.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@/views/frontend/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/profile/index.vue'),
        meta: { title: '个人中心', requiresAuth: true }
      }
    ]
  },
  // 404 匹配
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫（保留权限判断和登录判断）
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userStore = useUserStore()
  document.title = to.meta.title || '高校课程管理系统'

  // 不需要登录的页面直接访问
  if (!to.meta.requiresAuth) {
    next()
    return
  }

  // 需要登录但未登录，跳转到登录页
  if (!token) {
    next({ path: '/login', query: { redirect: to.fullPath } })
    return
  }

  // 已登录，判断是否有权限访问
  if (to.meta.roles && !to.meta.roles.includes(userStore.role)) {
    next({ path: '/403' })
    return
  }

  next()
})

export default router
