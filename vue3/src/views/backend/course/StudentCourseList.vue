<template>
  <div class="student-course-container">
    <div class="search-form">
      <el-form :inline="true" :model="searchForm" ref="searchFormRef">
        <el-form-item label="学生">
          <el-select v-model="searchForm.studentId" placeholder="请选择学生" clearable filterable>
            <el-option
              v-for="item in studentOptions"
              :key="item.id"
              :label="`${item.studentNo} - ${item.name}`"
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
        <el-form-item label="教师">
          <el-select v-model="searchForm.teacherId" placeholder="请选择教师" clearable filterable>
            <el-option
              v-for="item in teacherOptions"
              :key="item.id"
              :label="item.name"
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
          <el-button type="success" @click="handleAdd">学生选课</el-button>
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
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column prop="teacherName" label="授课教师" width="120" />
      <el-table-column prop="semester" label="学期" width="120" />
      <el-table-column prop="createTime" label="选课时间" >
        <template #default="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="scope">
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row)"
          >退课</el-button>
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

    <!-- 学生选课对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="学生选课"
      width="600px"
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
              :label="`${item.studentNo} - ${item.name}`"
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
          </el-select>
        </el-form-item>
        
        <el-form-item label="课程选择" prop="courseTeachers">
          <el-table
            :data="availableCourses"
            border
            style="width: 100%"
            @selection-change="handleCoursesSelectionChange"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="courseName" label="课程名称" />
            <el-table-column prop="teacherName" label="授课教师" />
            <el-table-column prop="credit" label="学分" width="80" />
          </el-table>
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
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'

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
  teacherId: '',
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
const formRef = ref(null)
const form = reactive({
  studentId: '',
  semester: '',
  courseTeachers: [] // 存储选中的课程和教师关系
})

// 可选课程列表
const availableCourses = ref([])
const selectedCourses = ref([])

// 表单验证规则
const rules = {
  studentId: [
    { required: true, message: '请选择学生', trigger: 'change' }
  ],
  semester: [
    { required: true, message: '请选择学期', trigger: 'change' }
  ],
  courseTeachers: [
    { required: true, message: '请至少选择一门课程', trigger: 'change', validator: (rule, value, callback) => {
      if (selectedCourses.value.length === 0) {
        callback(new Error('请至少选择一门课程'))
      } else {
        callback()
      }
    }}
  ]
}

// 初始化
onMounted(() => {
  fetchStudentCourses()
  fetchStudents()
  fetchCourses()
  fetchTeachers()
  fetchSemesters()
})

// 监听学期和学生变化，加载可选课程
watch([() => form.studentId, () => form.semester], ([newStudentId, newSemester]) => {
  if (newStudentId && newSemester) {
    fetchAvailableCourses(newStudentId, newSemester)
  } else {
    availableCourses.value = []
  }
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

// 获取学生选课列表
const fetchStudentCourses = async () => {
  loading.value = true
  try {
    await request.get('/student-course/page', {
      currentPage: currentPage.value,
      size: pageSize.value,
      studentId: searchForm.studentId || null,
      courseId: searchForm.courseId || null,
      teacherId: searchForm.teacherId || null,
      semester: searchForm.semester || null
    }, {
      onSuccess: (res) => {
        tableData.value = res.records
        total.value = res.total
      }
    })
  } catch (error) {
    console.error('获取学生选课列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取学生列表
const fetchStudents = async () => {
  try {
    await request.get('/student/all', {}, {
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
    await request.get('/teacher-course/semesters', {}, {
      onSuccess: (res) => {
        semesterOptions.value = res || []
      }
    })
  } catch (error) {
    console.error('获取学期列表失败:', error)
  }
}

// 获取可选课程列表
const fetchAvailableCourses = async (studentId, semester) => {
  try {
    // 获取该学期所有教师课程关联
    await request.get('/teacher-course/page', {
      currentPage: 1,
      size: 1000,
      semester: semester
    }, {
      onSuccess: async (res) => {
        const teacherCourses = res.records || []
        
        // 获取该学生已选课程
        await request.get(`/student-course/student/${studentId}`, {
          semester: semester
        }, {
          onSuccess: (studentCourses) => {
            // 过滤掉学生已选的课程
            const selectedCourseIds = (studentCourses || []).map(sc => sc.courseId)
            
            // 构建可选课程列表
            availableCourses.value = teacherCourses.map(tc => {
              const course = courseOptions.value.find(c => c.id === tc.courseId)
              return {
                courseId: tc.courseId,
                teacherId: tc.teacherId,
                courseName: tc.courseName,
                teacherName: tc.teacherName,
                credit: course ? course.credit : 0,
                disabled: selectedCourseIds.includes(tc.courseId)
              }
            }).filter(c => !c.disabled) // 过滤掉已选课程
          }
        })
      }
    })
  } catch (error) {
    console.error('获取可选课程列表失败:', error)
  }
}

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchStudentCourses()
}

// 重置查询
const resetSearch = () => {
  // 先使用resetFields方法
  searchFormRef.value.resetFields()
  
  // 然后手动重置表单值
  searchForm.studentId = ''
  searchForm.courseId = ''
  searchForm.teacherId = ''
  searchForm.semester = ''
  
  // 重置页码并重新获取数据
  currentPage.value = 1
  fetchStudentCourses()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchStudentCourses()
}

// 处理每页条数变化
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchStudentCourses()
}

// 处理添加
const handleAdd = () => {
  Object.keys(form).forEach(key => {
    form[key] = ''
  })
  selectedCourses.value = []
  dialogVisible.value = true
}

// 处理课程选择变化
const handleCoursesSelectionChange = (selection) => {
  selectedCourses.value = selection
}

// 处理删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要退选该课程吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/student-course/${row.id}`, {
        successMsg: '退课成功'
      })
      fetchStudentCourses()
    } catch (error) {
      console.error('退课失败:', error)
    }
  }).catch(() => {})
}

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 准备批量选课数据
        const courseIds = selectedCourses.value.map(course => course.courseId)
        const teacherIds = selectedCourses.value.map(course => course.teacherId)
        
        await request.post('/student-course/batch', {
          studentId: form.studentId,
          courseIds: courseIds,
          teacherIds: teacherIds,
          semester: form.semester
        }, {
          successMsg: '选课成功'
        })
        dialogVisible.value = false
        fetchStudentCourses()
      } catch (error) {
        console.error('选课失败:', error)
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.student-course-container {
  padding: 20px;
  
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