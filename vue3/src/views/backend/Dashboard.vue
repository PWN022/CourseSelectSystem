<template>
  <div class="dashboard">
    <!-- 欢迎卡片 -->
    <el-card class="welcome-card">
      <template #header>
        <div class="welcome-header">
          <el-avatar :size="64" :src="avatarUrl">
            {{ userInfo?.name?.charAt(0) }}
          </el-avatar>
          <div class="welcome-info">
            <h2>欢迎回来, {{ userInfo?.name || userInfo?.username }}</h2>
            <p>{{ currentTime }}</p>
          </div>
        </div>
      </template>
      <div class="role-info">
        <el-tag>{{ roleLabel }}</el-tag>
      </div>
    </el-card>
    
    <!-- 学生统计数据卡片 -->
    <div v-if="hasStudentPermission" class="stats-section">
      <h3 class="section-title">学生数据统计</h3>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card class="stat-card">
            <template #header>
              <div class="card-header">
                <span>学生总数</span>
              </div>
            </template>
            <div class="card-content">
              <div class="stat-number">{{ studentStats.totalStudents }}</div>
              <div class="stat-icon">
                <el-icon><User /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <template #header>
              <div class="card-header">
                <span>班级总数</span>
              </div>
            </template>
            <div class="card-content">
              <div class="stat-number">{{ studentStats.totalClasses }}</div>
              <div class="stat-icon">
                <el-icon><School /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <template #header>
              <div class="card-header">
                <span>男生比例</span>
              </div>
            </template>
            <div class="card-content">
              <div class="stat-number">{{ studentStats.malePercentage }}%</div>
              <div class="stat-icon">
                <el-icon><Male /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <template #header>
              <div class="card-header">
                <span>女生比例</span>
              </div>
            </template>
            <div class="card-content">
              <div class="stat-number">{{ studentStats.femalePercentage }}%</div>
              <div class="stat-icon">
                <el-icon><Female /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="20" class="chart-row">
        <el-col :span="12">
          <el-card class="chart-card">
            <template #header>
              <div class="card-header">
                <span>班级学生分布</span>
              </div>
            </template>
            <div class="chart-container" ref="classDistributionChart"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="chart-card">
            <template #header>
              <div class="card-header">
                <span>专业学生分布</span>
              </div>
            </template>
            <div class="chart-container" ref="majorDistributionChart"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    
    <!-- 教师统计数据卡片 -->
    <div v-if="hasTeacherPermission" class="stats-section">
      <h3 class="section-title">教师数据统计</h3>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card class="stat-card">
            <template #header>
              <div class="card-header">
                <span>教师总数</span>
              </div>
            </template>
            <div class="card-content">
              <div class="stat-number">{{ teacherStats.totalTeachers || 0 }}</div>
              <div class="stat-icon">
                <el-icon><User /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <template #header>
              <div class="card-header">
                <span>男教师</span>
              </div>
            </template>
            <div class="card-content">
              <div class="stat-number">{{ teacherStats.maleTeachers || 0 }}</div>
              <div class="stat-icon male-icon">
                <el-icon><Male /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <template #header>
              <div class="card-header">
                <span>女教师</span>
              </div>
            </template>
            <div class="card-content">
              <div class="stat-number">{{ teacherStats.femaleTeachers || 0 }}</div>
              <div class="stat-icon female-icon">
                <el-icon><Female /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stat-card">
            <template #header>
              <div class="card-header">
                <span>职称种类</span>
              </div>
            </template>
            <div class="card-content">
              <div class="stat-number">{{ teacherStats.titleCount || 0 }}</div>
              <div class="stat-icon">
                <el-icon><Briefcase /></el-icon>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="20" class="chart-row">
        <el-col :xs="24" :md="12">
          <el-card class="chart-card">
            <template #header>
              <div class="card-header">
                <span>教师性别分布</span>
              </div>
            </template>
            <div id="gender-chart" class="chart-container"></div>
          </el-card>
        </el-col>
        <el-col :xs="24" :md="12">
          <el-card class="chart-card">
            <template #header>
              <div class="card-header">
                <span>教师职称分布</span>
              </div>
            </template>
            <div id="title-chart" class="chart-container"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, reactive, onMounted, onUnmounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import request from '@/utils/request'
import { User, School, Male, Female, Briefcase } from '@element-plus/icons-vue'
import { getRoleLabel } from '@/constants/userRoles'
import * as echarts from 'echarts/core'
import { PieChart, BarChart } from 'echarts/charts'
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  GraphicComponent,
  AxisPointerComponent
} from 'echarts/components'
import { LabelLayout } from 'echarts/features'
import { CanvasRenderer } from 'echarts/renderers'

// 注册必须的组件
echarts.use([
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  GraphicComponent,
  AxisPointerComponent,
  PieChart,
  BarChart,
  LabelLayout,
  CanvasRenderer
])

const router = useRouter()
const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)
const baseAPI = import.meta.env.VITE_BASE_API || '/api'

// 角色标签
const roleLabel = computed(() => {
  return getRoleLabel(userInfo.value?.roleCode) || '未知角色'
})

const avatarUrl = computed(() => {
  return userInfo.value?.avatar ? baseAPI + userInfo.value.avatar : '';
})

// 权限计算属性
const hasStudentPermission = computed(() => {
  return ['ADMIN', 'TEACHER'].includes(userInfo.value?.roleCode)
})

const hasTeacherPermission = computed(() => {
  return ['ADMIN'].includes(userInfo.value?.roleCode)
})

// 图表引用
const classDistributionChart = ref(null)
const majorDistributionChart = ref(null)

// 图表实例
let classChart = null
let majorChart = null
let genderChart = null
let titleChart = null

// 学生统计数据
const studentStats = reactive({
  totalStudents: 0,
  totalClasses: 0,
  malePercentage: 0,
  femalePercentage: 0
})

// 教师统计数据
const teacherStats = reactive({
  totalTeachers: 0,
  maleTeachers: 0,
  femaleTeachers: 0,
  titleCount: 0
})

// 加载状态
const loading = reactive({
  student: false,
  teacher: false
})

// 当前时间
const currentTime = ref('')
let timeInterval = null // 保存定时器引用

const updateTime = () => {
  const now = new Date()
  const options = { 
    year: 'numeric', 
    month: 'long', 
    day: 'numeric', 
    weekday: 'long',
    hour: '2-digit',
    minute: '2-digit'
  }
  currentTime.value = now.toLocaleDateString('zh-CN', options)
}

onMounted(() => {
  updateTime()
  // 每分钟更新一次时间
  timeInterval = setInterval(updateTime, 60000)
  
  // 获取数据统计
  if (hasStudentPermission.value) {
    fetchStudentDashboardData()
  }
  
  if (hasTeacherPermission.value) {
    fetchTeacherStatistics()
  }
  
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  // 清除定时器
  if (timeInterval) {
    clearInterval(timeInterval)
    timeInterval = null
  }
  
  // 清除事件监听和图表实例
  window.removeEventListener('resize', handleResize)
  if (classChart) {
    classChart.dispose()
  }
  if (majorChart) {
    majorChart.dispose()
  }
  if (genderChart) {
    genderChart.dispose()
  }
  if (titleChart) {
    titleChart.dispose()
  }
})

// 处理窗口大小变化，重新绘制图表
const handleResize = () => {
  if (classChart) {
    classChart.resize()
  }
  if (majorChart) {
    majorChart.resize()
  }
  if (genderChart) {
    genderChart.resize()
  }
  if (titleChart) {
    titleChart.resize()
  }
}

// 格式化日期时间
const formatDateTime = (dateTime) => {
  if (!dateTime) return ''
  const date = new Date(dateTime)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}`
}

// 获取学生仪表盘数据
const fetchStudentDashboardData = async () => {
  loading.student = true
  try {
    // 获取学生总数
    await request.get('/student/page', {
      currentPage: 1,
      size: 1
    }, {
      onSuccess: (res) => {
        studentStats.totalStudents = res.total
      }
    })
    
    // 获取班级总数
    await request.get('/class/page', {
      currentPage: 1,
      size: 1
    }, {
      onSuccess: (res) => {
        studentStats.totalClasses = res.total
      }
    })
    
    // 获取性别分布
    await request.get('/student/page', {
      currentPage: 1,
      size: studentStats.totalStudents > 1000 ? 1000 : studentStats.totalStudents
    }, {
      onSuccess: (res) => {
        const students = res.records
        const maleCount = students.filter(s => s.gender === 'M').length
        const femaleCount = students.filter(s => s.gender === 'F').length
        const total = students.length
        
        studentStats.malePercentage = total > 0 ? Math.round((maleCount / total) * 100) : 0
        studentStats.femalePercentage = total > 0 ? Math.round((femaleCount / total) * 100) : 0
      }
    })
    
    // 获取班级列表
    await request.get('/class/all', {}, {
      onSuccess: (res) => {
        const classes = res
        
        // 获取所有班级的学生数量
        const fetchClassStudentCounts = async () => {
          const classData = []
          const majorData = {}
          
          for (const cls of classes) {
            await request.get(`/student/class/${cls.id}`, {}, {
              onSuccess: (students) => {
                // 班级学生分布数据
                classData.push({
                  name: cls.className,
                  value: students.length
                })
                
                // 专业学生分布数据
                if (!majorData[cls.major]) {
                  majorData[cls.major] = 0
                }
                majorData[cls.major] += students.length
              }
            })
          }
          
          // 转换专业数据为数组
          const majorDataArray = Object.keys(majorData).map(key => ({
            name: key,
            value: majorData[key]
          }))
          
          // 初始化图表
          nextTick(() => {
            initClassChart(classData)
            initMajorChart(majorDataArray)
          })
        }
        
        fetchClassStudentCounts()
      }
    })
  } catch (error) {
    console.error('获取学生仪表盘数据失败:', error)
  } finally {
    loading.student = false
  }
}

// 获取教师统计数据
const fetchTeacherStatistics = async () => {
  loading.teacher = true
  try {
    await request.get('/teacher/statistics', {}, {
      onSuccess: (res) => {
        teacherStats.totalTeachers = res.totalTeachers
        teacherStats.maleTeachers = res.maleTeachers
        teacherStats.femaleTeachers = res.femaleTeachers
        teacherStats.titleCount = res.titleCount
        
        // 初始化图表
        nextTick(() => {
          initGenderChart(res.maleTeachers, res.femaleTeachers)
          initTitleChart(res.titleDistribution)
        })
      }
    })
  } catch (error) {
    console.error('获取教师统计数据失败:', error)
  } finally {
    loading.teacher = false
  }
}

// 初始化班级分布图表
const initClassChart = (data) => {
  if (!classDistributionChart.value) return
  
  classChart = echarts.init(classDistributionChart.value)
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 10,
      data: data.map(item => item.name)
    },
    series: [
      {
        name: '班级学生分布',
        type: 'pie',
        radius: ['50%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: data
      }
    ]
  }
  
  classChart.setOption(option)
}

// 初始化专业分布图表
const initMajorChart = (data) => {
  if (!majorDistributionChart.value) return
  
  majorChart = echarts.init(majorDistributionChart.value)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: data.map(item => item.name),
      axisLabel: {
        interval: 0,
        rotate: 30
      }
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '学生数量',
        type: 'bar',
        barWidth: '60%',
        data: data.map(item => item.value),
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#83bff6' },
            { offset: 0.5, color: '#188df0' },
            { offset: 1, color: '#188df0' }
          ])
        }
      }
    ]
  }
  
  majorChart.setOption(option)
}

// 初始化教师性别分布图表
const initGenderChart = (male, female) => {
  const chartDom = document.getElementById('gender-chart')
  if (!chartDom) return
  
  genderChart = echarts.init(chartDom)
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'horizontal',
      bottom: 'bottom'
    },
    series: [
      {
        name: '教师性别分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: male, name: '男' },
          { value: female, name: '女' }
        ]
      }
    ],
    color: ['#4299e1', '#ed64a6']
  }
  
  genderChart.setOption(option)
}

// 初始化教师职称分布图表
const initTitleChart = (titleData) => {
  const chartDom = document.getElementById('title-chart')
  if (!chartDom) return
  
  titleChart = echarts.init(chartDom)
  
  // 转换职称数据为图表数据格式
  const seriesData = Object.entries(titleData).map(([title, count]) => {
    return { value: count, name: title }
  })
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'horizontal',
      bottom: 'bottom',
      type: 'scroll',
      pageIconSize: 12
    },
    series: [
      {
        name: '教师职称分布',
        type: 'pie',
        radius: '60%',
        center: ['50%', '45%'],
        data: seriesData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  
  titleChart.setOption(option)
}
</script>

<style lang="scss" scoped>
.dashboard {
  padding: 20px;
  
  .welcome-card {
    margin-bottom: 20px;
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    
    .welcome-header {
      display: flex;
      align-items: center;
      gap: 20px;
      
      .el-avatar {
        transition: transform 0.3s ease;
        
        &:hover {
          transform: scale(1.1);
        }
      }
      
      .welcome-info {
        h2 {
          margin: 0 0 8px 0;
          font-size: 24px;
          background: linear-gradient(to right, #409eff, #67c23a);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
        p {
          margin: 0;
          color: #666;
        }
      }
    }
    
    .role-info {
      margin-top: 16px;
    }
  }
  
  .stats-section {
    margin-bottom: 30px;
    
    .section-title {
      font-size: 18px;
      font-weight: bold;
      margin-bottom: 15px;
      color: #303133;
    }
  }
  
  .stat-card {
    height: 150px;
    margin-bottom: 20px;
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 16px;
      font-weight: bold;
    }
    
    .card-content {
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 80px;
      
      .stat-number {
        font-size: 36px;
        font-weight: bold;
        color: #409EFF;
      }
      
      .stat-icon {
        font-size: 48px;
        color: #ebeef5;
        
        .el-icon {
          width: 48px;
          height: 48px;
        }
        
        &.male-icon {
          color: #4299e1;
        }
        
        &.female-icon {
          color: #ed64a6;
        }
      }
    }
  }
  
  .chart-row {
    margin-bottom: 20px;
    
    .chart-card {
      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-weight: bold;
      }
      
      .chart-container {
        height: 300px;
      }
    }
  }
}
</style> 