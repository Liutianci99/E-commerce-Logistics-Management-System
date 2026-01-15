package com.logistics.controller;

import com.logistics.common.Result;
import com.logistics.dto.CreateOrderRequest;
import com.logistics.entity.Order;
import com.logistics.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 查询顾客的订单列表
     */
    @GetMapping("/my")
    public Result<List<Order>> getMyOrders(
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String search) {
        try {
            if (customerId == null) {
                return Result.error("用户ID不能为空");
            }
            
            List<Order> orders = orderService.getCustomerOrders(customerId, status, search);
            return Result.success("查询成功", orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 查询商户的订单列表
     */
    @GetMapping("/merchant")
    public Result<List<Order>> getMerchantOrders(
            @RequestParam(required = false) Integer merchantId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String search) {
        try {
            if (merchantId == null) {
                return Result.error("商户ID不能为空");
            }
            
            List<Order> orders = orderService.getMerchantOrders(merchantId, status, search);
            return Result.success("查询成功", orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 查询配送员的待揽收订单列表
     * 状态=1（已发货）的订单
     */
    @GetMapping("/pending-pickup")
    public Result<List<Order>> getPendingPickupOrders(
            @RequestParam(required = false) Integer warehouseId,
            @RequestParam(required = false) String search) {
        try {
            List<Order> orders = orderService.getPendingPickupOrders(warehouseId, search);
            return Result.success("查询成功", orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 确认收货
     */
    @PutMapping("/{orderId}/confirm")
    public Result<Void> confirmReceipt(@PathVariable Integer orderId, @RequestParam Integer customerId) {
        try {
            if (customerId == null) {
                return Result.error("用户ID不能为空");
            }
            
            orderService.confirmReceipt(orderId, customerId);
            return Result.success("确认收货成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 商户发货
     */
    @PutMapping("/{orderId}/ship")
    public Result<Void> shipOrder(@PathVariable Integer orderId, @RequestParam Integer merchantId) {
        try {
            if (merchantId == null) {
                return Result.error("商户ID不能为空");
            }
            
            orderService.shipOrder(orderId, merchantId);
            return Result.success("发货成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 配送员确认揽收
     * 将订单状态从1（已发货）更新为2（已揽收）
     */
    @PutMapping("/{orderId}/pickup")
    public Result<Void> confirmPickup(@PathVariable Integer orderId) {
        try {
            orderService.confirmPickup(orderId);
            return Result.success("揽收成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 查询配送员的待送货订单列表
     * 状态=2（已揽收）的订单
     */
    @GetMapping("/pending-delivery")
    public Result<List<Order>> getPendingDeliveryOrders(
            @RequestParam(required = false) Integer warehouseId) {
        try {
            List<Order> orders = orderService.getPendingDeliveryOrders(warehouseId);
            return Result.success("查询成功", orders);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 创建送货批次
     * 将选中的订单（最多5个）状态更新为运输中(3)
     */
    @PostMapping("/delivery-batch")
    public Result<Void> createDeliveryBatch(@RequestBody List<Integer> orderIds) {
        try {
            orderService.createDeliveryBatch(orderIds);
            return Result.success("创建送货批次成功", null);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 创建订单（下单）
     * TODO: 需要实现用户认证机制，目前临时使用请求参数传递用户ID
     */
    @PostMapping("/create")
    public Result<Order> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            // TODO: 从认证上下文获取用户ID，这里暂时从请求中获取
            // 临时方案：前端需要在请求体中包含customerId
            if (request.getCustomerId() == null) {
                return Result.error("用户ID不能为空");
            }
            
            // 校验请求参数
            if (request.getProductId() == null) {
                return Result.error("商品ID不能为空");
            }
            if (request.getQuantity() == null || request.getQuantity() <= 0) {
                return Result.error("购买数量必须大于0");
            }
            if (request.getPrice() == null) {
                return Result.error("商品价格不能为空");
            }
            
            // 创建订单
            Order order = orderService.createOrder(request, request.getCustomerId());
            
            return Result.success("下单成功", order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
