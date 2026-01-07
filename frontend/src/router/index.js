import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Demo from '../views/demo.vue'

// Admin
import UserManagement from '../views/admin/UserManagement.vue'
import AdminOrderManagement from '../views/admin/OrderManagement.vue'
import DataAnalysis from '../views/admin/DataAnalysis.vue'

// Merchant
import ProductManagement from '../views/merchant/ProductManagement.vue'
import MerchantOrderManagement from '../views/merchant/OrderManagement.vue'
import MerchantLogisticsQuery from '../views/merchant/LogisticsQuery.vue'

// Consumer
import Mall from '../views/consumer/Mall.vue'
import MyOrders from '../views/consumer/MyOrders.vue'
import ConsumerLogisticsQuery from '../views/consumer/LogisticsQuery.vue'

// Driver
import PendingPickup from '../views/delivery/PendingPickup.vue'
import PendingDelivery from '../views/delivery/PendingDelivery.vue'
import HistoryTasks from '../views/delivery/HistoryTasks.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/demo',
      name: 'home',
      component: Demo,
      children: [
        // Admin routes
        {
          path: '/admin/user-management',
          component: UserManagement
        },
        {
          path: '/admin/order-management',
          component: AdminOrderManagement
        },
        {
          path: '/admin/data-analysis',
          component: DataAnalysis
        },
        // Merchant routes
        {
          path: '/merchant/product-management',
          component: ProductManagement
        },
        {
          path: '/merchant/order-management',
          component: MerchantOrderManagement
        },
        {
          path: '/merchant/logistics-query',
          component: MerchantLogisticsQuery
        },
        // Consumer routes
        {
          path: '/consumer/mall',
          component: Mall
        },
        {
          path: '/consumer/my-orders',
          component: MyOrders
        },
        {
          path: '/consumer/logistics-query',
          component: ConsumerLogisticsQuery
        },
        // Driver routes
        {
          path: '/driver/pending-pickup',
          component: PendingPickup
        },
        {
          path: '/driver/pending-delivery',
          component: PendingDelivery
        },
        {
          path: '/driver/history-tasks',
          component: HistoryTasks
        }
      ]
    }
  ]
})

export default router
