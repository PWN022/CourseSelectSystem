<template>
  <div class="class-list-container">
    <div class="search-form">
      <el-form :inline="true" :model="searchForm" ref="searchFormRef">
        <el-form-item label="班级名称">
          <el-input v-model="searchForm.className" placeholder="请输入班级名称" clearable />
        </el-form-item>
        <el-form-item label="年级">
          <el-select v-model="searchForm.grade" placeholder="请选择年级" clearable>
            <el-option
              v-for="item in gradeList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="专业">
          <el-select v-model="searchForm.major" placeholder="请选择专业" clearable>
            <el-option
              v-for="item in majorList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="handleAdd">添加班级</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table
      v-loading="loading"
      :data="tableData"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="className" label="班级名称" width="180" />
      <el-table-column prop="grade" label="年级" width="120" />
      <el-table-column prop="major" label="专业" width="180" />
      <el-table-column prop="headTeacherName" label="班主任" width="150" />
      <el-table-column prop="createTime" label="创建时间">
        <template #default="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250" fixed="right">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row)"
          >删除</el-button>
          <el-button
            size="small"
            type="primary"
            @click="handleViewStudents(scope.row)"
          >查看学生</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 班级表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑班级信息' : '添加班级信息'"
      width="500px"
      destroy-on-close
    >
      <el-form
        :model="form"
        :rules="rules"
        label-width="100px"
        ref="formRef"
      >
        <el-form-item label="班级名称" prop="className">
          <el-input v-model="form.className" placeholder="请输入班级名称" />
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="form.grade" placeholder="请输入年级，如：2023级" />
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major" placeholder="请输入专业，如：计算机科学与技术" />
        </el-form-item>
        <el-form-item label="班主任" prop="headTeacherId">
          <el-select v-model="form.headTeacherId" placeholder="请选择班主任" style="width: 100%">
            <el-option
              v-for="item in teacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 班级学生对话框 -->
    <el-dialog
      v-model="studentsDialogVisible"
      :title="selectedClass ? `${selectedClass.className}的学生列表` : '学生列表'"
      width="900px"
    >
      <el-table
        v-loading="studentsLoading"
        :data="studentsList"
        border
        style="width: 100%"
      >
        <el-table-column prop="studentNo" label="学号" width="120" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="scope">
            {{ scope.row.gender === 'M' ? '男' : scope.row.gender === 'F' ? '女' : '未知' }}
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="联系电话" width="150" />
        <el-table-column prop="email" label="电子邮箱" width="200" />
        <el-table-column prop="address" label="家庭住址" min-width="200" />
      </el-table>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="studentsDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'

// 表格数据
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const selectedRows = ref([])

// 搜索表单
const searchForm = reactive({
  className: '',
  grade: '',
  major: ''
})
const searchFormRef = ref(null)

// 年级和专业列表
const gradeList = ref([])
const majorList = ref([])
// 教师列表
const teacherList = ref([])

// 表单对话框
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref(null)
const form = reactive({
  id: null,
  className: '',
  grade: '',
  major: '',
  headTeacherId: ''
})

// 学生对话框
const studentsDialogVisible = ref(false)
const studentsLoading = ref(false)
const studentsList = ref([])
const selectedClass = ref(null)

// 表单验证规则
const rules = {
  className: [
    { required: true, message: '请输入班级名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  grade: [
    { required: true, message: '请输入年级', trigger: 'blur' }
  ],
  major: [
    { required: true, message: '请输入专业', trigger: 'blur' }
  ]
}

// 初始化
onMounted(() => {
  fetchClasses()
  fetchGrades()
  fetchMajors()
  fetchTeachers()
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

// 获取班级列表
const fetchClasses = async () => {
  loading.value = true
  try {
    await request.get('/class/page', {
      currentPage: currentPage.value,
      size: pageSize.value,
      className: searchForm.className,
      grade: searchForm.grade,
      major: searchForm.major
    }, {
      onSuccess: (res) => {
        tableData.value = res.records
        total.value = res.total
      }
    })
  } catch (error) {
    console.error('获取班级列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取年级列表
const fetchGrades = async () => {
  try {
    await request.get('/class/grades', {}, {
      onSuccess: (res) => {
        gradeList.value = res
      }
    })
  } catch (error) {
    console.error('获取年级列表失败:', error)
  }
}

// 获取专业列表
const fetchMajors = async () => {
  try {
    await request.get('/class/majors', {}, {
      onSuccess: (res) => {
        majorList.value = res
      }
    })
  } catch (error) {
    console.error('获取专业列表失败:', error)
  }
}

// 获取教师列表
const fetchTeachers = async () => {
  try {
    await request.get('/teacher/all', {}, {
      onSuccess: (res) => {
        teacherList.value = res||[]
      }
    })
  } catch (error) {
    console.error('获取教师列表失败:', error)
  }
}

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchClasses()
}

// 重置查询
const resetSearch = () => {
  searchFormRef.value.resetFields()
  currentPage.value = 1
  fetchClasses()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchClasses()
}

// 处理每页条数变化
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchClasses()
}

// 处理多选
const handleSelectionChange = (rows) => {
  selectedRows.value = rows
}

// 处理添加
const handleAdd = () => {
  isEdit.value = false
  Object.keys(form).forEach(key => {
    form[key] = ''
  })
  dialogVisible.value = true
}

// 处理编辑
const handleEdit = (row) => {
  isEdit.value = true
  Object.keys(form).forEach(key => {
    form[key] = row[key]
  })
  dialogVisible.value = true
}

// 处理删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该班级吗？删除后将无法恢复，且班级下的学生将无法关联到此班级。', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/class/${row.id}`, {
        successMsg: '删除成功'
      })
      fetchClasses()
    } catch (error) {
      console.error('删除班级失败:', error)
    }
  }).catch(() => {})
}

// 查看班级学生
const handleViewStudents = async (row) => {
  selectedClass.value = row
  studentsDialogVisible.value = true
  studentsLoading.value = true
  
  try {
    await request.get(`/student/class/${row.id}`, {}, {
      onSuccess: (res) => {
        studentsList.value = res
      }
    })
  } catch (error) {
    console.error('获取班级学生失败:', error)
  } finally {
    studentsLoading.value = false
  }
}

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await request.put('/class', form, {
            successMsg: '更新成功'
          })
        } else {
          await request.post('/class', form, {
            successMsg: '添加成功'
          })
        }
        dialogVisible.value = false
        fetchClasses()
      } catch (error) {
        console.error('保存班级信息失败:', error)
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.class-list-container {
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