package com.logistics.service;

import com.logistics.dto.CreateOrderRequest;
import com.logistics.entity.Order;

import java.util.List;

/**
 * 订单服务接口
 */
public interface OrderService {
    
    /**
     * 创建订单
     * @param request 订单请求
     * @param customerId 顾客ID
     * @return 订单信息
     */
    Order createOrder(CreateOrderRequest request, Integer customerId);
    
    /**
     * 获取顾客的订单列表
     * @param customerId 顾客ID
     * @param status 订单状态（可选）
     * @param search 搜索关键词（可选）
     * @return 订单列表
     */
    List<Order> getCustomerOrders(Integer customerId, Integer status, String search);
    
    /**
     * 获取商户的订单列表
     * @param merchantId 商户ID
     * @param status 订单状态（可选）
     * @param search 搜索关键词（可选）
     * @return 订单列表
     */
    List<Order> getMerchantOrders(Integer merchantId, Integer status, String search);
    
    /**
     * 确认收货
     * @param orderId 订单ID
     * @param customerId 顾客ID
     */
    void confirmReceipt(Integer orderId, Integer customerId);
    
    /**
     * 商户发货
     * @param orderId 订单ID
     * @param merchantId 商户ID
     */
    void shipOrder(Integer orderId, Integer merchantId);
    
    /**
     * 配送员确认揽收
     * @param orderId 订单ID
     */
    void confirmPickup(Integer orderId);
    
    /**
     * 获取配送员的待揽收订单列表
     * @param warehouseId 仓库ID（可选）
     * @param search 搜索关键词（可选）
     * @return 订单列表
     */
    List<Order> getPendingPickupOrders(Integer warehouseId, String search);
    
    /**
     * 获取配送员的待送货订单列表（已揽收状态）
     * @param warehouseId 仓库ID（可选）
     * @return 订单列表
     */
    List<Order> getPendingDeliveryOrders(Integer warehouseId);
    
    /**
     * 创建送货批次（将选中的订单状态更新为运输中）
     * @param orderIds 订单ID列表
     */
    void createDeliveryBatch(List<Integer> orderIds);
}
