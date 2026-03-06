<template>
  <Auth 
    :formData="registerForm" 
    :rules="rules" 
    :loading="loading"
    submitText="注册账号"
    @submit="handleSubmit"
  >
    <template #form-items>
      <el-form-item prop="roleCode">
        <el-select v-model="registerForm.roleCode" placeholder="请选择身份" @change="handleRoleChange">
          <el-option label="学生" value="STUDENT" />
          <el-option label="教师" value="TEACHER" />
        </el-select>
      </el-form-item>
      <el-form-item prop="username">
        <el-input 
          v-model="registerForm.username"
          :prefix-icon="User"
          placeholder="请输入账号">
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input 
          v-model="registerForm.password"
          :prefix-icon="Lock"
          type="password"
          placeholder="请输入密码">
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input 
          v-model="registerForm.confirmPassword"
          :prefix-icon="Lock"
          type="password"
          placeholder="请再次输入密码">
        </el-input>
      </el-form-item>
      <el-form-item prop="name">
        <el-input 
          v-model="registerForm.name"
          :prefix-icon="UserFilled"
          placeholder="请输入真实姓名">
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input 
          v-model="registerForm.email"
          :prefix-icon="Message"
          placeholder="请输入电子邮箱">
        </el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input 
          v-model="registerForm.phone"
          :prefix-icon="Phone"
          placeholder="请输入手机号码">
        </el-input>
      </el-form-item>
      <!-- 学生专属信息 -->
      <template v-if="registerForm.roleCode === 'STUDENT'">
        <el-form-item prop="studentNo">
          <el-input v-model="registerForm.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item prop="gender">
          <el-radio-group v-model="registerForm.gender">
            <el-radio label="M">男</el-radio>
            <el-radio label="F">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birthDate">
          <el-date-picker v-model="registerForm.birthDate" type="date" placeholder="请选择出生日期" style="width: 100%" />
        </el-form-item>
        <el-form-item prop="idCard">
          <el-input v-model="registerForm.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item prop="address">
          <el-input v-model="registerForm.address" placeholder="请输入家庭住址" />
        </el-form-item>
        <el-form-item prop="classId">
          <el-select v-model="registerForm.classId" placeholder="请选择班级">
            <el-option 
              v-for="item in classesList" 
              :key="item.id" 
              :label="item.className" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
      </template>
      <!-- 教师专属信息 -->
      <template v-if="registerForm.roleCode === 'TEACHER'">
        <el-form-item prop="teacherNo">
          <el-input v-model="registerForm.teacherNo" placeholder="请输入教师编号" />
        </el-form-item>
        <el-form-item prop="gender">
          <el-radio-group v-model="registerForm.gender">
            <el-radio label="M">男</el-radio>
            <el-radio label="F">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="title">
          <el-select v-model="registerForm.title" placeholder="请选择职称">
            <el-option label="讲师" value="讲师" />
            <el-option label="副教授" value="副教授" />
            <el-option label="教授" value="教授" />
          </el-select>
        </el-form-item>
      </template>
    </template>

    <template #auth-links>
      <div class="auth-links-container">
        <span>已有账号？</span>
        <router-link to="/login">立即登录</router-link>
      </div>
    </template>
  </Auth>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import {Lock, Message, Phone, User, UserFilled} from '@element-plus/icons-vue'
import request from '@/utils/request'
import Auth from './Auth.vue'
import {GENDER} from '@/constants/gender'

const router = useRouter()
const formRef = ref(null)
const registerFormRef = ref(null)
const loading = ref(false)
const classesList = ref([])

const registerForm = reactive({
  roleCode: '',
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  phone: '',
  name: '',
  // 学生专属
  studentNo: '',
  gender: GENDER.MALE,
  birthDate: '',
  idCard: '',
  address: '',
  classId: '',  // 新增班级ID字段
  // 教师专属
  teacherNo: '',
  title: '',
})

// 获取班级列表
const fetchClasses = async () => {
  try {
    const res = await request.get('/class/all',null,{
      showDefaultMsg: true,
      onSuccess: (res) => {
        classesList.value = res
      }
    })
  } catch (error) {
    console.error('获取班级列表失败:', error)
  }
}

// 监听角色变化，当选择学生角色时加载班级列表
const handleRoleChange = () => {
  if (registerForm.roleCode === 'STUDENT') {
    fetchClasses()
  }
}

onMounted(() => {
  // 组件挂载时预加载班级列表
  fetchClasses()
})

const validatePass2 = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致!'))
  } else {
    callback()
  }
}

const validateEmail = (rule, value, callback) => {
  const emailRegex = /^[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
  if (!emailRegex.test(value)) {
    callback(new Error('邮箱格式不正确'))
  } else {
    callback()
  }
}

const validatePhone = (rule, value, callback) => {
  if (value && !/^1[3-9]\d{9}$/.test(value)) {
    callback(new Error('手机号格式不正确'))
  } else {
    callback()
  }
}

const rules = {
  roleCode: [
    { required: true, message: '请选择身份', trigger: 'change' }
  ],
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 50, message: '账号长度必须在3到50个字符之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 100, message: '密码长度必须在6到100个字符之间', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validatePass2, trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入电子邮箱', trigger: 'blur' },
    { validator: validateEmail, trigger: 'blur' }
  ],
  phone: [
    { validator: validatePhone, trigger: 'blur' }
  ],
  name: [
    { required: false }
  ],
  // 学生专属
  studentNo: [
    { required: () => registerForm.roleCode === 'STUDENT', message: '请输入学号', trigger: 'blur' },
    { min: 3, max: 20, message: '学号长度应在3-20个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: () => registerForm.roleCode !== '', message: '请选择性别', trigger: 'change' }
  ],
  birthDate: [
    { required: () => registerForm.roleCode === 'STUDENT', message: '请选择出生日期', trigger: 'change' }
  ],
  idCard: [
    { required: () => registerForm.roleCode === 'STUDENT', message: '请输入身份证号', trigger: 'blur' },
    { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确的身份证号码', trigger: 'blur' }
  ],
  address: [
    { required: () => registerForm.roleCode === 'STUDENT', message: '请输入家庭住址', trigger: 'blur' }
  ],
  classId: [
    { required: () => registerForm.roleCode === 'STUDENT', message: '请选择班级', trigger: 'change' }
  ],
  // 教师专属
  teacherNo: [
    { required: () => registerForm.roleCode === 'TEACHER', message: '请输入教师编号', trigger: 'blur' },
    { min: 3, max: 20, message: '教师编号长度应在3-20个字符之间', trigger: 'blur' }
  ],
  title: [
    { required: () => registerForm.roleCode === 'TEACHER', message: '请选择职称', trigger: 'change' }
  ]
}

const handleSubmit = (form) => {
  formRef.value = form.value
  registerFormRef.value = form.value
  handleRegister()
}

const handleRegister = () => {
  registerFormRef.value.validate(async valid => {
    if (valid) {
      loading.value = true
      try {
        const { confirmPassword, ...registerData } = registerForm

        // 验证角色选择
        if (!registerData.roleCode) {
          ElMessage.error('请选择身份')
          return
        }

        // 组装注册数据
        let payload = {
          user: {
            username: registerData.username,
            password: registerData.password,
            email: registerData.email,
            phone: registerData.phone,
            name: registerData.name,
            roleCode: registerData.roleCode
          }
        }

        if (registerData.roleCode === 'STUDENT') {
          // 验证学生必填字段
          if (!registerData.classId) {
            ElMessage.error('请选择班级')
            return
          }

          payload.student = {
            studentNo: registerData.studentNo,
            gender: registerData.gender,
            birthDate: registerData.birthDate,
            idCard: registerData.idCard,
            address: registerData.address,
            classId: registerData.classId
          }
        } else if (registerData.roleCode === 'TEACHER') {
          payload.teacher = {
            teacherNo: registerData.teacherNo,
            gender: registerData.gender,
            title: registerData.title
          }
        }

        await request.post("/user/register", payload, {
          successMsg: "注册成功，请登录",
          showDefaultMsg: true,
          onSuccess: () => {
            router.push('/login')
          },
          onError: (error) => {
            console.error('注册失败:', error)
            // 错误信息会由request工具自动显示
          }
        })
      } catch (error) {
        console.error('注册过程中发生错误:', error)
        ElMessage.error('注册失败，请重试')
      } finally {
        loading.value = false
      }
    } else {
      ElMessage.error('请检查表单填写是否正确')
    }
  })
}
</script>

<style lang="scss" scoped>
.auth-links-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  
  span {
    color: #64748b;
  }
  
  a {
    color: #3b82f6;
    font-weight: 500;
    transition: color 0.2s;
    
    &:hover {
      color: #2563eb;
    }
  }
}
</style> 