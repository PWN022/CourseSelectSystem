<template>
  <div class="person-info">
    <el-card class="info-card">
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
          <el-button type="primary" @click="handleEdit" v-if="!isEditing">
            编辑信息
          </el-button>
          <div v-else>
            <el-button type="primary" @click="handleSave" :loading="saving">
              保存
            </el-button>
            <el-button @click="handleCancel">取消</el-button>
          </div>
        </div>
      </template>

      <div class="info-content">
        <!-- 添加头像上传部分 -->
        <div class="avatar-container">
          <el-avatar :size="100" :src="avatarUrl" @error="() => false" />
          <el-upload
            class="avatar-uploader"
            action="#"
            :auto-upload="true"
            :show-file-list="false"
            :http-request="customUploadAvatar"
            :before-upload="beforeAvatarUpload"
            :disabled="!isEditing"
          >
            <el-button size="small" type="primary" :disabled="!isEditing">更换头像</el-button>
          </el-upload>
        </div>

        <el-form 
          ref="formRef"
          :model="form"
          :rules="rules"
          label-width="100px"
          :disabled="!isEditing"
          class="info-form"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" disabled />
          </el-form-item>

          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入姓名" />
          </el-form-item>

     

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入邮箱" />
          </el-form-item>

          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号" />
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <!-- 教师特有信息卡片 -->
    <el-card v-if="userStore.isTeacher" class="info-card">
      <template #header>
        <div class="card-header">
          <span>教师信息</span>
        </div>
      </template>
      <el-form
        ref="teacherFormRef"
        :model="teacherForm"
        :rules="teacherRules"
        label-width="100px"
        :disabled="!isEditing"
      >
        <el-form-item label="教师编号" prop="teacherNo">
          <el-input v-model="teacherForm.teacherNo" disabled />
        </el-form-item>

        <el-form-item label="职称" prop="title">
          <el-input v-model="teacherForm.title" placeholder="请输入职称" />
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 学生特有信息卡片 -->
    <el-card v-if="userStore.isStudent" class="info-card">
      <template #header>
        <div class="card-header">
          <span>学生信息</span>
        </div>
      </template>
      <el-form
        ref="studentFormRef"
        :model="studentForm"
        :rules="studentRules"
        label-width="100px"
        :disabled="!isEditing"
      >
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="studentForm.studentNo" disabled />
        </el-form-item>

        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="studentForm.idCard" placeholder="请输入身份证号" />
        </el-form-item>

        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker
            v-model="studentForm.birthDate"
            type="date"
            placeholder="选择出生日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="studentForm.address" placeholder="请输入家庭住址" />
        </el-form-item>

        <el-form-item label="班级" prop="className">
          <el-input v-model="studentForm.className" disabled />
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 班主任信息卡片 -->
    <el-card v-if="userStore.isStudent && studentForm.headTeacherId" class="info-card">
      <template #header>
        <div class="card-header">
          <span>班主任信息</span>
        </div>
      </template>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="姓名">{{ studentForm.headTeacherName }}</el-descriptions-item>
        <el-descriptions-item label="职称">{{ studentForm.headTeacherTitle }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ studentForm.headTeacherPhone }}</el-descriptions-item>
        <el-descriptions-item label="电子邮箱">{{ studentForm.headTeacherEmail }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card class="password-card">
      <template #header>
        <div class="card-header">
          <span>修改密码</span>
        </div>
      </template>

      <el-form
        ref="passwordFormRef"
        :model="passwordForm"
        :rules="passwordRules"
        label-width="100px"
      >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input 
            v-model="passwordForm.oldPassword" 
            type="password"
            placeholder="请输入原密码"
            show-password
          />
        </el-form-item>

        <el-form-item label="新密码" prop="newPassword">
          <el-input 
            v-model="passwordForm.newPassword" 
            type="password"
            placeholder="请输入新密码"
            show-password
          />
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input 
            v-model="passwordForm.confirmPassword" 
            type="password"
            placeholder="请再次输入新密码"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleChangePassword"
            :loading="changingPassword"
          >
            修改密码
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 教师课程和班级信息卡片 -->
    <div v-if="userStore.isTeacher" class="teacher-info">
      <el-card class="info-card">
        <template #header>
          <div class="card-header">
            <span>我的课程</span>
          </div>
        </template>
        <div class="course-list">
          <el-empty v-if="teacherCourses.length === 0" description="暂无教授课程" />
          <el-table v-else :data="teacherCourses" border stripe>
            <el-table-column prop="courseName" label="课程名称" />
            <el-table-column prop="courseCode" label="课程代码" />
            <el-table-column prop="semester" label="学期" />
            <el-table-column prop="courseType" label="课程类型" />
            <el-table-column prop="credit" label="学分" />
          </el-table>
        </div>
      </el-card>

      <el-card class="info-card">
        <template #header>
          <div class="card-header">
            <span>我的班级</span>
          </div>
        </template>
        <div class="class-list">
          <el-empty v-if="teacherClasses.length === 0" description="暂无担任班主任的班级" />
          <el-table v-else :data="teacherClasses" border stripe>
            <el-table-column prop="className" label="班级名称" />
            <el-table-column prop="grade" label="年级" />
            <el-table-column prop="major" label="专业" />
          </el-table>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, reactive, ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {useUserStore} from '@/store/user'
import request from '@/utils/request'

const baseAPI = import.meta.env.VITE_BASE_API || '/api'
const userStore = useUserStore()
const formRef = ref(null)
const teacherFormRef = ref(null)
const studentFormRef = ref(null)
const passwordFormRef = ref(null)
const isEditing = ref(false)
const saving = ref(false)
const changingPassword = ref(false)

// 表单数据
const form = reactive({
  id: '',
  username: '',
  name: '',
  email: '',
  phone: '',
  avatar: ''
})

// 头像地址
const avatarUrl = computed(() => {
  return form.avatar ? baseAPI + form.avatar : '';
})

// 密码表单
const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 教师表单数据
const teacherForm = reactive({
  id: '',
  teacherNo: '',
  title: ''
})

// 学生表单数据
const studentForm = reactive({
  id: '',
  studentNo: '',
  idCard: '',
  birthDate: '',
  address: '',
  classId: '',
  className: '',
  headTeacherId: '',
  headTeacherName: '',
  headTeacherTitle: '',
  headTeacherPhone: '',
  headTeacherEmail: ''
})

// 基本表单验证规则
const rules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ]
}

// 教师表单验证规则
const teacherRules = {
  title: [
    { required: true, message: '请输入职称', trigger: 'blur' }
  ]
}

// 学生表单验证规则
const studentRules = {
  idCard: [
    { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确的身份证号', trigger: 'blur' }
  ],
  birthDate: [
    { required: true, message: '请选择出生日期', trigger: 'change' }
  ],
  address: [
    { required: true, message: '请输入家庭住址', trigger: 'blur' }
  ]
}

// 密码验证规则
const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

// 教师课程和班级数据
const teacherCourses = ref([])
const teacherClasses = ref([])

// 获取用户信息
const fetchUserInfo = async () => {
  try {
    // 获取当前用户的最新信息
    const userId = userStore.userInfo.id
    const res = await request.get(`/user/${userId}`, null, {
      showDefaultMsg: false
    })
    
    // 更新基本用户信息
    form.id = res.id || userStore.userInfo.id
    form.username = res.username || ''
    form.name = res.name || ''
    form.email = res.email || ''
    form.phone = res.phone || ''
    form.avatar = res.avatar || ''
    
    // 如果是教师，获取教师信息
    if (userStore.isTeacher) {
      const teacherRes = await request.get(`/teacher/userId/${userId}`, null, {
        showDefaultMsg: false
      })
      if (teacherRes) {
        teacherForm.id = teacherRes.id
        teacherForm.teacherNo = teacherRes.teacherNo
        teacherForm.title = teacherRes.title
      }
    }
    
    // 如果是学生，获取学生信息
    if (userStore.isStudent) {
      const studentRes = await request.get(`/student/userId/${userId}`, null, {
        showDefaultMsg: false
      })
      if (studentRes) {
        studentForm.id = studentRes.id
        studentForm.studentNo = studentRes.studentNo
        studentForm.idCard = studentRes.idCard
        studentForm.birthDate = studentRes.birthDate
        studentForm.address = studentRes.address
        studentForm.classId = studentRes.classId
        studentForm.className = studentRes.className
        studentForm.headTeacherId = studentRes.headTeacherId
        studentForm.headTeacherName = studentRes.headTeacherName
        studentForm.headTeacherTitle = studentRes.headTeacherTitle
        studentForm.headTeacherPhone = studentRes.headTeacherPhone
        studentForm.headTeacherEmail = studentRes.headTeacherEmail
      }
    }
    
    console.log('用户信息加载成功:', form)
  } catch (error) {
    console.error('获取用户信息失败:', error)
    ElMessage.error('获取用户信息失败')
  }
}

// 获取教师课程信息
const fetchTeacherCourses = async () => {
  if (!userStore.isTeacher) return
  
  try {
    await request.get(`/teacher-course/teacher/${userStore.teacherInfo.id}`, null, {
      showDefaultMsg: false,
      onSuccess: (res) => {
        teacherCourses.value = res || []
      }
    })
  } catch (error) {
    console.error('获取教师课程信息失败:', error)
  }
}

// 获取教师班级信息
const fetchTeacherClasses = async () => {
  if (!userStore.isTeacher) return
  
  try {
    await request.get('/class/all', {
      headerTeacherId: userStore.teacherInfo.id
    }, {
      showDefaultMsg: false,
      onSuccess: (res) => {
        teacherClasses.value = res || []
      }
    })
  } catch (error) {
    console.error('获取教师班级信息失败:', error)
  }
}

// 上传头像前的校验
const beforeAvatarUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'
  const isPNG = file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG && !isPNG) {
    ElMessage.error('头像只能是 JPG 或 PNG 格式!')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('头像大小不能超过 2MB!')
    return false
  }
  return true
}

// 自定义头像上传方法
const customUploadAvatar = async (options) => {
  try {
    const { file } = options

    // 创建 FormData 对象
    const formData = new FormData()
    formData.append('file', file)

    // 设置自定义上传选项
    const uploadOptions = {
      headers: {
        token: localStorage.getItem('token') || '',
      },
      // 不进行JSON处理
      transformRequest: [(data) => data],
      // 自定义成功消息
      successMsg: '头像上传成功',
      // 自定义错误消息
      errorMsg: '头像上传失败',
      // 成功回调
      onSuccess: async (data) => {
        // 更新用户头像
        form.avatar = data

        // 保存到后端
        await updateUserAvatar(data)

        // 通知上传成功
        options.onSuccess({ data })
      },
      // 错误回调
      onError: (error) => {
        console.error('头像上传错误:', error)
        options.onError(new Error(error.message || '上传失败'))
      },
    }

    // 发送上传请求
    await request.post('/file/upload/img', formData, uploadOptions)
  } catch (error) {
    options.onError(error)
    console.error('头像上传过程发生错误:', error)
  }
}

// 更新用户头像信息
const updateUserAvatar = async (avatarPath) => {
  try {
    await request.put(
      `/user/${form.id}`,
      { avatar: avatarPath },
      {
        showDefaultMsg: false,
        successMsg: '头像更新成功',
        onSuccess: (data) => {
          // 更新本地用户信息
          const updatedUserInfo = { ...userStore.userInfo, avatar: avatarPath }
          userStore.updateUserInfo(updatedUserInfo)
        },
        onError: (error) => {
          console.error('头像信息保存失败', error)
          ElMessage.error('头像信息保存失败')
        },
      }
    )
  } catch (error) {
    console.error('头像信息保存失败', error)
    ElMessage.error('头像信息保存失败')
    throw error
  }
}

// 编辑信息
const handleEdit = () => {
  isEditing.value = true
}

// 取消编辑
const handleCancel = () => {
  isEditing.value = false
  fetchUserInfo() // 重新获取数据，恢复原值
}

// 验证所有表单
const validateAllForms = async () => {
  // 验证基本表单
  if (!formRef.value) {
    throw new Error('表单引用不存在')
  }
  await formRef.value.validate()

  // 如果是教师，验证教师表单
  if (userStore.isTeacher && teacherFormRef.value) {
    await teacherFormRef.value.validate()
  }

  // 如果是学生，验证学生表单
  if (userStore.isStudent && studentFormRef.value) {
    await studentFormRef.value.validate()
  }
}

// 保存信息
const handleSave = async () => {
  if (!formRef.value) return
  
  try {
    // 验证所有相关表单
    await validateAllForms()
    
    saving.value = true
    
    // 保存基本用户信息
    await request.put(
      `/user/${form.id}`,
      {
        name: form.name,
        email: form.email,
        phone: form.phone
      },
      {
        showDefaultMsg: false,
        successMsg: '个人信息更新成功',
        onSuccess: async (data) => {
          // 如果是教师，更新教师信息
          if (userStore.isTeacher && teacherForm.id) {
            await request.put('/teacher', {
              id: teacherForm.id,
              title: teacherForm.title
            })
          }
          
          // 如果是学生，更新学生信息
          if (userStore.isStudent && studentForm.id) {
            await request.put('/student', {
              id: studentForm.id,
              idCard: studentForm.idCard,
              birthDate: studentForm.birthDate,
              classId: studentForm.classId,
              address: studentForm.address
            })
          }
          
          isEditing.value = false
          // 更新store中的用户信息
          userStore.updateUserInfo({
            ...userStore.userInfo,
            name: form.name,
            email: form.email,
            phone: form.phone
          })
        },
        onError: (error) => {
          console.error('更新用户信息失败:', error)
          ElMessage.error('更新用户信息失败')
        }
      }
    )
  } catch (error) {
    console.error('更新用户信息失败:', error)
    ElMessage.error('更新用户信息失败')
  } finally {
    saving.value = false
  }
}

// 修改密码
const handleChangePassword = async () => {
  if (!passwordFormRef.value) return

  try {
    await passwordFormRef.value.validate()
    changingPassword.value = true

    await request.put(
      `/user/password/${form.id}`,
      {
        oldPassword: passwordForm.oldPassword,
        newPassword: passwordForm.newPassword
      },
      {
        showDefaultMsg: false,
        successMsg: '密码修改成功',
        onSuccess: (data) => {
          // 清空密码表单
          passwordFormRef.value.resetFields()
          
          // 提示用户重新登录
          ElMessageBox.confirm('密码已修改，需要重新登录', '提示', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            // 清除用户信息并跳转到登录页
            userStore.clearUserInfo()
            window.location.href = '/login'
          })
        },
        onError: (error) => {
          console.error('密码修改失败', error)
          ElMessage.error('密码修改失败')
        }
      }
    )
  } catch (error) {
    console.error('修改密码失败:', error)
    ElMessage.error('修改密码失败')
  } finally {
    changingPassword.value = false
  }
}

onMounted(() => {
  fetchUserInfo()
  fetchTeacherCourses()
  fetchTeacherClasses()
})
</script>

<style lang="scss" scoped>
.person-info {
  padding: 20px;
  
  .info-card,
  .password-card,
  .teacher-info {
    margin-bottom: 20px;
    border-radius: 8px;
    
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }

  .teacher-info {
    .course-list,
    .class-list {
      margin-top: 10px;
    }
  }

  .info-content {
    display: flex;
    flex-direction: column;
    gap: 30px;
    
    @media (min-width: 768px) {
      flex-direction: row;
    }
  }
  
  .avatar-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 15px;
    
    .avatar-uploader {
      text-align: center;
      margin-top: 10px;
    }
  }
  
  .info-form {
    flex: 1;
    max-width: 600px;
    margin: 0 auto;
  }

  :deep(.el-form) {
    max-width: 500px;
    margin: 0 auto;
  }
}
</style> 