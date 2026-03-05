<template>
  <div class="my-scores-container">
    <el-card class="score-card">
      <template #header>
        <div class="card-header">
          <span>我的成绩</span>
        </div>
      </template>

      <div class="search-form">
        <el-form :inline="true" :model="searchForm" ref="searchFormRef">
          <el-form-item label="课程名称">
            <el-input v-model="searchForm.courseName" placeholder="请输入课程名称" clearable />
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
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 成绩统计卡片 -->
      <div class="statistics-cards">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-card shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>平均学分绩点</span>
                </div>
              </template>
              <div class="card-content">{{ statistics.averageGPA || '-' }}</div>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>已修学分</span>
                </div>
              </template>
              <div class="card-content">{{ statistics.totalCredits || 0 }}</div>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>平均分</span>
                </div>
              </template>
              <div class="card-content">{{ statistics.averageScore || '-' }}</div>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>课程数</span>
                </div>
              </template>
              <div class="card-content">{{ statistics.totalCourses || 0 }}</div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <el-table
        v-loading="loading"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column prop="courseCode" label="课程代码" width="120" />
        <el-table-column prop="courseName" label="课程名称" width="180" />
        <el-table-column prop="teacherName" label="授课教师" width="120" />
        <el-table-column prop="semester" label="学期" width="120" />
        <el-table-column prop="credit" label="学分" width="80" />
        <el-table-column prop="score" label="分数" width="80" />
        <el-table-column prop="grade" label="等级" width="80" />
        <el-table-column prop="comment" label="评语" />
        <el-table-column prop="createTime" label="录入时间" width="180">
          <template #default="scope">
            {{ formatDateTime(scope.row.createTime) }}
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
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import request from '@/utils/request'

const userStore = useUserStore()

// 表格数据
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

// 搜索表单
const searchForm = reactive({
  courseName: '',
  semester: ''
})
const searchFormRef = ref(null)

// 学期选项
const semesterOptions = ref([])

// 成绩统计数据
const statistics = reactive({
  averageGPA: null,
  totalCredits: 0,
  averageScore: null,
  totalCourses: 0
})

// 初始化
onMounted(() => {
  fetchScores()
  fetchSemesters()
  fetchStatistics()
})

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

// 获取成绩列表
const fetchScores = async () => {
  loading.value = true
  try {
    await request.get('/score/page', {
      currentPage: currentPage.value,
      size: pageSize.value,
      studentId: userStore.studentInfo?.id,
      courseName: searchForm.courseName || null,
      semester: searchForm.semester || null
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

// 获取成绩统计信息
const fetchStatistics = async () => {
  try {
    await request.get(`/score/statistics/${userStore.studentInfo?.id}`, {}, {
      onSuccess: (res) => {
        Object.assign(statistics, res)
      }
    })
  } catch (error) {
    console.error('获取成绩统计信息失败:', error)
  }
}

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchScores()
}

// 重置查询
const resetSearch = () => {
  searchFormRef.value.resetFields()
  currentPage.value = 1
  fetchScores()
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
</script>

<style lang="scss" scoped>
.my-scores-container {
  padding: 20px;

  .score-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }

  .search-form {
    margin-bottom: 20px;
    padding: 20px;
    background-color: #f5f7fa;
    border-radius: 4px;
  }

  .statistics-cards {
    margin-bottom: 20px;

    .el-card {
      .card-header {
        font-size: 14px;
        color: #606266;
      }

      .card-content {
        font-size: 24px;
        font-weight: bold;
        color: #303133;
        text-align: center;
        padding: 10px 0;
      }
    }
  }

  .pagination-container {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }
}
</style> 