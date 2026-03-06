<template>
  <div class="sidebar-container" :class="{ 'is-collapsed': isCollapsed }">
    <div class="logo">
      <img src="@/assets/logo.png" class="logo-img" alt="Logo" />
      <span class="logo-text" v-show="!isCollapsed">高校课程管理系统</span>
    </div>
    <div class="menu-wrapper">
      <el-menu :default-active="activeMenu" :collapse="isCollapsed" :collapse-transition="false" mode="vertical" class="sidebar-menu"
               :text-color="menuTextColor" :active-text-color="activeTextColor" :background-color="menuBgColor" router>

        <!-- 固定菜单项 -->
        <el-menu-item index="/back/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <template #title>控制台</template>
        </el-menu-item>

        <!-- 管理员专属菜单 -->
        <el-menu-item index="/back/user" v-if="userStore.role === 'ADMIN'">
          <el-icon><User /></el-icon>
          <template #title>用户管理</template>
        </el-menu-item>

        <!-- 学生管理菜单 - 仅管理员和教师可见 -->
        <el-sub-menu index="/back/student" v-if="['ADMIN', 'TEACHER'].includes(userStore.role)">
          <template #title>
            <el-icon><Reading /></el-icon>
            <span>学生管理</span>
          </template>

          <el-menu-item index="/back/student/list">
            <el-icon><User /></el-icon>
            <template #title>学生列表</template>
          </el-menu-item>

          <!-- 班级管理仅管理员可见 -->
          <el-menu-item index="/back/student/class" v-if="userStore.isAdmin">
            <el-icon><OfficeBuilding /></el-icon>
            <template #title>班级管理</template>
          </el-menu-item>
        </el-sub-menu>

        <!-- 教师管理菜单 - 仅管理员可见 -->
        <el-sub-menu index="/back/teacher" v-if="userStore.role === 'ADMIN'">
          <template #title>
            <el-icon><Suitcase /></el-icon>
            <span>教师管理</span>
          </template>

          <el-menu-item index="/back/teacher/list">
            <el-icon><User /></el-icon>
            <template #title>教师列表</template>
          </el-menu-item>
        </el-sub-menu>

        <!-- 课程管理菜单 - 所有角色可见，但子菜单有权限控制 -->
        <el-sub-menu index="/back/course">
          <template #title>
            <el-icon><Reading /></el-icon>
            <span>课程管理</span>
          </template>

          <!-- 所有角色都可以查看课程列表 -->
          <el-menu-item index="/back/course/list" v-if="userStore.isAdmin || userStore.isTeacher">
            <el-icon><Collection /></el-icon>
            <template #title>课程列表</template>
          </el-menu-item>

          <!-- 教师课程管理仅管理员和教师可见 -->
          <el-menu-item index="/back/course/teacher-course" v-if="userStore.isAdmin || userStore.isTeacher">
            <el-icon><Suitcase /></el-icon>
            <template #title>教师课程管理</template>
          </el-menu-item>

          <!-- 学生选课管理仅管理员和学生可见 -->
          <el-menu-item index="/back/course/student-course" v-if="userStore.isAdmin">
            <el-icon><User /></el-icon>
            <template #title>学生选课管理</template>
          </el-menu-item>

          <!-- 选课申请仅学生可见 -->
          <el-menu-item index="/back/course/application" v-if="userStore.isStudent">
            <el-icon><Edit /></el-icon>
            <template #title>选课申请</template>
          </el-menu-item>

          <!-- 选课审批仅管理员和教师可见 -->
          <el-menu-item index="/back/course/approval" v-if="userStore.isAdmin || userStore.isTeacher">
            <el-icon><Check /></el-icon>
            <template #title>选课审批</template>
          </el-menu-item>

          <!-- 我的课程仅学生可见 -->
          <el-menu-item index="/back/course/my-courses" v-if="userStore.isStudent">
            <el-icon><Collection /></el-icon>
            <template #title>我的课程</template>
          </el-menu-item>
        </el-sub-menu>

        <!-- 成绩管理菜单 -->
        <el-sub-menu index="/back/score">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>成绩管理</span>
          </template>

          <!-- 所有角色都可以查看成绩列表 -->
          <el-menu-item index="/back/score/list" v-if="userStore.isAdmin || userStore.isTeacher">
            <el-icon><List /></el-icon>
            <template #title>成绩列表</template>
          </el-menu-item>

          <!-- 成绩分析仅管理员和教师可见 -->
          <el-menu-item index="/back/score/analysis" v-if="['ADMIN', 'TEACHER'].includes(userStore.role)">
            <el-icon><PieChart /></el-icon>
            <template #title>成绩分析</template>
          </el-menu-item>

          <!-- 我的成绩仅学生可见 -->
          <el-menu-item index="/back/score/my-scores" v-if="userStore.isStudent">
            <el-icon><Document /></el-icon>
            <template #title>我的成绩</template>
          </el-menu-item>
        </el-sub-menu>

        <!-- 考勤管理菜单 -->
        <el-sub-menu index="/back/attendance">
          <template #title>
            <el-icon><Calendar /></el-icon>
            <span>考勤管理</span>
          </template>

          <!-- 所有角色都可以查看考勤记录 -->
          <el-menu-item index="/back/attendance/list" v-if="userStore.isAdmin || userStore.isTeacher">
            <el-icon><List /></el-icon>
            <template #title>考勤记录</template>
          </el-menu-item>

          <!-- 考勤统计仅管理员和教师可见 -->
          <el-menu-item index="/back/attendance/statistics" v-if="userStore.isAdmin || userStore.isTeacher">
            <el-icon><PieChart /></el-icon>
            <template #title>考勤统计</template>
          </el-menu-item>

          <!-- 我的考勤仅学生可见 -->
          <el-menu-item index="/back/attendance/my-attendance" v-if="userStore.isStudent">
            <el-icon><Calendar /></el-icon>
            <template #title>我的考勤</template>
          </el-menu-item>
        </el-sub-menu>

        <!-- 个人信息菜单 - 所有角色可见 -->
        <el-menu-item index="/back/profile">
          <el-icon><UserFilled /></el-icon>
          <template #title>个人信息</template>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '@/store/app'
import { useUserStore } from '@/store/user'
import {
  HomeFilled,
  User,
  UserFilled,
  Reading,
  DataAnalysis,
  OfficeBuilding,
  Suitcase,
  Collection,
  Document,
  List,
  PieChart,
  Calendar,
  Edit,
  Check
} from '@element-plus/icons-vue'

const route = useRoute()
const appStore = useAppStore()
const userStore = useUserStore()

const isCollapsed = computed(() => appStore.sidebarCollapsed)

// 当前激活的菜单
const activeMenu = computed(() => {
  const { meta, path } = route
  if (meta.activeMenu) {
    return meta.activeMenu
  }
  return path
})

// 主题色配置
const menuBgColor = '#ffffff'  // 白色背景
const menuTextColor = '#333333'  // 深灰色文字
const activeTextColor = '#1890ff'  // 蓝色激活
</script>

<style lang="scss" scoped>
.sidebar-container {
  height: 100%;
  min-height: 100vh;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  width: 220px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 2px 0 8px 0 rgba(0, 0, 0, 0.05);

  &.is-collapsed {
    width: 64px;

    .logo {
      padding: 0;
      justify-content: center;

      .logo-img {
        margin-right: 0;
        width: 32px;
        height: auto;
      }
    }

    :deep(.el-menu) {
      .el-sub-menu__title span,
      .el-menu-item span {
        opacity: 0;
        transition: opacity 0.2s;
      }
    }
  }

  .logo {
    height: 61px;
    flex-shrink: 0;
    line-height: 70px;
    text-align: center;
    background: #ffffff;
    border-bottom: 1px solid #f0f2f5;
    display: flex;
    align-items: center;
    padding: 0 16px;
    overflow: hidden;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);

    .logo-img {
      width: 24px;
      height: 24px;
      margin-right: 10px;
      border-radius: 4px;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
      object-fit: cover;
    }

    .logo-text {
      color: #1890ff;
      font-size: 18px;
      font-weight: 600;
      white-space: nowrap;
      opacity: 1;
      transition: opacity 0.2s;
      letter-spacing: 1px;
    }
  }

  .menu-wrapper {
    flex: 1;
    overflow-y: auto;
    overflow-x: hidden;
    background: #ffffff;

    &::-webkit-scrollbar {
      width: 4px;
    }

    &::-webkit-scrollbar-thumb {
      background: #d9d9d9;
      border-radius: 2px;
    }

    &::-webkit-scrollbar-track {
      background: #f5f5f5;
    }
  }

  :deep(.sidebar-menu) {
    border: none;
    background: #ffffff;
    transition: width 0.3s cubic-bezier(0.4, 0, 0.2, 1);

    .el-menu-item, .el-sub-menu__title {
      height: 48px;
      line-height: 48px;
      color: #333333;
      background: #ffffff;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
      font-size: 14px;

      span {
        opacity: 1;
        transition: opacity 0.3s;
      }

      &:hover {
        background: #e6f7ff !important;
        color: #1890ff;

        .el-icon {
          color: #1890ff;
        }
      }
    }

    .el-menu-item.is-active {
      background: #e6f7ff !important;
      color: #1890ff !important;
      font-weight: 500;

      &::before {
        content: '';
        position: absolute;
        left: 0;
        top: 0;
        width: 3px;
        height: 100%;
        background: #1890ff;
        box-shadow: 0 0 6px #1890ff;
      }

      .el-icon {
        color: #1890ff;
      }
    }

    .el-sub-menu {
      &.is-opened {
        > .el-sub-menu__title {
          color: #1890ff;
          background: #ffffff !important;
          font-weight: 500;

          .el-icon {
            color: #1890ff;
          }
        }
      }

      .el-menu {
        background: #fafafa;

        .el-menu-item {
          background: #fafafa;
          padding-left: 52px !important;

          &:hover {
            background: #e6f7ff !important;
            color: #1890ff;
          }

          &.is-active {
            background: #e6f7ff !important;
            color: #1890ff !important;
            font-weight: 500;
          }
        }
      }
    }

    // 折叠状态下的样式
    &.el-menu--collapse {
      .el-sub-menu {
        &.is-opened {
          > .el-sub-menu__title {
            background: #e6f7ff !important;
            color: #1890ff;
          }
        }
      }

      .el-menu-item {
        &:hover {
          background: #e6f7ff !important;
          color: #1890ff;
        }

        &.is-active {
          background: #e6f7ff !important;
          color: #1890ff !important;
        }
      }
    }
  }

  .el-icon {
    vertical-align: middle;
    margin-right: 8px;
    width: 20px;
    text-align: center;
    color: #666666;
    font-size: 18px;
    transition: color 0.3s;
  }

  span {
    vertical-align: middle;
    font-weight: 400;
  }
}
</style>