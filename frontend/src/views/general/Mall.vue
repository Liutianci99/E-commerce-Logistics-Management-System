<template>
    <div class="page-container">
        <h1>商城</h1>
        <div class="content-card">
            <p>浏览和购买商品</p>
            <ul>
                <li>商品浏览</li>
                <li>商品搜索</li>
                <li>购物车管理</li>
                <li>下单购买</li>
            </ul>
        </div>
    </div>
</template>

<script setup>
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

.content-card {
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    padding: 24px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.content-card p {
    color: #6b7280;
    margin: 0 0 16px 0;
}

.content-card ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.content-card li {
    padding: 8px 0;
    color: #374151;
    border-bottom: 1px solid #f3f4f6;
}

.content-card li:last-child {
    border-bottom: none;
}
</style>
