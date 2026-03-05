<template>
  <div class="score-analysis-container">
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
      <p>1. 您担任班主任的班级学生成绩分析</p>
      <p>2. 您所教授课程的学生成绩分析</p>
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
        <el-form-item label="学期">
          <el-select v-model="searchForm.semester" placeholder="请选择学期" clearable>
            <el-option
              v-for="item in semesterOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="searchForm.classId" placeholder="请选择班级" clearable filterable>
            <el-option
              v-for="item in classOptions"
              :key="item.id"
              :label="item.className"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-row :gutter="20">
      <el-col :span="12">
        <div class="chart-card">
          <div class="chart-title">成绩分布</div>
          <div class="chart-container" ref="gradeChartRef"></div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="chart-card">
          <div class="chart-title">分数段分布</div>
          <div class="chart-container" ref="scoreRangeChartRef"></div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <div class="chart-card">
          <div class="chart-title">统计信息</div>
          <div class="statistics-container">
            <el-descriptions :column="4" border>
              <el-descriptions-item label="总人数">{{ statistics.totalCount }}</el-descriptions-item>
              <el-descriptions-item label="平均分">{{ statistics.averageScore }}</el-descriptions-item>
              <el-descriptions-item label="最高分">{{ statistics.maxScore }}</el-descriptions-item>
              <el-descriptions-item label="最低分">{{ statistics.minScore }}</el-descriptions-item>
              <el-descriptions-item label="及格率">{{ statistics.passRate }}%</el-descriptions-item>
              <el-descriptions-item label="优秀率">{{ statistics.excellentRate }}%</el-descriptions-item>
              <el-descriptions-item label="良好率">{{ statistics.goodRate }}%</el-descriptions-item>
              <el-descriptions-item label="中等率">{{ statistics.mediumRate }}%</el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <div class="chart-card">
          <div class="chart-title">成绩详情</div>
          <el-table
            v-loading="loading"
            :data="tableData"
            border
            style="width: 100%"
          >
            <el-table-column prop="studentNo" label="学号" width="120" />
            <el-table-column prop="studentName" label="学生姓名" width="120" />
            <el-table-column prop="className" label="班级" width="150" />
            <el-table-column prop="courseName" label="课程名称" width="150" />
            <el-table-column prop="semester" label="学期" width="120" />
            <el-table-column prop="score" label="分数" width="80" />
            <el-table-column prop="grade" label="等级" width="80">
              <template #default="scope">
                <el-tag :type="getGradeTagType(scope.row.grade)">{{ scope.row.grade }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination-container">
            <el-pagination
              :current-page="currentPage"
              :page-size="pageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref, watch} from 'vue'
import request from '@/utils/request'
import * as echarts from 'echarts'
import {useUserStore} from '@/store/user'

const userStore = useUserStore()
// 表格数据
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

// 搜索表单
const searchForm = reactive({
  courseId: '',
  semester: '',
  classId: ''
})
const searchFormRef = ref(null)

// 选项数据
const courseOptions = ref([])
const semesterOptions = ref([])
const classOptions = ref([])

// 图表引用
const gradeChartRef = ref(null)
const scoreRangeChartRef = ref(null)
let gradeChart = null
let scoreRangeChart = null

// 统计数据
const statistics = reactive({
  totalCount: 0,
  averageScore: '0.00',
  maxScore: '0.00',
  minScore: '0.00',
  passRate: '0.00',
  excellentRate: '0.00',
  goodRate: '0.00',
  mediumRate: '0.00'
})

// 初始化
onMounted(() => {
  fetchScores()
  fetchCourses()
  fetchSemesters()
  fetchClasses()
  initCharts()
  
  // 监听窗口大小变化，重新调整图表大小
  window.addEventListener('resize', handleResize)
})

// 监听搜索条件变化
watch([() => searchForm.courseId, () => searchForm.semester, () => searchForm.classId], () => {
  if (gradeChart && scoreRangeChart) {
    fetchScoreStatistics()
  }
})

// 初始化图表
const initCharts = () => {
  // 初始化成绩等级分布图表
  gradeChart = echarts.init(gradeChartRef.value)
  const gradeOption = {
    title: {
      text: '成绩等级分布',
      left: 'center'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      data: ['A', 'B', 'C', 'D', 'E']
    },
    series: [
      {
        name: '成绩等级',
        type: 'pie',
        radius: '60%',
        center: ['50%', '50%'],
        data: [
          { value: 0, name: 'A' },
          { value: 0, name: 'B' },
          { value: 0, name: 'C' },
          { value: 0, name: 'D' },
          { value: 0, name: 'E' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ],
    color: ['#67C23A', '#409EFF', '#E6A23C', '#F56C6C', '#909399']
  }
  gradeChart.setOption(gradeOption)
  
  // 初始化分数段分布图表
  scoreRangeChart = echarts.init(scoreRangeChartRef.value)
  const scoreRangeOption = {
    title: {
      text: '分数段分布',
      left: 'center'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: ['0-59', '60-69', '70-79', '80-89', '90-100']
    },
    yAxis: {
      type: 'value',
      name: '人数'
    },
    series: [
      {
        name: '人数',
        type: 'bar',
        data: [0, 0, 0, 0, 0],
        itemStyle: {
          color: function(params) {
            const colors = ['#F56C6C', '#E6A23C', '#E6A23C', '#409EFF', '#67C23A']
            return colors[params.dataIndex]
          }
        }
      }
    ]
  }
  scoreRangeChart.setOption(scoreRangeOption)
  
  // 获取统计数据
  fetchScoreStatistics()
}

// 处理窗口大小变化
const handleResize = () => {
  if (gradeChart) {
    gradeChart.resize()
  }
  if (scoreRangeChart) {
    scoreRangeChart.resize()
  }
}

// 获取成绩列表
const fetchScores = async () => {
  loading.value = true
  try {
    await request.get('/score/page', {
      currentPage: currentPage.value,
      size: pageSize.value,
      courseId: searchForm.courseId || null,
      semester: searchForm.semester || null,
      classId: searchForm.classId || null,
      teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null
    }, {
      onSuccess: (res) => {
        tableData.value = res.records
        total.value = res.total
      }
    })
  } catch (error) {
    console.error('获取成绩列表失败:', error)
  } finally {
    loading.value = false
  }
}

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

// 获取学期列表
const fetchSemesters = async () => {
  try {
    await request.get('/score/semesters', {}, {
      onSuccess: (res) => {
        semesterOptions.value = res || []
      }
    })
  } catch (error) {
    console.error('获取学期列表失败:', error)
  }
}

// 获取班级列表
const fetchClasses = async () => {
  try {
    await request.get('/class/all', {}, {
      onSuccess: (res) => {
        classOptions.value = res || []
      }
    })
  } catch (error) {
    console.error('获取班级列表失败:', error)
  }
}

// 获取成绩统计数据
const fetchScoreStatistics = async () => {
  try {
    const params = {
      currentPage: 1,
      size: 1000, // 获取足够多的数据进行统计
      courseId: searchForm.courseId || null,
      semester: searchForm.semester || null,
      classId: searchForm.classId || null,
      teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null
    }
    await request.get('/score/page', params, {
      onSuccess: (res) => {
        const scores = res.records
        calculateStatistics(scores)
        updateCharts(scores)
      }
    })
  } catch (error) {
    console.error('获取成绩统计数据失败:', error)
  }
}

// 计算统计数据
const calculateStatistics = (scores) => {
  if (!scores || scores.length === 0) {
    resetStatistics()
    return
  }
  
  const totalCount = scores.length
  let totalScore = 0
  let maxScore = 0
  let minScore = 100
  let passCount = 0
  let excellentCount = 0
  let goodCount = 0
  let mediumCount = 0
  
  scores.forEach(item => {
    const score = parseFloat(item.score)
    totalScore += score
    
    if (score > maxScore) {
      maxScore = score
    }
    
    if (score < minScore) {
      minScore = score
    }
    
    if (score >= 60) {
      passCount++
    }
    
    if (score >= 90) {
      excellentCount++
    } else if (score >= 80) {
      goodCount++
    } else if (score >= 70) {
      mediumCount++
    }
  })
  
  statistics.totalCount = totalCount
  statistics.averageScore = (totalScore / totalCount).toFixed(2)
  statistics.maxScore = maxScore.toFixed(2)
  statistics.minScore = minScore.toFixed(2)
  statistics.passRate = ((passCount / totalCount) * 100).toFixed(2)
  statistics.excellentRate = ((excellentCount / totalCount) * 100).toFixed(2)
  statistics.goodRate = ((goodCount / totalCount) * 100).toFixed(2)
  statistics.mediumRate = ((mediumCount / totalCount) * 100).toFixed(2)
}

// 重置统计数据
const resetStatistics = () => {
  statistics.totalCount = 0
  statistics.averageScore = '0.00'
  statistics.maxScore = '0.00'
  statistics.minScore = '0.00'
  statistics.passRate = '0.00'
  statistics.excellentRate = '0.00'
  statistics.goodRate = '0.00'
  statistics.mediumRate = '0.00'
}

// 更新图表数据
const updateCharts = (scores) => {
  if (!scores || scores.length === 0) {
    resetCharts()
    return
  }
  
  // 统计等级分布
  const gradeCount = {
    'A': 0,
    'B': 0,
    'C': 0,
    'D': 0,
    'E': 0
  }
  
  // 统计分数段分布
  const scoreRangeCount = [0, 0, 0, 0, 0] // [0-59, 60-69, 70-79, 80-89, 90-100]
  
  scores.forEach(item => {
    // 统计等级
    if (item.grade) {
      gradeCount[item.grade]++
    }
    
    // 统计分数段
    const score = parseFloat(item.score)
    if (score < 60) {
      scoreRangeCount[0]++
    } else if (score < 70) {
      scoreRangeCount[1]++
    } else if (score < 80) {
      scoreRangeCount[2]++
    } else if (score < 90) {
      scoreRangeCount[3]++
    } else {
      scoreRangeCount[4]++
    }
  })
  
  // 更新等级分布图表
  if (gradeChart) {
    gradeChart.setOption({
      series: [{
        data: [
          { value: gradeCount['A'], name: 'A' },
          { value: gradeCount['B'], name: 'B' },
          { value: gradeCount['C'], name: 'C' },
          { value: gradeCount['D'], name: 'D' },
          { value: gradeCount['E'], name: 'E' }
        ]
      }]
    })
  }
  
  // 更新分数段分布图表
  if (scoreRangeChart) {
    scoreRangeChart.setOption({
      series: [{
        data: scoreRangeCount
      }]
    })
  }
}

// 重置图表数据
const resetCharts = () => {
  if (gradeChart) {
    gradeChart.setOption({
      series: [{
        data: [
          { value: 0, name: 'A' },
          { value: 0, name: 'B' },
          { value: 0, name: 'C' },
          { value: 0, name: 'D' },
          { value: 0, name: 'E' }
        ]
      }]
    })
  }
  
  if (scoreRangeChart) {
    scoreRangeChart.setOption({
      series: [{
        data: [0, 0, 0, 0, 0]
      }]
    })
  }
}

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchScores()
  fetchScoreStatistics()
}

// 重置查询
const resetSearch = () => {
  // 先使用resetFields方法
  searchFormRef.value.resetFields()
  
  // 然后手动重置表单值
  searchForm.courseId = ''
  searchForm.semester = ''
  searchForm.classId = ''
  
  // 重置页码并重新获取数据
  currentPage.value = 1
  fetchScores()
  fetchScoreStatistics()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchScores()
}

// 处理每页条数变化
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchScores()
}

// 获取等级对应的标签类型
const getGradeTagType = (grade) => {
  switch (grade) {
    case 'A':
      return 'success'
    case 'B':
      return 'primary'
    case 'C':
      return 'warning'
    case 'D':
      return 'warning'
    case 'E':
      return 'danger'
    default:
      return 'info'
  }
}
</script>

<style lang="scss" scoped>
.score-analysis-container {
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
  
  .chart-card {
    background-color: #fff;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin-bottom: 20px;
    
    .chart-title {
      font-size: 18px;
      font-weight: bold;
      margin-bottom: 20px;
      color: #303133;
    }
    
    .chart-container {
      height: 300px;
    }
    
    .statistics-container {
      padding: 10px;
    }
  }
  
  .el-table {
    margin-bottom: 20px;
  }
  
  .pagination-container {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }
}
</style> 