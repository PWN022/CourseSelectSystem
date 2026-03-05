<template>
  <div class="profile-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
        </div>
      </template>
      
      <el-tabs v-model="activeTab">
        <el-tab-pane label="基本信息" name="basic">
          <div class="profile-content">
            <div class="avatar-container">
              <el-avatar :size="100" :src="avatarUrl"></el-avatar>
              <el-upload
                class="avatar-uploader"
                action="#"
                :auto-upload="true"
                :show-file-list="false"
                :http-request="customUploadAvatar"
                :before-upload="beforeAvatarUpload"
              >
                <el-button size="small" type="primary">更换头像</el-button>
              </el-upload>
            </div>
            
            <el-form
              :model="userForm"
              :rules="userFormRules"
              ref="userFormRef"
              label-width="100px"
              class="profile-form"
            >
              <el-form-item label="用户名">
                <span>{{ userForm.username }}</span>
              </el-form-item>
              <el-form-item label="姓名" prop="name">
                <el-input v-model="userForm.name" placeholder="请输入姓名"></el-input>
              </el-form-item>
              <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="userForm.sex">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女">女</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="userForm.email" placeholder="请输入邮箱"></el-input>
              </el-form-item>
              <el-form-item label="手机号" prop="phone">
                <el-input v-model="userForm.phone" placeholder="请输入手机号"></el-input>
              </el-form-item>
              <el-form-item label="角色">
                <el-tag v-if="userForm.roleCode === 'ADMIN'" type="danger">管理员</el-tag>
                <el-tag v-else-if="userForm.roleCode === 'TEACHER'" type="warning">教师</el-tag>
                <el-tag v-else-if="userForm.roleCode === 'STUDENT'" type="success">学生</el-tag>
                <el-tag v-else type="info">未知</el-tag>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="updateProfile" :loading="updateLoading">保存修改</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
        
        <el-tab-pane label="修改密码" name="password">
          <el-form
            :model="passwordForm"
            :rules="passwordRules"
            ref="passwordFormRef"
            label-width="100px"
            class="password-form"
          >
            <el-form-item label="原密码" prop="oldPassword">
              <el-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入原密码"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="passwordForm.confirmPassword" type="password" placeholder="请确认新密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="updatePassword" :loading="passwordLoading">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/user'
import request from '@/utils/request'

const userStore = useUserStore()
const activeTab = ref('basic')
const userFormRef = ref(null)
const passwordFormRef = ref(null)
const updateLoading = ref(false)
const passwordLoading = ref(false)

// 用户表单数据
const userForm = reactive({
  id: '',
  username: '',
  name: '',
  sex: '',
  email: '',
  phone: '',
  roleCode: '',
  avatar: ''
})

// 密码表单数据
const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 头像（文件）地址
const baseAPI = import.meta.env.VITE_BASE_API || '/api'
const avatarUrl = computed(() => {
  return userForm.avatar ? baseAPI + userForm.avatar : '';
});

// 表单验证规则
const userFormRules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ]
}

// 密码表单验证规则
const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
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
  fetchUserInfo()
})

// 获取用户信息
const fetchUserInfo = async () => {
  try {
    await request.get('/user/current', {
      showDefaultMsg: false,
      onSuccess: (data) => {
        Object.keys(userForm).forEach(key => {
          if (key in data) {
            userForm[key] = data[key]
          }
        })
      }
    })
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

// 更新个人信息
const updateProfile = () => {
  userFormRef.value.validate(async valid => {
    if (valid) {
      updateLoading.value = true
      try {
        await request.put(`/user/${userForm.id}`, userForm, {
          successMsg: '个人信息更新成功',
          onSuccess: (data) => {
            // 更新本地存储的用户信息
            userStore.updateUserInfo({
              ...userStore.userInfo,
              name: userForm.name,
              sex: userForm.sex,
              email: userForm.email,
              phone: userForm.phone
            })
          }
        })
      } catch (error) {
        console.error('更新个人信息失败:', error)
      } finally {
        updateLoading.value = false
      }
    }
  })
}

// 更新密码
const updatePassword = () => {
  passwordFormRef.value.validate(async valid => {
    if (valid) {
      passwordLoading.value = true
      try {
        await request.put(`/user/password/${userForm.id}`, {
          oldPassword: passwordForm.oldPassword,
          newPassword: passwordForm.newPassword
        }, {
          successMsg: '密码修改成功',
          onSuccess: () => {
            // 清空密码表单
            passwordForm.oldPassword = ''
            passwordForm.newPassword = ''
            passwordForm.confirmPassword = ''
            passwordFormRef.value.resetFields()
          }
        })
      } catch (error) {
        console.error('修改密码失败:', error)
      } finally {
        passwordLoading.value = false
      }
    }
  })
}

// 上传头像前的校验
const beforeAvatarUpload = (file) => {
  const isJPG = file.type === "image/jpeg";
  const isPNG = file.type === "image/png";
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isJPG && !isPNG) {
    ElMessage.error("头像只能是 JPG 或 PNG 格式!");
    return false;
  }
  if (!isLt2M) {
    ElMessage.error("头像大小不能超过 2MB!");
    return false;
  }
  return true;
};

// 自定义头像上传方法
const customUploadAvatar = async (options) => {
  try {
    const { file } = options;

    // 创建 FormData 对象
    const formData = new FormData();
    formData.append("file", file);

    // 设置自定义上传选项
    const uploadOptions = {
      headers: {
        token: localStorage.getItem("token") || "",
      },
      // 不进行JSON处理
      transformRequest: [(data) => data],
      // 自定义成功消息
      successMsg: "头像上传成功",
      // 自定义错误消息
      errorMsg: "头像上传失败",
      // 成功回调
      onSuccess: async (data) => {
        // 更新用户头像
        userForm.avatar = data;

        // 保存到后端
        await updateUserAvatar(data);

        // 通知上传成功
        options.onSuccess({ data });
      },
      // 错误回调
      onError: (error) => {
        console.error("头像上传错误:", error);
        options.onError(new Error(error.message || "上传失败"));
      },
    };

    // 发送上传请求
    await request.post("/file/upload/img", formData, uploadOptions);
  } catch (error) {
    options.onError(error);
    console.error("头像上传过程发生错误:", error);
  }
};

// 更新用户头像信息
const updateUserAvatar = async (avatarPath) => {
  try {
    await request.put(
      `/user/${userForm.id}`,
      { avatar: avatarPath },
      {
        showDefaultMsg: false,
        onSuccess: (data) => {
          // 更新本地用户信息
          const updatedUserInfo = { ...userStore.userInfo, avatar: avatarPath };
          userStore.updateUserInfo(updatedUserInfo);
        },
        onError: (error) => {
          console.error("头像信息保存失败", error);
          ElMessage.error("头像信息保存失败");
        },
      }
    );
  } catch (error) {
    console.error("头像信息保存失败", error);
    ElMessage.error("头像信息保存失败");
    throw error;
  }
};
</script>

<style lang="scss" scoped>
.profile-container {
  padding: 20px 0;
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .profile-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    
    .avatar-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-bottom: 30px;
      
      .el-avatar {
        margin-bottom: 15px;
      }
    }
    
    .profile-form {
      width: 100%;
      max-width: 500px;
    }
  }
  
  .password-form {
    width: 100%;
    max-width: 500px;
    margin: 0 auto;
  }
}
</style> 