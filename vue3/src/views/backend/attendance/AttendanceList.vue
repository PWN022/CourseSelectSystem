<template>
  <div class="attendance-container">
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
      <p>1. 您担任班主任的班级学生考勤记录</p>
      <p>2. 您所教授课程的学生考勤记录</p>
    </el-alert>

    <div class="search-form">
      <el-form :inline="true" :model="searchForm" ref="searchFormRef">
        <el-form-item label="学生">
          <el-select v-model="searchForm.studentId" placeholder="请选择学生" clearable filterable>
            <el-option
              v-for="item in studentOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
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
          <el-button type="success" @click="handleAdd">添加考勤</el-button>
          <el-button type="warning" @click="handleBatchAdd">批量添加</el-button>
        </el-form-item>
      </el-form>
    </div>

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
      <el-table-column prop="attendanceDate" label="考勤日期" width="120" />
      <el-table-column prop="status" label="考勤状态" width="100">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="150" />
      <el-table-column prop="createTime" label="创建时间">
        <template #default="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="scope">
          <el-button
            size="small"
            type="primary"
            @click="handleEdit(scope.row)"
          >编辑</el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加/编辑考勤对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
      destroy-on-close
    >
      <el-form
        :model="form"
        :rules="rules"
        label-width="100px"
        ref="formRef"
      >
        <el-form-item label="学生" prop="studentId">
          <el-select v-model="form.studentId" placeholder="请选择学生" style="width: 100%" filterable>
            <el-option
              v-for="item in studentOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="form.courseId" placeholder="请选择课程" style="width: 100%" filterable>
            <el-option
              v-for="item in courseOptions"
              :key="item.id"
              :label="item.courseName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="考勤日期" prop="attendanceDate">
          <el-date-picker
            v-model="form.attendanceDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
            value-format="YYYY-MM-DD"
            format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="考勤状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择考勤状态" style="width: 100%">
            <el-option
              v-for="item in statusOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 批量添加考勤对话框 -->
    <el-dialog
      v-model="batchDialogVisible"
      title="批量添加考勤"
      width="600px"
      destroy-on-close
    >
      <el-form
        :model="batchForm"
        :rules="batchRules"
        label-width="100px"
        ref="batchFormRef"
      >
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="batchForm.courseId" placeholder="请选择课程" style="width: 100%" filterable>
            <el-option
              v-for="item in courseOptions"
              :key="item.id"
              :label="item.courseName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="考勤日期" prop="attendanceDate">
          <el-date-picker
            v-model="batchForm.attendanceDate"
            type="date"
            placeholder="选择日期"
            style="width: 100%"
            value-format="YYYY-MM-DD"
            format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="考勤状态" prop="status">
          <el-select v-model="batchForm.status" placeholder="请选择考勤状态" style="width: 100%">
            <el-option
              v-for="item in statusOptions"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="batchForm.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="选择学生" prop="studentIds">
          <div class="student-selection">
            <div class="selection-header">
              <el-checkbox
                v-model="selectAll"
                :indeterminate="isIndeterminate"
                @change="handleSelectAllChange"
              >全选</el-checkbox>
            </div>
            <div class="selection-body">
              <el-checkbox-group v-model="batchForm.studentIds">
                <el-checkbox
                  v-for="student in studentOptions"
                  :key="student.id"
                  :label="student.id"
                >
                  {{ student.name }} ({{ student.studentNo }})
                </el-checkbox>
              </el-checkbox-group>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="batchDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitBatchForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'
import { useUserStore } from '@/store/user'
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
  studentId: '',
  courseId: '',
  status: '',
  startDate: '',
  endDate: ''
})
const searchFormRef = ref(null)

// 选项数据
const studentOptions = ref([])
const courseOptions = ref([])
const statusOptions = ref([])

// 表单对话框
const dialogVisible = ref(false)
const dialogTitle = ref('添加考勤')
const formRef = ref(null)
const form = reactive({
  id: '',
  studentId: '',
  courseId: '',
  attendanceDate: '',
  status: '',
  remark: ''
})

// 批量添加对话框
const batchDialogVisible = ref(false)
const batchFormRef = ref(null)
const batchForm = reactive({
  courseId: '',
  attendanceDate: '',
  status: '',
  remark: '',
  studentIds: []
})

// 全选状态
const selectAll = ref(false)
const isIndeterminate = ref(false)

// 表单验证规则
const rules = {
  studentId: [
    { required: true, message: '请选择学生', trigger: 'change' }
  ],
  courseId: [
    { required: true, message: '请选择课程', trigger: 'change' }
  ],
  attendanceDate: [
    { required: true, message: '请选择考勤日期', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择考勤状态', trigger: 'change' }
  ]
}

// 批量表单验证规则
const batchRules = {
  courseId: [
    { required: true, message: '请选择课程', trigger: 'change' }
  ],
  attendanceDate: [
    { required: true, message: '请选择考勤日期', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择考勤状态', trigger: 'change' }
  ],
  studentIds: [
    { type: 'array', required: true, message: '请至少选择一名学生', trigger: 'change' }
  ]
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

// 监听批量选择学生变化
watch(() => batchForm.studentIds, (newVal) => {
  const checkedCount = newVal.length
  selectAll.value = checkedCount === studentOptions.value.length
  isIndeterminate.value = checkedCount > 0 && checkedCount < studentOptions.value.length
})

// 初始化
onMounted(() => {
  fetchAttendance()
  fetchStudents()
  fetchCourses()
  fetchAttendanceStatuses()
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
      studentId: searchForm.studentId || null,
      courseId: searchForm.courseId || null,
      status: searchForm.status || null,
      startDate: searchForm.startDate || null,
      endDate: searchForm.endDate || null,
      teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null
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

// 获取学生列表
const fetchStudents = async () => {
  try {
    const params = {
      teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null
    }
    await request.get('/student/all', params, {
      onSuccess: (res) => {
        studentOptions.value = res || []
      }
    })
  } catch (error) {
    console.error('获取学生列表失败:', error)
  }
}

// 获取课程列表

const fetchCourses = async () => {
  const params = {
  teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null
}
  try {
    await request.get('/course/all', params, {
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

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchAttendance()
}

// 重置查询
const resetSearch = () => {
  // 先使用resetFields方法
  searchFormRef.value.resetFields()
  
  // 然后手动重置表单值
  searchForm.studentId = ''
  searchForm.courseId = ''
  searchForm.status = ''
  searchForm.startDate = ''
  searchForm.endDate = ''
  dateRange.value = []
  
  // 重置页码并重新获取数据
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

// 处理添加
const handleAdd = () => {
  dialogTitle.value = '添加考勤'
  Object.keys(form).forEach(key => {
    form[key] = ''
  })
  dialogVisible.value = true
}

// 处理编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑考勤'
  Object.keys(form).forEach(key => {
    form[key] = row[key]
  })
  dialogVisible.value = true
}

// 处理删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该考勤记录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/attendance/${row.id}`, {
        successMsg: '删除成功'
      })
      fetchAttendance()
    } catch (error) {
      console.error('删除考勤记录失败:', error)
    }
  }).catch(() => {})
}

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id) {
          // 更新
          await request.put(`/attendance/${form.id}`, {
            studentId: form.studentId,
            courseId: form.courseId,
            attendanceDate: form.attendanceDate,
            status: form.status,
            remark: form.remark
          }, {
            successMsg: '更新成功'
          })
        } else {
          // 新增
          await request.post('/attendance', {
            studentId: form.studentId,
            courseId: form.courseId,
            attendanceDate: form.attendanceDate,
            status: form.status,
            remark: form.remark
          }, {
            successMsg: '添加成功'
          })
        }
        dialogVisible.value = false
        fetchAttendance()
      } catch (error) {
        console.error('保存考勤记录失败:', error)
      }
    }
  })
}

// 处理批量添加
const handleBatchAdd = () => {
  Object.keys(batchForm).forEach(key => {
    if (key === 'studentIds') {
      batchForm.studentIds = []
    } else {
      batchForm[key] = ''
    }
  })
  selectAll.value = false
  isIndeterminate.value = false
  batchDialogVisible.value = true
}

// 处理全选变化
const handleSelectAllChange = (val) => {
  batchForm.studentIds = val ? studentOptions.value.map(item => item.id) : []
  isIndeterminate.value = false
}

// 提交批量表单
const submitBatchForm = () => {
  batchFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await request.post('/attendance/batch', {
          studentIds: batchForm.studentIds,
          courseId: batchForm.courseId,
          attendanceDate: batchForm.attendanceDate,
          status: batchForm.status,
          remark: batchForm.remark
        }, {
          successMsg: '批量添加成功'
        })
        batchDialogVisible.value = false
        fetchAttendance()
      } catch (error) {
        console.error('批量添加考勤记录失败:', error)
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.attendance-container {
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
  
  .el-table {
    margin-bottom: 20px;
  }
  
  .pagination-container {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }

  .student-selection {
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    
    .selection-header {
      padding: 8px 12px;
      border-bottom: 1px solid #dcdfe6;
      background-color: #f5f7fa;
    }
    
    .selection-body {
      padding: 12px;
      max-height: 200px;
      overflow-y: auto;
      
      .el-checkbox {
        display: block;
        margin-right: 0;
        margin-bottom: 8px;
      }
    }
  }
}
</style> 