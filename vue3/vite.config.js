import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  server: {
    port: 3000,
    open: true,
    proxy: {
      '/api': {
        target: 'http://localhost:1232',
        changeOrigin: true,
        // pathRewrite: {
        //   '^/api': ''
        // }
      }
    }
  },
  css: {
    preprocessorOptions: {
      scss: {
        // 移除 additionalData 选项
        // 改为在需要的文件中手动导入变量文件
      }
    }
  }
}) 