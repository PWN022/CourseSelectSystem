<template>
  <div class="course-approval">
    <div class="search-form">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="学期">
          <el-input 
            v-model="searchForm.semester" 
            placeholder="格式如：2024-2025-1"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="学生学号">
          <el-input 
            v-model="searchForm.studentNo" 
            placeholder="请输入学生学号" 
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input 
            v-model="searchForm.courseName" 
            placeholder="请输入课程名称" 
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="申请类型">
          <el-select v-model="searchForm.applicationType" placeholder="请选择申请类型" clearable>
            <el-option label="选课申请" value="待审批"></el-option>
            <el-option label="退课申请" value="退课待审批"></el-option>
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
          <span>待审批申请</span>
          <div>
            <el-button 
              type="success" 
              @click="batchApprove('已通过')" 
              :disabled="!selectedApplications || selectedApplications.length === 0"
            >
              批量通过
            </el-button>
            <el-button 
              type="danger" 
              @click="batchApprove('已拒绝')" 
              :disabled="!selectedApplications || selectedApplications.length === 0"
            >
              批量拒绝
            </el-button>
          </div>
        </div>
      </template>
      <el-table
        v-loading="loading"
        :data="applicationList || []"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="学生学号" width="120">
          <template #default="scope">
            {{ scope.row?.studentNo || '' }}
          </template>
        </el-table-column>
        <el-table-column label="学生姓名" width="120">
          <template #default="scope">
            {{ scope.row?.studentName || '' }}
          </template>
        </el-table-column>
        <el-table-column label="课程编号" width="120">
          <template #default="scope">
            {{ scope.row?.courseCode || '' }}
          </template>
        </el-table-column>
        <el-table-column label="课程名称" width="180">
          <template #default="scope">
            {{ scope.row?.courseName || '' }}
          </template>
        </el-table-column>
        <el-table-column label="学期" width="120">
          <template #default="scope">
            {{ scope.row?.semester || '' }}
          </template>
        </el-table-column>
        <el-table-column label="学分" width="80">
          <template #default="scope">
            {{ scope.row?.credit || '' }}
          </template>
        </el-table-column>
        <el-table-column label="课程类型" width="100">
          <template #default="scope">
            {{ scope.row?.courseType || '' }}
          </template>
        </el-table-column>
        <el-table-column label="申请类型" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row?.status)">
              {{ getApplicationType(scope.row?.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="申请时间">
          <template #default="scope">
            {{ scope.row?.createTime ? formatDateTime(scope.row.createTime) : '' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button
              size="small"
              type="success"
              @click="approveApplication(scope.row?.id, '已通过')"
              :disabled="!scope.row?.id"
            >通过</el-button>
            <el-button
              size="small"
              type="danger"
              @click="approveApplication(scope.row?.id, '已拒绝')"
              :disabled="!scope.row?.id"
            >拒绝</el-button>
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/store/user'
import request from '@/utils/request'

const userStore = useUserStore()
const teacherId = ref(userStore.teacherInfo?.id)
const isAdmin = ref(userStore.userInfo?.roleCode === 'ADMIN')

// 搜索表单 - 确保初始化所有字段
const searchForm = reactive({
  semester: '',
  studentNo: '',
  courseName: '',
  applicationType: '' // 新增申请类型字段
})

// 申请列表数据 - 确保所有ref都被初始化
const applicationList = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const selectedApplications = ref([])

// 格式化日期时间
const formatDateTime = (dateTime) => {
  if (!dateTime) return "";
  const date = new Date(dateTime);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

// 获取状态标签类型
const getStatusType = (status) => {
  switch (status) {
    case '待审批':
      return 'warning'
    case '退课待审批':
      return 'info'
    default:
      return 'warning'
  }
}

// 获取申请类型显示文本
const getApplicationType = (status) => {
  switch (status) {
    case '待审批':
      return '选课申请'
    case '退课待审批':
      return '退课申请'
    default:
      return '未知类型'
  }
}

// 获取待审批的选课申请
const fetchPendingApplications = async () => {
  loading.value = true
  try {
    // 构建查询参数
    const params = {
      currentPage: currentPage.value,
      size: pageSize.value,
      semester: searchForm.semester || undefined,
      teacherId: userStore.isTeacher ? teacherId.value : undefined
    }
    
    // 如果有学生学号，查询学生ID
    if (searchForm.studentNo) {
      const studentRes = await request.get('/student/page', {
        currentPage: 1,
        size: 10,
        studentNo: searchForm.studentNo
      })
      
      if (studentRes?.data?.records?.length > 0) {
        params.studentId = studentRes.data.records[0].id
      } else {
        applicationList.value = []
        total.value = 0
        loading.value = false
        return
      }
    }
    
    // 如果有课程名称，查询课程ID
    if (searchForm.courseName) {
      const courseRes = await request.get('/course/page', {
        currentPage: 1,
        size: 10,
        courseName: searchForm.courseName
      })
      
      if (courseRes?.data?.records?.length > 0) {
        params.courseId = courseRes.data.records[0].id
      } else {
        applicationList.value = []
        total.value = 0
        loading.value = false
        return
      }
    }
    
    // 获取待审批申请
    const res = await request.get('/student-course/pending', params,{
      showDefaultMessage:false,
      onSuccess:(res)=>{
        console.log('待审批申请数据:', res)
        if(res.records){
          // 根据申请类型过滤
          let records = res.records;
          if (searchForm.applicationType) {
            records = records.filter(item => item.status === searchForm.applicationType);
          }
          applicationList.value = records
          total.value = records.length || 0
        }else{
          applicationList.value = []
          total.value = 0
        }
      }
    })
  } catch (error) {
    console.error('获取待审批申请出错:', error)
    ElMessage.error('获取待审批申请出错')
    applicationList.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

// 审批单个申请
const approveApplication = async (id, status) => {
  if (!id) return
  
  const statusText = status === '已通过' ? '通过' : '拒绝'
  const application = applicationList.value.find(app => app.id === id)
  const isDropApplication = application && application.status === '退课待审批'
  
  try {
    await ElMessageBox.confirm(
      isDropApplication 
        ? `确定${statusText}此退课申请吗?${status === '已通过' ? '通过将删除该选课记录' : '拒绝将恢复为已通过状态'}`
        : `确定${statusText}此选课申请吗?`, 
      '审批确认', 
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: status === '已通过' ? 'success' : 'warning'
      }
    )
    
    if (isDropApplication && status === '已通过') {
      // 如果是退课申请且通过，则删除选课记录
      await request.delete(`/student-course/${id}`)
      ElMessage.success('退课申请已通过，选课记录已删除')
    } else {
      // 其他情况，更新状态
      await request.put(`/student-course/approve/${id}`, null, {
        params: { status }
      })
      ElMessage.success(`申请已${statusText}`)
    }
    
    fetchPendingApplications() // 刷新申请列表
  } catch (error) {
    if (error !== 'cancel') {
      console.error(`审批申请出错:`, error)
      ElMessage.error(`审批申请出错`)
    }
  }
}

// 批量审批申请
const batchApprove = async (status) => {
  if (!selectedApplications.value?.length) {
    ElMessage.warning('请先选择申请')
    return
  }
  
  const statusText = status === '已通过' ? '通过' : '拒绝'
  const hasDropApplications = selectedApplications.value.some(app => app.status === '退课待审批')
  
  try {
    await ElMessageBox.confirm(
      hasDropApplications
        ? `确定批量${statusText}选中的 ${selectedApplications.value.length} 条申请吗?其中退课申请通过将删除相应选课记录`
        : `确定批量${statusText}选中的 ${selectedApplications.value.length} 条申请吗?`,
      '批量审批确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: status === '已通过' ? 'success' : 'warning'
      }
    )
    
    const promises = selectedApplications.value.map(app => {
      if (app.status === '退课待审批' && status === '已通过') {
        // 如果是退课申请且通过，则删除选课记录
        return request.delete(`/student-course/${app.id}`)
      } else {
        // 其他情况，更新状态
        return request.put(`/student-course/approve/${app.id}`, null, {
          params: { status }
        })
      }
    })
    
    await Promise.all(promises)
    ElMessage.success(`批量${statusText}成功`)
    fetchPendingApplications() // 刷新申请列表
  } catch (error) {
    if (error !== 'cancel') {
      console.error(`批量审批出错:`, error)
      ElMessage.error(`批量审批出错`)
    }
  }
}

// 表格选择变化
const handleSelectionChange = (selection) => {
  selectedApplications.value = selection || []
}

// 查询
const handleQuery = () => {
  currentPage.value = 1
  fetchPendingApplications()
}

// 重置查询
const resetQuery = () => {
  searchForm.semester = ''
  searchForm.studentNo = ''
  searchForm.courseName = ''
  searchForm.applicationType = ''
  currentPage.value = 1
  fetchPendingApplications()
}

// 分页大小变化
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchPendingApplications()
}

// 当前页变化
const handleCurrentChange = (page) => {
  currentPage.value = page
  fetchPendingApplications()
}

onMounted(() => {
  fetchPendingApplications()
})
</script>

<style scoped>
.course-approval {
  padding: 20px;
}

.search-form {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
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
</style> 