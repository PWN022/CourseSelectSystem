<template>
  <div class="user-list-container">
    <!-- 搜索区域 -->
    <div class="search-area">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.username" placeholder="请输入用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="searchForm.name" placeholder="请输入姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="searchForm.roleCode" placeholder="请选择角色" clearable>
            <el-option
              v-for="(label, value) in ROLE_LABELS"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 操作区域 -->
    <div class="operation-area">
      <el-button type="primary" @click="handleAdd">添加用户</el-button>
      <el-button type="danger" :disabled="selectedIds.length === 0" @click="handleBatchDelete">批量删除</el-button>
    </div>

    <!-- 表格区域 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
      <el-table-column prop="phone" label="手机号" width="130"></el-table-column>
      <el-table-column prop="roleCode" label="角色" width="100">
        <template #default="scope">
          <el-tag :type="getRoleColor(scope.row.roleCode)">
            {{ ROLE_LABELS[scope.row.roleCode] || '未知' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间">
        <template #default="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="300" >
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
          <el-button type="warning" size="small" @click="handleResetPassword(scope.row)">重置密码</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页区域 -->
    <div class="pagination-area">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :current-page="currentPage"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      ></el-pagination>
    </div>

    <!-- 用户表单对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
      :close-on-click-modal="false"
    
    >
      <el-form
        :model="userForm"
        :rules="userFormRules"
        ref="userFormRef"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" placeholder="请输入用户名" :disabled="isEdit"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!isEdit">
          <el-input v-model="userForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="userForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="userForm.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleCode">
          <el-select v-model="userForm.roleCode" placeholder="请选择角色">
            <el-option
              v-for="(label, value) in ROLE_LABELS"
              :key="value"
              :label="label"
              :value="value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm" :loading="submitLoading">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 重置密码对话框 -->
    <el-dialog
      title="重置密码"
      v-model="resetPwdDialogVisible"
      width="400px"
      :close-on-click-modal="false"
    >
      <el-form
        :model="resetPwdForm"
        :rules="resetPwdRules"
        ref="resetPwdFormRef"
        label-width="100px"
      >
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="resetPwdForm.newPassword" type="password" placeholder="请输入新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="resetPwdForm.confirmPassword" type="password" placeholder="请确认密码"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetPwdDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitResetPwd" :loading="resetPwdLoading">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import request from '@/utils/request'
import {formatDateTime} from '@/utils/dateUtils'
import {getRoleColor, ROLE_LABELS} from '@/constants/userRoles'

// 表格数据
const tableData = ref([])
const loading = ref(false)
const selectedIds = ref([])

// 分页参数
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 搜索表单
const searchForm = reactive({
  username: '',
  name: '',
  roleCode: ''
})

// 用户表单
const userFormRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('添加用户')
const isEdit = ref(false)
const submitLoading = ref(false)

const userForm = reactive({
  id: null,
  username: '',
  password: '',
  email: '',
  phone: '',
  name: '',
 
  roleCode: '',
  status: 1
})

// 表单验证规则
const userFormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  roleCode: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

// 重置密码表单
const resetPwdFormRef = ref(null)
const resetPwdDialogVisible = ref(false)
const resetPwdLoading = ref(false)
const currentUserId = ref(null)

const resetPwdForm = reactive({
  newPassword: '',
  confirmPassword: ''
})

// 重置密码表单验证规则
const resetPwdRules = {
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== resetPwdForm.newPassword) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

// 初始化
onMounted(() => {
  fetchUsers()
})

// 获取用户列表
const fetchUsers = async () => {
  loading.value = true
  try {
    await request.get('/user/page', {
      currentPage: currentPage.value,
      size: pageSize.value,
      ...searchForm
    }, {
      onSuccess: (res) => {
        tableData.value = res.records
        total.value = res.total
      }
    })
  } catch (error) {
    console.error('获取用户列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  currentPage.value = 1
  fetchUsers()
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  handleSearch()
}

// 表格选择变化
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

// 添加用户
const handleAdd = () => {
   resetUserForm()
  dialogTitle.value = '添加用户'
  isEdit.value = false
 
  dialogVisible.value = true
}

// 编辑用户
const handleEdit = (row) => {
   resetUserForm()
  dialogTitle.value = '编辑用户'
  isEdit.value = true
 
  Object.keys(userForm).forEach(key => {
    if (key in row) {
      userForm[key] = row[key]
    }
  })
  dialogVisible.value = true
}

// 删除用户
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除用户 ${row.username} 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await request.delete(`/user/delete/${row.id}`, {
        successMsg: '删除成功'
      })
      fetchUsers()
    } catch (error) {
      console.error('删除用户失败:', error)
    }
  }).catch(() => {})
}

// 批量删除
const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要删除的用户')
    return
  }

  ElMessageBox.confirm(
    `确定要删除选中的 ${selectedIds.value.length} 个用户吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await request.delete('/user/deleteBatch?ids='+selectedIds.value, {
        successMsg: '批量删除成功'
      })
      fetchUsers()
    } catch (error) {
      console.error('批量删除用户失败:', error)
    }
  }).catch(() => {})
}

// 提交表单
const submitForm = () => {
  userFormRef.value.validate(async valid => {
    if (valid) {
      submitLoading.value = true
      try {
        if (isEdit.value) {
          // 编辑用户
          await request.put(`/user/${userForm.id}`, userForm, {
            successMsg: '更新成功'
          })
        } else {
          // 添加用户
          await request.post('/user/add', userForm, {
            successMsg: '添加成功'
          })
        }
        dialogVisible.value = false
        fetchUsers()
      } catch (error) {
        console.error('保存用户失败:', error)
      } finally {
        submitLoading.value = false
      }
    }
  })
}

// 重置用户表单
const resetUserForm = () => {
  userForm.id = null
  userForm.username = ''
  userForm.password = ''
  userForm.email = ''
  userForm.phone = ''
  userForm.name = ''
  userForm.roleCode = ''
  userForm.status = 1

  if (userFormRef.value) {
    userFormRef.value.resetFields()
  }
}

// 重置密码
const handleResetPassword = (row) => {
  currentUserId.value = row.id
  resetPwdForm.newPassword = ''
  resetPwdForm.confirmPassword = ''
  resetPwdDialogVisible.value = true
}

// 提交重置密码
const submitResetPwd = () => {
  resetPwdFormRef.value.validate(async valid => {
    if (valid) {
      resetPwdLoading.value = true
      try {
        await request.get('/user/forget', {
            email: tableData.value.find(item => item.id === currentUserId.value).email,
            newPassword: resetPwdForm.newPassword
        },{
          successMsg: '密码重置成功'
        })
        resetPwdDialogVisible.value = false
      } catch (error) {
        console.error('重置密码失败:', error)
      } finally {
        resetPwdLoading.value = false
      }
    }
  })
}

// 修改用户状态
const handleStatusChange = async (row) => {
  const originalStatus = row.status
  try {
    await request.put(`/user/status/${row.id}?status=${row.status}`, null, {
      successMsg: row.status === 1 ? '用户已启用' : '用户已禁用'
    })
  } catch (error) {
    console.error('修改用户状态失败:', error)
    // 恢复原状态
    row.status = originalStatus === 1 ? 0 : 1
    ElMessage.error('修改用户状态失败')
  }
}

// 分页大小变化
const handleSizeChange = (size) => {
  pageSize.value = size
  fetchUsers()
}

// 页码变化
const handleCurrentChange = (page) => {
  currentPage.value = page
  fetchUsers()
}
</script>

<style lang="scss" scoped>
.user-list-container {
  .search-area {
    margin-bottom: 20px;
  }

  .operation-area {
    margin-bottom: 20px;
    display: flex;
    justify-content: flex-start;
  }

  .pagination-area {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
}
</style> 