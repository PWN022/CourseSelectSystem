<template>
  <div class="teacher-list-container">
    <div class="search-form">
      <el-form :inline="true" :model="searchForm" ref="searchFormRef">
        <el-form-item label="教师编号">
          <el-input v-model="searchForm.teacherNo" placeholder="请输入教师编号" clearable />
        </el-form-item>
        <el-form-item label="职称">
          <el-select v-model="searchForm.title" placeholder="请选择职称" clearable>
            <el-option
              v-for="item in titleList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="handleAdd">添加教师</el-button>
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
      <el-table-column prop="teacherNo" label="教师编号" width="120" />
      <el-table-column prop="gender" label="性别" width="80">
        <template #default="scope">
          {{ getGenderLabel(scope.row.gender) }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="职称" width="120" />
      <el-table-column prop="username" label="关联用户" width="150" />
      <el-table-column prop="createTime" label="创建时间">
        <template #default="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
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

    <!-- 教师表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑教师信息' : '添加教师信息'"
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
        <el-form-item label="教师编号" prop="teacherNo">
          <el-input v-model="form.teacherNo" placeholder="请输入教师编号" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="M">男</el-radio>
            <el-radio label="F">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-input v-model="form.title" placeholder="请输入职称，如：教授、副教授" />
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
import { ref, reactive, onMounted } from 'vue'
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
  teacherNo: '',
  title: ''
})
const searchFormRef = ref(null)

// 职称列表
const titleList = ref([])
// 用户列表
const userList = ref([])

// 表单对话框
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref(null)
const form = reactive({
  id: null,
  teacherNo: '',
  gender: GENDER.MALE,
  title: '',
  userId: ''
})

// 表单验证规则
const rules = {
  teacherNo: [
    { required: true, message: '请输入教师编号', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  userId: [
    { required: true, message: '请选择关联用户', trigger: 'change' }
  ]
}

// 初始化
onMounted(() => {
  fetchTeachers()
  fetchTitles()
  fetchUsers()
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

// 获取教师列表
const fetchTeachers = async () => {
  loading.value = true
  try {
    await request.get('/teacher/page', {
      currentPage: currentPage.value,
      size: pageSize.value,
      teacherNo: searchForm.teacherNo,
      title: searchForm.title
    }, {
      onSuccess: (res) => {
        tableData.value = res.records
        total.value = res.total
      }
    })
  } catch (error) {
    console.error('获取教师列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 获取职称列表
const fetchTitles = async () => {
  try {
    await request.get('/teacher/titles', {}, {
      onSuccess: (res) => {
        titleList.value = res
      }
    })
  } catch (error) {
    console.error('获取职称列表失败:', error)
  }
}

// 获取用户列表
const fetchUsers = async () => {
  try {
    await request.get('/user/page', {
      currentPage: 1,
      size: 1000, // 获取足够多的用户
      roleCode: 'TEACHER' // 只获取教师角色的用户
    }, {
      onSuccess: (res) => {
        userList.value = res.records
      }
    })
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

// 处理查询
const handleSearch = () => {
  currentPage.value = 1
  fetchTeachers()
}

// 重置查询
const resetSearch = () => {
  searchFormRef.value.resetFields()
  currentPage.value = 1
  fetchTeachers()
}

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchTeachers()
}

// 处理每页条数变化
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  fetchTeachers()
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
    form[key] = row[key]
  })
  dialogVisible.value = true
}

// 处理删除
const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该教师信息吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request.delete(`/teacher/${row.id}`, {
        successMsg: '删除成功'
      })
      fetchTeachers()
    } catch (error) {
      console.error('删除教师失败:', error)
    }
  }).catch(() => {})
}

// 提交表单
const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await request.put('/teacher', form, {
            successMsg: '更新成功'
          })
        } else {
          await request.post('/teacher', form, {
            successMsg: '添加成功'
          })
        }
        dialogVisible.value = false
        fetchTeachers()
      } catch (error) {
        console.error('保存教师信息失败:', error)
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.teacher-list-container {
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