<template>
  <div class="auth-container">
    <div class="auth-box">
      <div class="auth-header" v-if="showHeader">
        <div class="logo">🖥️</div>
        <h1 class="title">高校课程管理系统</h1>
        <div class="subtitle">Course Selection System</div>
      </div>
      
      <el-form :model="formData" :rules="rules" ref="formRef" class="auth-form">
        <slot name="form-items"></slot>
        
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleSubmit" class="auth-button">
            {{ submitText }}
          </el-button>
        </el-form-item>
        
        <div class="auth-links">
          <slot name="auth-links"></slot>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'

const props = defineProps({
  formData: {
    type: Object,
    required: true
  },
  rules: {
    type: Object,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  },
  submitText: {
    type: String,
    default: '提交'
  },
  showHeader: {
    type: Boolean,
    default: true
  }
})

const formRef = ref(null)

const emit = defineEmits(['submit'])

const handleSubmit = () => {
  formRef.value.validate(valid => {
    if (valid) {
      emit('submit', formRef)
    }
  })
}

defineExpose({
  formRef
})
</script>

<style lang="scss" scoped>
.auth-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e9f2 100%);
  padding: 20px;
}

.auth-box {
  width: 100%;
  max-width: 420px;
  padding: 40px 30px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
}

.auth-header {
  text-align: center;
  margin-bottom: 40px;

  .logo {
    font-size: 48px;
    margin-bottom: 16px;
  }

  .title {
    font-size: 24px;
    color: #2c3e50;
    margin: 0 0 8px;
    font-weight: 600;
  }

  .subtitle {
    font-size: 14px;
    color: #94a3b8;
    text-transform: uppercase;
    letter-spacing: 2px;
  }
}

.auth-form {
  :deep(.el-form-item) {
    margin-bottom: 20px;
  }

  :deep(.el-input) {
    --el-input-hover-border-color: #4b5563;
    --el-input-focus-border-color: #3b82f6;

    .el-input__wrapper {
      box-shadow: 0 0 0 1px #e5e7eb;
      padding: 8px 12px;
      border-radius: 8px;
      
      &:hover {
        box-shadow: 0 0 0 1px var(--el-input-hover-border-color);
      }
      
      &.is-focus {
        box-shadow: 0 0 0 2px var(--el-input-focus-border-color);
      }
    }
  }
}

.auth-button {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  background: #3b82f6;
  border: none;
  
  &:hover {
    background: #2563eb;
  }
  
  &:active {
    background: #1d4ed8;
  }
}

.auth-links {
  margin-top: 20px;
  text-align: center;
  font-size: 14px;
  
  a {
    color: #3b82f6;
    text-decoration: none;
    margin: 0 8px;
    
    &:hover {
      text-decoration: underline;
    }
  }
}
</style> 