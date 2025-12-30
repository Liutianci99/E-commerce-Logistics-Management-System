<template>
  <div class="login-container">
    <div class="login-box">
      <h1 class="welcome-title">Welcome</h1>
      
      <!-- 登录表单 -->
      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label class="form-label">用户名</label>
          <input
            v-model="loginForm.username"
            type="text"
            placeholder="请输入用户名"
            class="form-input"
            required
          />
        </div>

        <div class="form-group">
          <div class="password-header">
            <label class="form-label">密码</label>
            <a href="#" class="forgot-link" @click.prevent="forgotPassword">
              忘记密码？
            </a>
          </div>
          <div class="password-input-wrapper">
            <input
              v-model="loginForm.password"
              :type="showPassword ? 'text' : 'password'"
              placeholder="••••••••••••"
              class="form-input"
              required
            />
            <button
              type="button"
              class="password-toggle"
              @click="showPassword = !showPassword"
            >
              <svg v-if="!showPassword" viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                <circle cx="12" cy="12" r="3"/>
              </svg>
              <svg v-else viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/>
                <line x1="1" y1="1" x2="23" y2="23"/>
              </svg>
            </button>
          </div>
        </div>

        <button type="submit" class="sign-in-btn" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>

      <!-- 注册按钮 -->
      <button class="sign-up-btn" @click="goToSignup">
        注册
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const router = useRouter()
const loading = ref(false)
const showPassword = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

// 账号密码登录
const handleLogin = async () => {
  // 简单验证
  if (!loginForm.username || !loginForm.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }

  loading.value = true
  
  // 模拟登录延迟
  setTimeout(() => {
    try {
      // 暂时跳过后端API调用，直接跳转
      ElMessage.success('登录成功！')
      // 模拟保存token
      localStorage.setItem('token', 'demo-token-' + Date.now())
      localStorage.setItem('username', loginForm.username)
      // 跳转到首页
      router.push('/home')
    } catch (error) {
      ElMessage.error('登录失败：' + error.message)
    } finally {
      loading.value = false
    }
  }, 500)
  
  // TODO: 后续接入真实的后端API
  // try {
  //   const res = await request.post('/api/auth/login', loginForm)
  //   ElMessage.success('登录成功！')
  //   localStorage.setItem('token', res.token)
  //   router.push('/home')
  // } catch (error) {
  //   ElMessage.error('登录失败：' + error.message)
  // } finally {
  //   loading.value = false
  // }
}

// 忘记密码
const forgotPassword = () => {
  ElMessage.info('密码找回功能开发中...')
}

// 跳转注册
const goToSignup = () => {
  router.push('/register')
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: white;
  padding: 20px;
}

.login-box {
  background: white;
  padding: 48px 40px;
  width: 100%;
  max-width: 430px;
}

.welcome-title {
  font-size: 32px;
  font-weight: 600;
  color: #000000;
  margin: 0 0 36px 0;
  text-align: center;
  font-family: system-ui, -apple-system, sans-serif;
}

/* 表单 */
.login-form {
  margin-bottom: 12px;
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: #000000;
  margin-bottom: 8px;
  font-family: system-ui, -apple-system, sans-serif;
}

.password-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 6px;
}

.forgot-link {
  font-size: 14px;
  color: #9ca3af;
  text-decoration: none;
  transition: color 0.2s;
}

.forgot-link:hover {
  color: #6b7280;
}

.form-input {
  width: 100%;
  height: 44px;
  padding: 0 14px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 15px;
  color: #000000;
  background: #f9fafb;
  transition: all 0.2s;
  font-family: system-ui, -apple-system, sans-serif;
}

.form-input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.form-input::placeholder {
  color: #9ca3af;
}

.password-input-wrapper {
  position: relative;
}

.password-input-wrapper .form-input {
  padding-right: 48px;
}

.password-toggle {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #6b7280;
  cursor: pointer;
  padding: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: color 0.2s;
}

.password-toggle:hover {
  color: #374151;
}

/* 登录按钮 */
.sign-in-btn {
  width: 100%;
  height: 48px;
  background: #000000;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  font-family: system-ui, -apple-system, sans-serif;
}

.sign-in-btn:hover:not(:disabled) {
  background: #333333;
}

.sign-in-btn:disabled {
  cursor: not-allowed;
  opacity: 0.6;
}

/* 注册按钮 */
.sign-up-btn {
  width: 100%;
  height: 48px;
  background: #000000;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  font-family: system-ui, -apple-system, sans-serif;
  margin-top: 8px;
}

.sign-up-btn:hover {
  background: #333333;
}

/* 响应式 */
@media (max-width: 480px) {
  .login-box {
    padding: 32px 24px;
  }
  
  .welcome-title {
    font-size: 26px;
    margin-bottom: 32px;
  }
}
</style>
