<template>
  <div class="error-page">
    <div class="error-container">
      <h1>404</h1>
      <h2>页面不存在</h2>
      <p>抱歉，您访问的页面不存在</p>
      <el-button type="primary" @click="goBack">返回上一页</el-button>
      <el-button @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from 'vue-router';

const router = useRouter();

const goBack = () => {
  router.go(-1);
};

const goHome = () => {
  router.push('/');
};
</script>

<style lang="scss" scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
  
  .error-container {
    text-align: center;
    padding: 40px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    
    h1 {
      font-size: 80px;
      margin: 0;
      color: #909399;
    }
    
    h2 {
      font-size: 24px;
      margin: 20px 0;
      color: #606266;
    }
    
    p {
      font-size: 16px;
      color: #909399;
      margin-bottom: 30px;
    }
    
    .el-button {
      margin: 0 10px;
    }
  }
}
</style> 