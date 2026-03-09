<template>
  <div class="score-container">
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
      <p>1. 您担任班主任的班级学生成绩</p>
      <p>2. 您所教授课程的学生成绩</p>
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
          <el-button type="success" @click="handleAdd">添加成绩</el-button>
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
      <el-table-column prop="teacherName" label="教师姓名" width="120" />
      <el-table-column prop="semester" label="学期" width="120" />
      <el-table-column prop="score" label="分数" width="80" />
      <el-table-column prop="grade" label="等级" width="80" />
      <el-table-column prop="comment" label="评语" width="150" />
      <el-table-column prop="createTime" label="录入时间">
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

    <!-- 添加/编辑成绩对话框 -->
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
        <el-form-item label="任课教师" prop="teacherId">
          <el-input
              v-if="userStore.isTeacher"
              :value="userStore.userInfo?.name"
              disabled
              placeholder="自动绑定当前教师"
          />

          <el-select
              v-else
              v-model="form.teacherId"
              placeholder="请选择任课教师"
              style="width: 100%"
          >
            <el-option
                v-for="item in teacherList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-select v-model="form.semester" placeholder="请选择学期" style="width: 100%">
            <el-option
              v-for="item in semesterOptions"
              :key="item"
              :label="item"
              :value="item"
            />
            <el-option key="new" label="+ 添加新学期" value="new" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.semester === 'new'" label="新学期" prop="newSemester">
          <el-input v-model="form.newSemester" placeholder="请输入新学期，如：2023-2024-1" />
        </el-form-item>
        <el-form-item label="分数" prop="score">
          <el-input-number v-model="form.score" :min="0" :max="100" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="评语" prop="comment">
          <el-input v-model="form.comment" type="textarea" :rows="3" placeholder="请输入评语" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {ElMessageBox} from 'element-plus'
import request from '@/utils/request'
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
  studentId: '',
  courseId: '',
  semester: ''
})
const searchFormRef = ref(null)

// 选项数据
const studentOptions = ref([])
const courseOptions = ref([])
const teacherOptions = ref([])
const semesterOptions = ref([])

// 表单对话框
const dialogVisible = ref(false)
const dialogTitle = ref('添加成绩')
const formRef = ref(null)
const form = reactive({
  id: '',
  studentId: '',
  courseId: '',
  teacherId: '',
  semester: '',
  newSemester: '',
  score: '',
  grade: '',
  comment: ''
})

// 表单验证规则
const rules = {
  studentId: [
    { required: true, message: '请选择学生', trigger: 'change' }
  ],
  courseId: [
    { required: true, message: '请选择课程', trigger: 'change' }
  ],
  teacherId: [
    { required: true, message: '请选择教师', trigger: 'change' }
  ],
  semester: [
    { required: true, message: '请选择学期', trigger: 'change' }
  ],
  newSemester: [
    { required: true, message: '请输入新学期', trigger: 'blur', validator: (rule, value, callback) => {
      if (form.semester === 'new' && !value) {
        callback(new Error('请输入新学期'))
      } else {
        callback()
      }
    }}
  ],
  score: [
    { required: true, message: '请输入分数', trigger: 'blur' }
  ]
}

// 初始化
onMounted(() => {
  fetchScores()
  fetchStudents()
  fetchCourses()
  fetchTeachers()
  fetchSemesters()
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
      pageSize: pageSize.value,
      studentId: searchForm.studentId || null,
      teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null,
      courseId: searchForm.courseId || null,
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

// 获取学生列表
const fetchStudents = async () => {
  const params = {
    teacherId: userStore.isTeacher ?  userStore.teacherInfo?.id:null
  }
  try {
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

// 获取教师列表
const fetchTeachers = async () => {
  try {
    await request.get('/teacher/all', {}, {
      onSuccess: (res) => {
        teacherOptions.value = res || []
      }
    })
  } catch (error) {
    console.error('获取教师列表失败:', error)
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

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchScores()
}

// 重置查询
const resetSearch = () => {
  // 先使用resetFields方法
  searchFormRef.value.resetFields()
  
  // 然后手动重置表单值
  searchForm.studentId = ''
  searchForm.courseId = ''
  searchForm.semester = ''
  
  // 重置页码并重新获取数据
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

// 处理添加
const handleAdd = () => {
  dialogTitle.value = '添加成绩'
  Object.keys(form).forEach(key => {
    form[key] = ''
  })

  // 底层数据：依然绑定教师表的 ID
  if (userStore.isTeacher && userStore.teacherInfo) {
    form.teacherId = userStore.teacherInfo.id;
  }

  dialogVisible.value = true
}

// 处理编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑成绩'
  Object.keys(form).forEach(key => {
    if (key !== 'newSemester') {
      form[key] = row[key]
    }
  })
  dialogVisible.value = true
}

// 处理删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该成绩记录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/score/${row.id}`, {
        successMsg: '删除成功'
      })
      fetchScores()
    } catch (error) {
      console.error('删除成绩记录失败:', error)
    }
  }).catch(() => {})
}

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      // 如果选择了新增学期
      if (form.semester === 'new' && form.newSemester) {
        form.semester = form.newSemester
      }
      
      try {
        if (form.id) {
          // 更新
          await request.put(`/score/${form.id}`, {
            studentId: form.studentId,
            courseId: form.courseId,
            teacherId: form.teacherId,
            semester: form.semester,
            score: form.score,
            comment: form.comment
          }, {
            successMsg: '更新成功'
          })
        } else {
          // 新增
          await request.post('/score', {
            studentId: form.studentId,
            courseId: form.courseId,
            teacherId: form.teacherId,
            semester: form.semester,
            score: form.score,
            comment: form.comment
          }, {
            successMsg: '添加成功'
          })
        }
        dialogVisible.value = false
        fetchScores()
        fetchSemesters() // 刷新学期列表
      } catch (error) {
        console.error('保存成绩失败:', error)
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.score-container {
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
}
</style> 