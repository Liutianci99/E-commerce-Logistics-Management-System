<template>
    <div class="page-container">
        <h1>历史任务</h1>
        <div class="content-card">
            <p>已完成的配送任务</p>
            <ul>
                <li>历史任务列表</li>
                <li>配送记录查询</li>
                <li>任务统计</li>
                <li>收入明细</li>
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
