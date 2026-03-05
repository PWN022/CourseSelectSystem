<template>
  <div class="my-attendance-container">
    <el-card class="attendance-card">
      <template #header>
        <div class="card-header">
          <span>我的考勤记录</span>
        </div>
      </template>

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
          <el-form-item label="考勤状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
              <el-option
                v-for="item in statusOptions"
                :key="item"
                :label="item"
                :value="item"
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

      <!-- 统计卡片 -->
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
                <div class="statistic-value">{{ statistics.attendanceRate }}%</div>
                <div class="statistic-description">我的出勤率</div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>缺勤次数</span>
                </div>
              </template>
              <div class="card-content">
                <div class="statistic-value">{{ statistics.absentCount }}</div>
                <div class="statistic-description">我的缺勤次数</div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>请假次数</span>
                </div>
              </template>
              <div class="card-content">
                <div class="statistic-value">{{ statistics.leaveCount }}</div>
                <div class="statistic-description">我的请假次数</div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <el-table
        v-loading="loading"
        :data="tableData"
        border
        style="width: 100%; margin-top: 20px;"
      >
        <el-table-column prop="courseName" label="课程名称" width="180" />
        <el-table-column prop="attendanceDate" label="考勤日期" width="120" />
        <el-table-column prop="status" label="考勤状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="150" />
        <el-table-column prop="createTime" label="记录时间" width="180">
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
import { ref, reactive, onMounted, watch } from 'vue'
import { useUserStore } from '@/store/user'
import request from '@/utils/request'

const userStore = useUserStore()

// 表格数据
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

// 日期范围
const dateRange = ref([])

// 搜索表单
const searchForm = reactive({
  courseId: '',
  status: '',
  startDate: '',
  endDate: ''
})
const searchFormRef = ref(null)

// 选项数据
const courseOptions = ref([])
const statusOptions = ref([])

// 统计数据
const statistics = reactive({
  attendanceRate: 0,
  absentCount: 0,
  leaveCount: 0,
  presentCount: 0,
  total: 0
})

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
  fetchAttendance()
  fetchCourses()
  fetchAttendanceStatuses()
  fetchStudentStatistics()
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

// 获取状态标签类型
const getStatusType = (status) => {
  switch (status) {
    case '出席':
      return 'success'
    case '缺席':
      return 'danger'
    case '请假':
      return 'warning'
    default:
      return 'info'
  }
}

// 获取考勤列表
const fetchAttendance = async () => {
  loading.value = true
  try {
    await request.get('/attendance/page', {
      currentPage: currentPage.value,
      size: pageSize.value,
      studentId: userStore.studentInfo?.id,
      courseId: searchForm.courseId || null,
      status: searchForm.status || null,
      startDate: searchForm.startDate || null,
      endDate: searchForm.endDate || null
    }, {
      onSuccess: (res) => {
        tableData.value = res.records
        total.value = res.total
      }
    })
  } catch (error) {
    console.error('获取考勤列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取课程列表
const fetchCourses = async () => {
  try {
    await request.get('/course/all', {}, {
      onSuccess: (res) => {
        courseOptions.value = res || []
      }
    })
  } catch (error) {
    console.error('获取课程列表失败:', error)
  }
}

// 获取考勤状态选项
const fetchAttendanceStatuses = async () => {
  try {
    await request.get('/attendance/statuses', {}, {
      onSuccess: (res) => {
        statusOptions.value = res || []
      }
    })
  } catch (error) {
    console.error('获取考勤状态选项失败:', error)
  }
}

// 获取学生考勤统计
const fetchStudentStatistics = async () => {
  try {
    await request.get('/attendance/student/statistics', {
      studentId: userStore.studentInfo?.id
    }, {
      onSuccess: (res) => {
        statistics.attendanceRate = res.attendanceRate || 0
        statistics.absentCount = res.absentCount || 0
        statistics.leaveCount = res.leaveCount || 0
        statistics.presentCount = res.presentCount || 0
        statistics.total = res.total || 0
      }
    })
  } catch (error) {
    console.error('获取学生考勤统计失败:', error)
  }
}

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchAttendance()
}

// 重置查询
const resetSearch = () => {
  searchFormRef.value.resetFields()
  searchForm.courseId = ''
  searchForm.status = ''
  searchForm.startDate = ''
  searchForm.endDate = ''
  dateRange.value = []
  currentPage.value = 1
  fetchAttendance()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchAttendance()
}

// 处理每页条数变化
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchAttendance()
}
</script>

<style lang="scss" scoped>
.my-attendance-container {
  padding: 20px;

  .attendance-card {
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

  .pagination-container {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }
}
</style> 