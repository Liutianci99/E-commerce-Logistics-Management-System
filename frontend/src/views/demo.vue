<template>
    <div class="home-container">
        <!-- 侧边栏 -->
        <aside class="sidebar">
            <div class="logo">
                <h2>电商物流管理系统</h2>
                
            </div>
            <nav class="nav-menu">
                <ul>
                    <li v-for="item in visibleMenu" :key="item.path">
                        <a href="#" @click.prevent="selectMenu(item)" :class="{ active: activeMenu === item.path }">
                            <span class="icon">{{ item.icon }}</span>
                            <span class="title">{{ item.title }}</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </aside>

        <!-- 主内容区域 -->
        <div class="main-container">
            <!-- 顶部导航栏 -->
            <header class="top-header">
                <div class="user-info">
                    <span>用户：{{ getUserName() }}（{{ getRoleLabel(currentUser.role) }}）</span>
                </div>
            </header>

            <!-- 页面内容 -->
            <section class="content-area">
                <!-- 内容容器 -->
            </section>
        </div>
    </div>
</template>

<script setup>
import { computed, ref } from 'vue'

// 当前用户信息 - 登录时从后端获取
const currentUser = ref({
    id: '',
    username: '',
    role: 'merchant' // 'merchant' | 'driver' | 'consumer' | 'admin'
})

// 当前选中的菜单项
const activeMenu = ref('/dashboard')

// 菜单配置 - 每项指定可见的角色
const menuConfig = [
    {
        title: '首页',
        path: '/dashboard',
        icon: '📊',
        roles: ['merchant', 'driver', 'consumer', 'admin']
    },
    {
        title: '商户面板',
        path: '/merchant',
        icon: '🏪',
        roles: ['merchant']
    },
    {
        title: '司机面板',
        path: '/driver',
        icon: '🚚',
        roles: ['driver']
    },
    {
        title: '消费者面板',
        path: '/consumer',
        icon: '🛍️',
        roles: ['consumer']
    },
    {
        title: '管理员面板',
        path: '/admin',
        icon: '👨‍💼',
        roles: ['admin']
    },
    {
        title: '账户设置',
        path: '/settings',
        icon: '🔧',
        roles: ['merchant', 'driver', 'consumer', 'admin']
    }
]

// 根据当前用户角色过滤菜单
const visibleMenu = computed(() => {
    return menuConfig.filter(item => item.roles.includes(currentUser.value.role))
})

// 获取角色中文标签
const getRoleLabel = (role) => {
    const roleLabels = {
        merchant: '商户',
        driver: '司机',
        consumer: '消费者',
        admin: '管理员'
    }
    return roleLabels[role] || role
}

// 获取当前用户名 - 直接从后端数据获取
const getUserName = () => {
    return currentUser.value.username || '未知用户'
}

// 选择菜单项
const selectMenu = (item) => {
    activeMenu.value = item.path
}

// 设置用户信息 - 登录成功后调用
const setUserInfo = (userInfo) => {
    currentUser.value = userInfo // { id: '1', username: '刘天赐', role: 'merchant' }
}

// 四种角色的菜单生成函数
const getMerchantMenu = () => {
    return menuConfig.filter(item => item.roles.includes('merchant'))
}

const getDriverMenu = () => {
    return menuConfig.filter(item => item.roles.includes('driver'))
}

const getConsumerMenu = () => {
    return menuConfig.filter(item => item.roles.includes('consumer'))
}

const getAdminMenu = () => {
    return menuConfig.filter(item => item.roles.includes('admin'))
}

// 根据角色设置菜单的函数
const setMenuByRole = (role) => {
    currentUser.value.role = role
    activeMenu.value = '/dashboard'
}
</script>

<style scoped>
/* 全局容器 */
.home-container {
    display: flex;
    height: 100vh;
    background-color: #ffffff;
}

/* 侧边栏 */
.sidebar {
    width: 250px;
    background-color: #f8f9fa;
    color: #1f2937;
    display: flex;
    flex-direction: column;
    box-shadow: 1px 0 3px rgba(0, 0, 0, 0.05);
}

.logo {
    padding: 24px 16px;
    border-bottom: 1px solid #e5e7eb;
}

.logo h2 {
    margin: 0;
    font-size: 18px;
    font-weight: 600;
    color: #1f2937;
}

/* 导航菜单 */
.nav-menu {
    flex: 1;
    padding: 16px 0;
    overflow-y: auto;
}

.nav-menu ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.nav-menu li {
    margin: 4px 8px;
}

.nav-menu a {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 12px 16px;
    color: #1f2937;
    text-decoration: none;
    border-radius: 6px;
    transition: all 0.2s ease;
    cursor: pointer;
}

.nav-menu a:hover {
    background-color: #e5e7eb;
    color: #2563eb;
}

.nav-menu a.active {
    background-color: #2563eb;
    color: #ffffff;
    font-weight: 600;
}

.icon {
    font-size: 18px;
    min-width: 24px;
}

.title {
    font-size: 14px;
}

.nav-menu {
    flex: 1;
    padding: 16px 0;
    overflow-y: auto;
}

/* 主容器 */
.main-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    overflow: hidden;
}

/* 顶部导航栏 */
.top-header {
    height: 60px;
    background-color: #ffffff;
    border-bottom: 1px solid #e5e7eb;
    display: flex;
    align-items: center;
    padding: 0 24px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.user-info {
    font-size: 14px;
    color: #6b7280;
}

/* 内容区域 */
.content-area {
    flex: 1;
    overflow-y: auto;
    padding: 24px;
    background-color: #ffffff;
}
</style>