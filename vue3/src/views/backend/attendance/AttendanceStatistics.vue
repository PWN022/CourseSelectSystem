<template>
  <div class="attendance-statistics-container">
    <!-- 添加教师提示信息 -->
    <el-alert
      v-if="userStore.isTeacher"
      title="提示"
      type="info"
      :closable="false"
      show-icon
      class="teacher-alert"
    >
      <p>您当前以教师身份登录，只能查看：</p>
      <p>1. 您担任班主任的班级学生考勤统计</p>
      <p>2. 您所教授课程的学生考勤统计</p>
    </el-alert>

    <div class="search-form">
      <el-form :inline="true" :model="searchForm" ref="searchFormRef">
        <el-form-item label="课程">
          <el-select v-model="searchForm.courseId" placeholder="请选择课程" clearable filterable>
            <el-option
              v-for="item in courseOptions"
              :key="item.id"
              :label="item.courseName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="日期范围">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
            format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="statistics-cards">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-card shadow="hover">
            <template #header>
              <div class="card-header">
                <span>出勤率</span>
              </div>
            </template>
            <div class="card-content">
              <div class="statistic-value">{{ attendanceRate }}%</div>
              <div class="statistic-description">总出勤率</div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="hover">
            <template #header>
              <div class="card-header">
                <span>缺勤数</span>
              </div>
            </template>
            <div class="card-content">
              <div class="statistic-value">{{ absenceCount }}</div>
              <div class="statistic-description">总缺勤次数</div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="hover">
            <template #header>
              <div class="card-header">
                <span>请假数</span>
              </div>
            </template>
            <div class="card-content">
              <div class="statistic-value">{{ leaveCount }}</div>
              <div class="statistic-description">总请假次数</div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="chart-container">
      <el-card shadow="hover">
        <template #header>
          <div class="card-header">
            <span>考勤状态分布</span>
          </div>
        </template>
        <div class="chart" ref="pieChartRef"></div>
      </el-card>
    </div>

    <div class="chart-container">
      <el-card shadow="hover">
        <template #header>
          <div class="card-header">
            <span>考勤趋势</span>
          </div>
        </template>
        <div class="chart" ref="lineChartRef"></div>
      </el-card>
    </div>

    <div class="table-container">
      <el-card shadow="hover">
        <template #header>
          <div class="card-header">
            <span>班级考勤统计</span>
          </div>
        </template>
        <el-table :data="classStatistics" border style="width: 100%">
          <el-table-column prop="className" label="班级" />
          <el-table-column prop="totalStudents" label="学生总数" />
          <el-table-column prop="attendanceRate" label="出勤率">
            <template #default="scope">
              {{ scope.row.attendanceRate }}%
            </template>
          </el-table-column>
          <el-table-column prop="absenceCount" label="缺勤次数" />
          <el-table-column prop="leaveCount" label="请假次数" />
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import request from '@/utils/request'
import { useUserStore } from '@/store/user'
const userStore = useUserStore()

// 搜索表单
const searchForm = reactive({
  courseId: '',
  startDate: '',
  endDate: ''
})
const searchFormRef = ref(null)
const dateRange = ref([])

// 选项数据
const courseOptions = ref([])

// 统计数据
const attendanceRate = ref(0)
const absenceCount = ref(0)
const leaveCount = ref(0)
const classStatistics = ref([])

// 图表引用
const pieChartRef = ref(null)
const lineChartRef = ref(null)

// 图表实例
let pieChart = null
let lineChart = null

// 监听窗口大小变化
const handleResize = () => {
  pieChart && pieChart.resize()
  lineChart && lineChart.resize()
}

// 监听日期范围变化
watch(dateRange, (newVal) => {
  if (newVal && newVal.length === 2) {
    searchForm.startDate = newVal[0]
    searchForm.endDate = newVal[1]
  } else {
    searchForm.startDate = ''
    searchForm.endDate = ''
  }
})

// 初始化
onMounted(() => {
  fetchCourses()
  fetchStatistics()
  window.addEventListener('resize', handleResize)
})

// 组件销毁前清理
onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  pieChart && pieChart.dispose()
  lineChart && lineChart.dispose()
})

// 获取课程列表
const fetchCourses = async () => {
  try {
    const params = {
      teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null
    }
    await request.get('/course/all', params, {
      onSuccess: (res) => {
        courseOptions.value = res || []
      }
    })
  } catch (error) {
    console.error('获取课程列表失败:', error)
  }
}

// 获取考勤统计数据
const fetchStatistics = async () => {
  try {
    const params = {
      courseId: searchForm.courseId || null,
      startDate: searchForm.startDate || null,
      endDate: searchForm.endDate || null,
      teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null
    }
    await request.get('/attendance/statistics', params, {
      onSuccess: (res) => {
        // 更新统计数据
        attendanceRate.value = res.attendanceRate || 0
        absenceCount.value = res.absentCount || 0
        leaveCount.value = res.leaveCount || 0
        classStatistics.value = res.classStatistics || []
        
        // 初始化图表
        initPieChart(res)
        initLineChart(res)
      }
    })
  } catch (error) {
    console.error('获取考勤统计数据失败:', error)
  }
}

// 初始化饼图
const initPieChart = (data) => {
  if (!pieChartRef.value) return
  
  pieChart = echarts.init(pieChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'horizontal',
      bottom: 10,
      data: ['出席', '缺席', '请假']
    },
    series: [
      {
        name: '考勤状态',
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
        data: [
          { value: data.presentCount || 0, name: '出席', itemStyle: { color: '#67C23A' } },
          { value: data.absentCount || 0, name: '缺席', itemStyle: { color: '#F56C6C' } },
          { value: data.leaveCount || 0, name: '请假', itemStyle: { color: '#E6A23C' } }
        ]
      }
    ]
  }
  
  pieChart.setOption(option)
}

// 初始化折线图
const initLineChart = (data) => {
  if (!lineChartRef.value) return
  
  lineChart = echarts.init(lineChartRef.value)
  
  const trendData = data.trendData || []
  const weeks = trendData.map(item => item.week)
  const presentRates = trendData.map(item => item.presentRate)
  const absentRates = trendData.map(item => item.absentRate)
  const leaveRates = trendData.map(item => item.leaveRate)
  
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['出席率', '缺席率', '请假率'],
      bottom: 10
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '15%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: weeks
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        formatter: '{value}%'
      }
    },
    series: [
      {
        name: '出席率',
        type: 'line',
        data: presentRates,
        itemStyle: { color: '#67C23A' }
      },
      {
        name: '缺席率',
        type: 'line',
        data: absentRates,
        itemStyle: { color: '#F56C6C' }
      },
      {
        name: '请假率',
        type: 'line',
        data: leaveRates,
        itemStyle: { color: '#E6A23C' }
      }
    ]
  }
  
  lineChart.setOption(option)
}

// 处理查询
const handleSearch = () => {
  fetchStatistics()
}

// 重置查询
const resetSearch = () => {
  searchFormRef.value.resetFields()
  searchForm.courseId = ''
  searchForm.startDate = ''
  searchForm.endDate = ''
  dateRange.value = []
  fetchStatistics()
}
</script>

<style lang="scss" scoped>
.attendance-statistics-container {
  padding: 20px;
  
  .teacher-alert {
    margin-bottom: 20px;
    
    p {
      margin: 5px 0;
      &:first-child {
        margin-top: 0;
      }
      &:last-child {
        margin-bottom: 0;
      }
    }
  }
  
  .search-form {
    margin-bottom: 20px;
    padding: 20px;
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  
  .statistics-cards {
    margin-bottom: 20px;
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-weight: bold;
    }
    
    .card-content {
      text-align: center;
      padding: 20px 0;
      
      .statistic-value {
        font-size: 36px;
        font-weight: bold;
        color: #1976D2;
        margin-bottom: 10px;
      }
      
      .statistic-description {
        font-size: 14px;
        color: #909399;
      }
    }
  }
  
  .chart-container {
    margin-bottom: 20px;
    
    .card-header {
      font-weight: bold;
    }
    
    .chart {
      height: 400px;
    }
  }
  
  .table-container {
    margin-bottom: 20px;
    
    .card-header {
      font-weight: bold;
    }
  }
}
</style> 