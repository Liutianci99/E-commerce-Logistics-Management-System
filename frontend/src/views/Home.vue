<template>
  <div class="home">
    <el-container>
      <el-header>
        <h1>电商物流管理系统</h1>
      </el-header>
      <el-main>
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>系统状态</span>
            </div>
          </template>
          <el-button type="primary" @click="testApi">测试后端连接</el-button>
          <div v-if="apiResponse" class="response">
            <p><strong>响应信息：</strong></p>
            <pre>{{ JSON.stringify(apiResponse, null, 2) }}</pre>
          </div>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const apiResponse = ref(null)

const testApi = async () => {
  try {
    const res = await request.get('/test/hello')
    apiResponse.value = res
    ElMessage.success('后端连接成功！')
  } catch (error) {
    ElMessage.error('后端连接失败：' + error.message)
  }
}
</script>

<style scoped>
.home {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.el-header {
  background-color: #409eff;
  color: white;
  text-align: center;
  line-height: 60px;
}

.el-main {
  padding: 40px;
}

.box-card {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.response {
  margin-top: 20px;
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

pre {
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style>
