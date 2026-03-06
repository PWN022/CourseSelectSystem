<template>
  <div class="student-list-container">
    <!-- 添加教师提示信息 -->
    <el-alert
      v-if="userStore.isTeacher"
      title="提示"
      type="info"
      :closable="false"
      show-icon
      class="teacher-alert"
    >
      <p>您当前以教师身份登录，只能查看您担任班主任的班级学生信息。</p>
    </el-alert>

    <div class="search-form">
      <el-form :inline="true" :model="searchForm" ref="searchFormRef">
        <el-form-item label="学号">
          <el-input v-model="searchForm.studentNo" placeholder="请输入学号" clearable />
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="searchForm.classId" placeholder="请选择班级" clearable>
            <el-option
              v-for="item in classList"
              :key="item.id"
              :label="item.className"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button  type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button v-if="userStore.isAdmin" type="success" @click="handleAdd">添加学生</el-button>
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
      <el-table-column prop="studentNo" label="学号" width="120" />
      <!--后添加-->
      <el-table-column label="学生姓名" width="120">
        <template #default="scope">
          {{ scope.row?.name || scope.row?.username || '未命名' }}
        </template>
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="80">
        <template #default="scope">
          {{ getGenderLabel(scope.row.gender) }}
        </template>
      </el-table-column>
      <el-table-column prop="className" label="班级" width="150" />
      <el-table-column prop="address" label="家庭住址" min-width="200" />
      <el-table-column v-if="userStore.isAdmin" label="操作" width="200" fixed="right">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
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

    <!-- 学生表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑学生信息' : '添加学生信息'"
      width="650px"
      destroy-on-close
    >
      <el-form
        :model="form"
        :rules="rules"
        label-width="100px"
        ref="formRef"
        style="max-height: 60vh; overflow-y: auto;"
      >
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="form.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="M">男</el-radio>
            <el-radio label="F">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker
            v-model="form.birthDate"
            type="date"
            placeholder="选择出生日期"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="form.address" placeholder="请输入家庭住址" />
        </el-form-item>
        <el-form-item label="班级" prop="classId">
          <el-select v-model="form.classId" placeholder="请选择班级" style="width: 100%">
            <el-option
              v-for="item in classList"
              :key="item.id"
              :label="item.className"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="关联用户" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择关联用户" style="width: 100%">
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.username"
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'
import { useUserStore } from '@/store/user'
import { getGenderLabel, GENDER } from '@/constants/gender'

// 用户存储
const userStore = useUserStore()

// 表格数据
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const selectedRows = ref([])

// 搜索表单
const searchForm = reactive({
  studentNo: '',
  classId: ''
})
const searchFormRef = ref(null)

// 班级列表
const classList = ref([])
// 用户列表
const userList = ref([])

// 表单对话框
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref(null)
const form = reactive({
  id: null,
  studentNo: '',
  gender: GENDER.MALE,
  birthDate: '',
  idCard: '',
  address: '',
  classId: '',
  userId: ''
})

// 表单验证规则
const rules = {
  studentNo: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  classId: [
    { required: true, message: '请选择班级', trigger: 'change' }
  ],
  userId: [
    { required: true, message: '请选择关联用户', trigger: 'change' }
  ],
  idCard: [
    { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确的身份证号码', trigger: 'blur' }
  ]
}

// 初始化
onMounted(() => {
  fetchStudents()
  fetchClasses()
  fetchUsers()
})

// 获取学生列表
const fetchStudents = async () => {
const params = {
  currentPage: currentPage.value,
  size: pageSize.value,
  studentNo: searchForm.studentNo,
  classId: searchForm.classId,
  headerTeacherId: userStore.isTeacher ? String(userStore.teacherInfo?.id || '') : null
}

  loading.value = true
  try {
    await request.get('/student/page', params, {
      onSuccess: (res) => {
        tableData.value = res.records||[]
        total.value = res.total||0
      }
    })
  } catch (error) {
    console.error('获取学生列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取班级列表
const fetchClasses = async () => {
  try {
    await request.get('/class/all', {}, {
      onSuccess: (res) => {
        classList.value = res
      }
    })
  } catch (error) {
    console.error('获取班级列表失败:', error)
  }
}

// 获取用户列表
const fetchUsers = async () => {
  try {
    await request.get('/user/page', {
      currentPage: 1,
      size: 100,
      roleCode: 'STUDENT'
    }, {
      onSuccess: (res) => {
        userList.value = res.records || []
      }
    })
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
  }
}

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchStudents()
}

// 重置查询
const resetSearch = () => {
  searchFormRef.value.resetFields()
  currentPage.value = 1
  fetchStudents()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchStudents()
}

// 处理每页条数变化
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchStudents()
}

// 处理多选
const handleSelectionChange = (rows) => {
  selectedRows.value = rows
}

// 处理添加
const handleAdd = () => {
  isEdit.value = false
  Object.keys(form).forEach(key => {
    form[key] = key === 'gender' ? GENDER.MALE : ''
  })
  dialogVisible.value = true
}

// 处理编辑
const handleEdit = (row) => {
  isEdit.value = true
  Object.keys(form).forEach(key => {
    form[key] = row[key] !== undefined ? row[key] : (key === 'gender' ? GENDER.MALE : '')
  })
  dialogVisible.value = true
}

// 处理删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该学生信息吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/student/${row.id}`, {
        successMsg: '删除成功'
      })
      fetchStudents()
    } catch (error) {
      console.error('删除学生失败:', error)
    }
  }).catch(() => {})
}

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await request.put('/student', form, {
            successMsg: '更新成功'
          })
        } else {
          await request.post('/student', form, {
            successMsg: '添加成功'
          })
        }
        dialogVisible.value = false
        fetchStudents()
      } catch (error) {
        console.error('保存学生信息失败:', error)
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.student-list-container {
  padding: 20px;
  
  .teacher-alert {
    margin-bottom: 20px;
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