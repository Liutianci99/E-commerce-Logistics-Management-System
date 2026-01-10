<template>
    <div class="page-container">
        <!-- 搜索筛选栏 -->
        <div class="filter-bar">
            <div class="search-box">
                <input 
                    type="text" 
                    v-model="searchKeyword" 
                    placeholder="搜索商品名称..."
                    class="search-input"
                    @keyup.enter="handleSearch"
                />
                <button @click="handleSearch" class="search-btn">搜索</button>
            </div>
            <div class="stock-filter">
                <label>库存数量：</label>
                <input 
                    type="number" 
                    v-model.number="minStock" 
                    placeholder="最少库存"
                    class="stock-input"
                />
                <span class="stock-separator">-</span>
                <input 
                    type="number" 
                    v-model.number="maxStock" 
                    placeholder="最多库存"
                    class="stock-input"
                />
                <label style="margin-left: 16px;">状态：</label>
                <select v-model="statusFilter" class="status-select">
                    <option value="all">全部</option>
                    <option value="online">已上架</option>
                    <option value="offline">未上架</option>
                </select>
                <button @click="handleSearch" class="filter-btn">筛选</button>
                <button @click="clearFilters" class="clear-btn">清除结果</button>
                <button @click="handleStockIn" class="add-btn">商品入库</button>
                <button @click="goToProductManagement" class="add-btn">商品上架</button>
            </div>
        </div>

        <!-- 库存列表 -->
        <div class="inventory-list">
            <div class="inventory-item" v-for="item in filteredInventory" :key="item.id">
                <div class="product-image">
                    <img :src="item.image" :alt="item.name" />
                </div>
                <div class="product-info">
                    <h3 class="product-name">{{ item.name }}</h3>
                </div>
                <div class="inventory-stock">
                    <span class="stock-label">库存</span>
                    <span class="stock-value" :class="getStockClass(item.stock)">{{ item.stock }} 件</span>
                </div>
                <div class="product-status">
                    <span class="status-label">状态</span>
                    <span class="status-value" :class="item.status === 'online' ? 'status-online' : 'status-offline'">
                        {{ item.status === 'online' ? '已上架' : '未上架' }}
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 搜索和筛选 - 输入框绑定的值
const searchKeyword = ref('')
const minStock = ref(null)
const maxStock = ref(null)
const statusFilter = ref('all')

// 实际用于筛选的值（点击搜索后更新）
const activeSearchKeyword = ref('')
const activeMinStock = ref(null)
const activeMaxStock = ref(null)
const activeStatusFilter = ref('all')

// 模拟库存数据
const inventory = ref([
    {
        id: 1,
        name: '苹果 iPhone 15 Pro',
        description: '搭载A17 Pro芯片，钛金属设计，专业级摄影系统',
        price: 7999,
        stock: 25,
        status: 'online',
        image: 'https://picsum.photos/200?random=1'
    },
    {
        id: 2,
        name: '索尼 WH-1000XM5 耳机',
        description: '业界领先降噪技术，30小时续航，舒适佩戴体验',
        price: 2499,
        stock: 8,
        status: 'online',
        image: 'https://images.unsplash.com/photo-1546435770-a3e426bf472b?w=200&h=200&fit=crop'
    },
    {
        id: 3,
        name: '戴尔 XPS 15 笔记本',
        description: '4K OLED显示屏，Intel i7处理器，16GB内存，512GB SSD',
        price: 12999,
        stock: 42,
        status: 'online',
        image: 'https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=200&h=200&fit=crop'
    },
    {
        id: 4,
        name: '罗技 MX Master 3S 鼠标',
        description: '精准追踪，静音按键，多设备连接，人体工学设计',
        price: 699,
        stock: 3,
        status: 'offline',
        image: 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=200&h=200&fit=crop'
    },
    {
        id: 5,
        name: '三星 Galaxy S24 Ultra',
        description: '200MP相机，S Pen支持，AI功能，骁龙8 Gen 3处理器',
        price: 9299,
        stock: 0,
        status: 'offline',
        image: 'https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=200&h=200&fit=crop'
    }
])

// 过滤后的库存列表
const filteredInventory = computed(() => {
    let result = inventory.value

    // 按名称搜索
    if (activeSearchKeyword.value.trim()) {
        result = result.filter(item => 
            item.name.toLowerCase().includes(activeSearchKeyword.value.toLowerCase())
        )
    }

    // 按库存筛选
    if (activeMinStock.value !== null && activeMinStock.value !== '') {
        result = result.filter(item => item.stock >= activeMinStock.value)
    }
    if (activeMaxStock.value !== null && activeMaxStock.value !== '') {
        result = result.filter(item => item.stock <= activeMaxStock.value)
    }

    // 按状态筛选
    if (activeStatusFilter.value !== 'all') {
        result = result.filter(item => item.status === activeStatusFilter.value)
    }

    return result
})

// 搜索处理
const handleSearch = () => {
    // 将输入框的值应用到实际筛选变量
    activeSearchKeyword.value = searchKeyword.value
    activeMinStock.value = minStock.value
    activeMaxStock.value = maxStock.value
    activeStatusFilter.value = statusFilter.value
}

// 清除筛选
const clearFilters = () => {
    searchKeyword.value = ''
    minStock.value = null
    maxStock.value = null
    statusFilter.value = 'all'
    // 同时清除实际筛选变量
    activeSearchKeyword.value = ''
    activeMinStock.value = null
    activeMaxStock.value = null
    activeStatusFilter.value = 'all'
}

// 跳转到商品上架页面
const goToProductManagement = () => {
    router.push('/merchant/product-listing')
}

// 商品入库处理
const handleStockIn = () => {
    router.push('/merchant/stock-in')
}

// 根据库存数量返回样式类
const getStockClass = (stock) => {
    if (stock === 0) return 'stock-out'
    if (stock < 10) return 'stock-low'
    return 'stock-normal'
}
</script>

<style scoped>
.page-container {
    width: 100%;
}

h1 {
    margin: 0 0 24px 0;
    color: #1f2937;
    font-size: 28px;
}

/* 搜索筛选栏样式 */
.filter-bar {
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 20px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.search-box {
    margin-bottom: 16px;
    display: flex;
    gap: 12px;
}

.search-input {
    flex: 1;
    padding: 10px 16px;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 14px;
    color: #374151;
    transition: border-color 0.2s;
}

.search-input:focus {
    outline: none;
    border-color: #3b82f6;
}

.search-btn {
    padding: 10px 24px;
    background: #ffffff;
    color: #1f2937;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s;
    white-space: nowrap;
}

.search-btn:hover {
    background: #f9fafb;
    border-color: #9ca3af;
}

.search-btn:active {
    background: #f3f4f6;
}

.stock-filter {
    display: flex;
    align-items: center;
    gap: 12px;
    flex-wrap: wrap;
}

.stock-filter label {
    font-size: 14px;
    color: #374151;
    font-weight: 500;
}

.stock-input {
    width: 120px;
    padding: 8px 12px;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 14px;
    color: #374151;
    transition: border-color 0.2s;
}

.stock-input:focus {
    outline: none;
    border-color: #3b82f6;
}

.stock-separator {
    color: #9ca3af;
    font-weight: 500;
}

.filter-btn {
    padding: 8px 16px;
    background: #ffffff;
    color: #1f2937;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s;
}

.filter-btn:hover {
    background: #f9fafb;
    border-color: #9ca3af;
}

.clear-btn {
    padding: 8px 16px;
    background: #ffffff;
    color: #1f2937;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s;
}

.clear-btn:hover {
    background: #f9fafb;
    border-color: #9ca3af;
}

.status-select {
    padding: 8px 12px;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 14px;
    color: #374151;
    background: #ffffff;
    cursor: pointer;
    transition: border-color 0.2s;
}

.status-select:focus {
    outline: none;
    border-color: #3b82f6;
}

.add-btn {
    padding: 8px 16px;
    background: #ffffff;
    color: #1f2937;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s;
    margin-left: auto;
}

.add-btn:hover {
    background: #f9fafb;
    border-color: #9ca3af;
}

/* 库存列表样式 */
.inventory-list {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.inventory-item {
    display: flex;
    align-items: center;
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    padding: 16px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.2s;
}

.inventory-item:hover {
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.product-image {
    width: 100px;
    height: 100px;
    flex-shrink: 0;
    margin-right: 20px;
    border-radius: 6px;
    overflow: hidden;
    background: #f3f4f6;
}

.product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.product-info {
    flex: 1;
    min-width: 0;
    margin-right: 20px;
}

.product-name {
    margin: 0 0 8px 0;
    color: #1f2937;
    font-size: 18px;
    font-weight: 600;
}

.product-description {
    margin: 0;
    color: #6b7280;
    font-size: 14px;
    line-height: 1.5;
}

.product-price {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    margin-right: 20px;
    min-width: 100px;
}

.price-label {
    font-size: 12px;
    color: #9ca3af;
    margin-bottom: 4px;
}

.price-value {
    font-size: 16px;
    font-weight: 500;
    color: #1f2937;
}

.inventory-stock {
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 100px;
}

.stock-label {
    font-size: 12px;
    color: #9ca3af;
    margin-bottom: 4px;
}

.stock-value {
    font-size: 16px;
    font-weight: 500;
    color: #1f2937;
}

.stock-normal {
    color: #1f2937;
}

.stock-low {
    color: #1f2937;
}

.stock-out {
    color: #1f2937;
}

.product-status {
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 100px;
}

.status-label {
    font-size: 12px;
    color: #9ca3af;
    margin-bottom: 4px;
}

.status-value {
    font-size: 14px;
    font-weight: 500;
    color: #1f2937;
}

.status-online {
    color: #1f2937;
}

.status-offline {
    color: #1f2937;
}

/* 响应式设计 */
@media (max-width: 768px) {
    .inventory-item {
        flex-direction: column;
        align-items: stretch;
    }

    .product-image {
        width: 100%;
        height: 200px;
        margin-right: 0;
        margin-bottom: 16px;
    }

    .product-info {
        margin-right: 0;
        margin-bottom: 12px;
    }

    .product-price {
        align-items: flex-start;
        margin-right: 0;
        margin-bottom: 12px;
    }

    .inventory-stock {
        align-items: flex-start;
    }
}
</style>
