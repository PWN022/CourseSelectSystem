<template>
  <div class="course-application">
    <div class="search-form">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="课程名称">
          <el-input v-model="searchForm.courseName" placeholder="请输入课程名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="searchForm.semester" placeholder="格式如：2023-2024-1" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程类型">
          <el-select v-model="searchForm.courseType" placeholder="请选择课程类型" clearable>
            <el-option label="必修课" value="必修课"></el-option>
            <el-option label="选修课" value="选修课"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>可选课程列表</span>
          <el-button type="primary" @click="submitApplications" :disabled="selectedCourses.length === 0">
            提交选课申请
          </el-button>
        </div>
      </template>
      <el-table
        v-loading="loading"
        :data="courseList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="courseCode" label="课程编号" width="120"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" width="180"></el-table-column>
        <el-table-column prop="teacherName" label="任课教师" width="120"></el-table-column>
        <el-table-column prop="semester" label="学期" width="120"></el-table-column>
        <el-table-column prop="credit" label="学分" width="80"></el-table-column>
        <el-table-column prop="hours" label="学时" width="80"></el-table-column>
        <el-table-column prop="courseType" label="课程类型" ></el-table-column>
        <el-table-column label="容量 / 余量" width="120" align="center">
          <template #default="scope">
            <span v-if="scope.row.maxCapacity">
              {{ scope.row.maxCapacity }} /
              <span :style="{ color: (scope.row.maxCapacity - (scope.row.studentCount || 0)) <= 0 ? '#F56C6C' : '#67C23A', fontWeight: 'bold' }">
                {{ Math.max(0, scope.row.maxCapacity - (scope.row.studentCount || 0)) }}
              </span>
            </span>
            <span v-else>不限</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button
                size="small"
                :type="scope.row.maxCapacity && (scope.row.maxCapacity - (scope.row.studentCount || 0)) <= 0 ? 'info' : 'primary'"
                :disabled="scope.row.maxCapacity && (scope.row.maxCapacity - (scope.row.studentCount || 0)) <= 0"
                @click="applyCourse(scope.row)"
            >
              {{ scope.row.maxCapacity && (scope.row.maxCapacity - (scope.row.studentCount || 0)) <= 0 ? '已满' : '申请选课' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[10, 20, 50, 100]"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        ></el-pagination>
      </div>
    </el-card>

    <el-card class="box-card mt-4">
      <template #header>
        <div class="card-header">
          <span>我的选课申请</span>
        </div>
      </template>
      <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <el-tab-pane label="全部申请" name="all"></el-tab-pane>
        <el-tab-pane label="待审批" name="待审批"></el-tab-pane>
        <el-tab-pane label="已通过" name="已通过"></el-tab-pane>
        <el-tab-pane label="已拒绝" name="已拒绝"></el-tab-pane>
        <el-tab-pane label="退课待审批" name="退课待审批"></el-tab-pane>
      </el-tabs>
      <el-table
        v-loading="applicationsLoading"
        :data="applicationList"
        style="width: 100%"
      >
        <el-table-column prop="courseCode" label="课程编号" width="120"></el-table-column>
        <el-table-column prop="courseName" label="课程名称" width="180"></el-table-column>
        <el-table-column prop="teacherName" label="任课教师" width="120"></el-table-column>
        <el-table-column prop="semester" label="学期" width="120"></el-table-column>
        <el-table-column prop="credit" label="学分" width="80"></el-table-column>
        <el-table-column prop="courseType" label="课程类型" width="100"></el-table-column>
        <el-table-column prop="status" label="审批状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button
              v-if="scope.row.status === '待审批'"
              size="small"
              type="danger"
              @click="handleDropUnapproved(scope.row)"
            >退课</el-button>
            <el-button
              v-if="scope.row.status === '已通过'"
              size="small"
              type="warning"
              @click="handleApplyDrop(scope.row)"
            >申请退课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/store/user'
import request from '@/utils/request'

const userStore = useUserStore()
const studentId = computed(() => userStore.studentInfo?.id)

// 搜索表单
const searchForm = reactive({
  courseName: '',
  semester: '',
  courseType: ''
})

// 课程列表数据
const courseList = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const selectedCourses = ref([])

// 申请列表数据
const applicationList = ref([])
const applicationsLoading = ref(false)
const activeTab = ref('all')

// 获取可选课程列表
const fetchCourses = async () => {
  if (!studentId.value) {
    ElMessage.warning('请先登录')
    return
  }
  
  loading.value = true
  try {
    await request.get('/teacher-course/page', {
      currentPage: currentPage.value,
      size: pageSize.value,
      courseName: searchForm.courseName || undefined,
      semester: searchForm.semester || undefined,
      courseType: searchForm.courseType || undefined
    }, {
      showDefaultMsg: false,
      onSuccess: (res) => {
        courseList.value = res.records
        total.value = res.total
      },
      onError: () => {
        courseList.value = []
        total.value = 0
      }
    })
  } catch (error) {
    console.error('获取课程列表出错:', error)
    ElMessage.error('获取课程列表出错')
  } finally {
    loading.value = false
  }
}

// 获取选课申请列表
const fetchApplications = async () => {
  if (!studentId.value) {
    ElMessage.warning('请先登录')
    return
  }
  
  applicationsLoading.value = true
  try {
    const status = activeTab.value === 'all' ? '' : activeTab.value
    await request.get('/student-course/applications', {
      studentId: studentId.value,
      status: status,
      semester: searchForm.semester || undefined
    }, {
      showDefaultMsg: false,
      onSuccess: (res) => {
        applicationList.value = res
      },
      onError: () => {
        applicationList.value = []
      }
    })
  } catch (error) {
    console.error('获取申请列表出错:', error)
    ElMessage.error('获取申请列表出错')
  } finally {
    applicationsLoading.value = false
  }
}

// 申请选课
const applyCourse = (course) => {
  if (!studentId.value) {
    ElMessage.warning('请先登录')
    return
  }
  
  ElMessageBox.confirm(`确定申请选修课程《${course.courseName}》吗?`, '申请确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.post('/student-course/apply', {
        studentId: studentId.value,
        courseId: course.courseId,
        teacherId: course.teacherId,
        semester: course.semester
      }, {
        successMsg: '申请提交成功',
        onSuccess: () => {
          fetchApplications() // 刷新申请列表
        }
      })
    } catch (error) {
      console.error('申请选课出错:', error)
    }
  }).catch(() => {
    // 用户取消操作
  })
}

// 批量提交选课申请
const submitApplications = () => {
  if (selectedCourses.value.length === 0) {
    ElMessage.warning('请先选择课程')
    return
  }
  
  if (!studentId.value) {
    ElMessage.warning('请先登录')
    return
  }
  
  ElMessageBox.confirm(`确定申请选修已选择的 ${selectedCourses.value.length} 门课程吗?`, '批量申请确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      const courseIds = selectedCourses.value.map(course => course.courseId || course.id)
      const teacherIds = selectedCourses.value.map(course => course.teacherId)
      const semester = selectedCourses.value[0].semester
      
      // 检查是否所有课程都是同一学期
      const allSameSemester = selectedCourses.value.every(course => course.semester === semester)
      if (!allSameSemester) {
        ElMessage.warning('请选择同一学期的课程进行批量申请')
        return
      }
      
      await request.post('/student-course/batch-apply', null, {
        params: {
          studentId: studentId.value,
          courseIds: courseIds,
          teacherIds: teacherIds,
          semester: semester
        },
        successMsg: '批量申请提交成功',
        onSuccess: () => {
          fetchApplications() // 刷新申请列表
        }
      })
    } catch (error) {
      console.error('批量申请选课出错:', error)
    }
  }).catch(() => {
    // 用户取消操作
  })
}

// 表格选择变化
const handleSelectionChange = (selection) => {
  selectedCourses.value = selection
}

// 查询
const handleQuery = () => {
  currentPage.value = 1
  fetchCourses()
}

// 重置查询
const resetQuery = () => {
  searchForm.courseName = ''
  searchForm.semester = ''
  searchForm.courseType = ''
  handleQuery()
}

// 分页大小变化
const handleSizeChange = (size) => {
  pageSize.value = size
  fetchCourses()
}

// 当前页变化
const handleCurrentChange = (page) => {
  currentPage.value = page
  fetchCourses()
}

// 标签页切换
const handleTabClick = () => {
  fetchApplications()
}

// 获取状态标签类型
const getStatusType = (status) => {
  switch (status) {
    case '待审批':
      return 'warning'
    case '已通过':
      return 'success'
    case '已拒绝':
      return 'danger'
    case '退课待审批':
      return 'info'
    default:
      return 'info'
  }
}

// 申请退课
const handleApplyDrop = (row) => {
  ElMessageBox.confirm(`确定要申请退课《${row.courseName}》吗? 申请将等待管理员或教师审批。`, '申请退课', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.post(`/student-course/drop-apply/${row.id}`, null, {
        successMsg: '申请退课成功，等待审批',
        onSuccess: () => {
          fetchApplications() // 刷新申请列表
        }
      })
    } catch (error) {
      console.error('申请退课失败:', error)
    }
  }).catch(() => {
    // 用户取消操作
  })
}

// 直接退课（针对未审批的课程）
const handleDropUnapproved = (row) => {
  ElMessageBox.confirm(`确定要退选课程《${row.courseName}》吗?`, '退课确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/student-course/drop-unapproved/${row.id}`, {
        successMsg: '退课成功',
        onSuccess: () => {
          fetchApplications() // 刷新申请列表
        }
      })
    } catch (error) {
      console.error('退课失败:', error)
    }
  }).catch(() => {
    // 用户取消操作
  })
}

onMounted(() => {
  fetchCourses()
  fetchApplications()
})
</script>

<style scoped>
.course-application {
  padding: 20px;
}

.search-form {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.mt-4 {
  margin-top: 20px;
}
</style> 