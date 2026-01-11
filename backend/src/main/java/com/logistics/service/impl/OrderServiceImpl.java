package com.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logistics.dto.CreateOrderRequest;
import com.logistics.entity.Inventory;
import com.logistics.entity.Mall;
import com.logistics.entity.Order;
import com.logistics.mapper.InventoryMapper;
import com.logistics.mapper.MallMapper;
import com.logistics.mapper.OrderMapper;
import com.logistics.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private MallMapper mallMapper;
    
    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(CreateOrderRequest request, Integer customerId) {
        // 1. 校验商品是否存在
        Mall mall = mallMapper.selectById(request.getProductId());
        if (mall == null) {
            throw new RuntimeException("商品不存在");
        }
        
        // 2. 校验商品是否已上架
        if (mall.getIsPublished() == null || mall.getIsPublished() != 1) {
            throw new RuntimeException("商品未上架");
        }
        
        // 3. 校验库存是否充足
        if (mall.getAvailableQuantity() < request.getQuantity()) {
            throw new RuntimeException("库存不足，当前可用库存：" + mall.getAvailableQuantity());
        }
        
        // 4. 校验价格是否一致（防止前端篡改价格）
        if (mall.getPrice().compareTo(request.getPrice()) != 0) {
            throw new RuntimeException("商品价格已变动，请刷新页面");
        }
        
        // 5. 获取商户ID（从inventory表获取）
        QueryWrapper<Inventory> inventoryQuery = new QueryWrapper<>();
        inventoryQuery.eq("product_id", request.getProductId());
        Inventory inventory = inventoryMapper.selectOne(inventoryQuery);
        if (inventory == null) {
            throw new RuntimeException("商品信息异常");
        }
        Integer merchantId = inventory.getUserId();
        
        // 6. 计算总金额
        BigDecimal totalAmount = mall.getPrice().multiply(new BigDecimal(request.getQuantity()));
        
        // 7. 创建订单
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setCustomerId(customerId);
        order.setMerchantId(merchantId);
        order.setProductName(mall.getProductName());
        order.setQuantity(request.getQuantity());
        order.setUnitPrice(mall.getPrice());
        order.setTotalAmount(totalAmount);
        order.setImageUrl(mall.getImageUrl());
        order.setStatus(0); // 待发货
        order.setOrderTime(LocalDateTime.now());
        
        // 8. 保存订单
        orderMapper.insert(order);
        
        return order;
    }
    
    @Override
    public List<Order> getCustomerOrders(Integer customerId, Integer status, String search) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        
        // 查询该顾客的订单
        queryWrapper.eq("customer_id", customerId);
        
        // 按状态筛选
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        
        // 按商品名称搜索
        if (search != null && !search.trim().isEmpty()) {
            queryWrapper.like("product_name", search);
        }
        
        // 按下单时间倒序排列
        queryWrapper.orderByDesc("order_time");
        
        return orderMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Order> getMerchantOrders(Integer merchantId, Integer status, String search) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        
        // 查询该商户的订单
        queryWrapper.eq("merchant_id", merchantId);
        
        // 按状态筛选
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        
        // 按商品名称搜索
        if (search != null && !search.trim().isEmpty()) {
            queryWrapper.like("product_name", search);
        }
        
        // 按下单时间倒序排列
        queryWrapper.orderByDesc("order_time");
        
        return orderMapper.selectList(queryWrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmReceipt(Integer orderId, Integer customerId) {
        // 1. 查询订单是否存在
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        // 2. 验证订单是否属于该顾客
        if (!order.getCustomerId().equals(customerId)) {
            throw new RuntimeException("无权操作此订单");
        }
        
        // 3. 验证订单状态（只有待收货状态才能确认收货）
        if (order.getStatus() != 2) {
            throw new RuntimeException("订单状态不正确，无法确认收货");
        }
        
        // 4. 更新订单状态为已收货(5)
        order.setStatus(5);
        order.setReceiveTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shipOrder(Integer orderId, Integer merchantId) {
        // 1. 查询订单是否存在
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        // 2. 验证订单是否属于该商户
        if (!order.getMerchantId().equals(merchantId)) {
            throw new RuntimeException("无权操作此订单");
        }
        
        // 3. 验证订单状态（只有未发货状态才能发货）
        if (order.getStatus() != 0) {
            throw new RuntimeException("订单状态不正确，无法发货");
        }
        
        // 4. 更新订单状态为已发货(1)
        order.setStatus(1);
        order.setShipTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }
}
