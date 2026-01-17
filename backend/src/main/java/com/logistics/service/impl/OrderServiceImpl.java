package com.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.dto.AmapRouteResponse;
import com.logistics.dto.CreateOrderRequest;
import com.logistics.entity.Address;
import com.logistics.entity.DeliveryBatch;
import com.logistics.entity.DeliveryBatchOrder;
import com.logistics.entity.DeliveryPersonnel;
import com.logistics.entity.Inventory;
import com.logistics.entity.Mall;
import com.logistics.entity.Order;
import com.logistics.entity.Warehouse;
import com.logistics.mapper.AddressMapper;
import com.logistics.mapper.DeliveryBatchMapper;
import com.logistics.mapper.DeliveryBatchOrderMapper;
import com.logistics.mapper.DeliveryPersonnelMapper;
import com.logistics.mapper.InventoryMapper;
import com.logistics.mapper.MallMapper;
import com.logistics.mapper.OrderMapper;
import com.logistics.mapper.WarehouseMapper;
import com.logistics.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    
    @Autowired
    private AddressMapper addressMapper;
    
    @Autowired
    private DeliveryPersonnelMapper deliveryPersonnelMapper;
    
    @Autowired
    private WarehouseMapper warehouseMapper;
    
    @Autowired
    private DeliveryBatchMapper deliveryBatchMapper;
    
    @Autowired
    private DeliveryBatchOrderMapper deliveryBatchOrderMapper;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${amap.key}")
    private String amapKey;
    
    @Value("${amap.direction-api-url}")
    private String amapDirectionApiUrl;
    
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
        
        // 6. 获取收货地址ID（如果前端没传，则查询用户默认地址）
        Integer addressId = request.getAddressId();
        if (addressId == null) {
            // 查询用户的默认地址
            QueryWrapper<Address> addressQuery = new QueryWrapper<>();
            addressQuery.eq("user_id", customerId);
            addressQuery.eq("is_default", 1);
            Address defaultAddress = addressMapper.selectOne(addressQuery);
            if (defaultAddress != null) {
                addressId = defaultAddress.getId().intValue();
            }
        }
        
        // 7. 计算总金额
        BigDecimal totalAmount = mall.getPrice().multiply(new BigDecimal(request.getQuantity()));
        
        // 8. 创建订单
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setCustomerId(customerId);
        order.setMerchantId(merchantId);
        order.setAddressId(addressId);
        order.setProductName(mall.getProductName());
        order.setQuantity(request.getQuantity());
        order.setUnitPrice(mall.getPrice());
        order.setTotalAmount(totalAmount);
        order.setImageUrl(mall.getImageUrl());
        order.setStatus(0); // 待发货
        order.setOrderTime(LocalDateTime.now());
        
        // 9. 保存订单
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
    
    @Override
    public List<Order> getPendingPickupOrders(Long deliveryPersonnelId, String search) {
        // 根据配送员ID获取所属仓库
        DeliveryPersonnel personnel = deliveryPersonnelMapper.selectOne(
            new LambdaQueryWrapper<DeliveryPersonnel>().eq(DeliveryPersonnel::getUserId, deliveryPersonnelId)
        );
        if (personnel == null || personnel.getWarehouseId() == null) {
            throw new RuntimeException("配送员或仓库信息不存在");
        }
        return orderMapper.selectPendingPickupOrders(personnel.getWarehouseId(), search);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmPickup(Integer orderId) {
        // 1. 查询订单是否存在
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        
        // 2. 验证订单状态（只有已发货状态才能揽收）
        if (order.getStatus() != 1) {
            throw new RuntimeException("订单状态不正确，无法揽收");
        }
        
        // 3. 更新订单状态为已揽收(2)
        order.setStatus(2);
        order.setPickupTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }
    
    @Override
    public List<Order> getPendingDeliveryOrders(Long deliveryPersonnelId) {
        // 根据配送员ID获取所属仓库
        DeliveryPersonnel personnel = deliveryPersonnelMapper.selectOne(
            new LambdaQueryWrapper<DeliveryPersonnel>().eq(DeliveryPersonnel::getUserId, deliveryPersonnelId)
        );
        if (personnel == null || personnel.getWarehouseId() == null) {
            throw new RuntimeException("配送员或仓库信息不存在");
        }
        return orderMapper.selectPendingDeliveryOrders(personnel.getWarehouseId());
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public com.logistics.dto.CreateBatchResponse createDeliveryBatch(Long deliveryPersonnelId, List<Integer> orderIds) {
        if (deliveryPersonnelId == null) {
            throw new RuntimeException("配送员ID不能为空");
        }
        if (orderIds == null || orderIds.isEmpty()) {
            throw new RuntimeException("订单列表不能为空");
        }
        if (orderIds.size() > 5) {
            throw new RuntimeException("每个批次最多只能选择5个订单");
        }

        // 配送员与仓库
        DeliveryPersonnel personnel = deliveryPersonnelMapper.selectOne(
            new LambdaQueryWrapper<DeliveryPersonnel>().eq(DeliveryPersonnel::getUserId, deliveryPersonnelId)
        );
        if (personnel == null || personnel.getWarehouseId() == null) {
            throw new RuntimeException("配送员或仓库信息不存在");
        }
        Warehouse warehouse = warehouseMapper.selectById(personnel.getWarehouseId());
        if (warehouse == null || warehouse.getLongitude() == null || warehouse.getLatitude() == null) {
            throw new RuntimeException("仓库坐标信息不完整");
        }

        // 订单与地址：只使用顾客默认地址
        List<Order> orders = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        for (Integer orderId : orderIds) {
            Order order = orderMapper.selectById(orderId);
            if (order == null) {
                throw new RuntimeException("订单不存在: " + orderId);
            }
            if (order.getStatus() != 2) {
                throw new RuntimeException("订单状态不正确，只能配送已揽收的订单: " + orderId);
            }

            // 顾客默认地址
            Address addr = addressMapper.selectOne(
                new LambdaQueryWrapper<Address>()
                    .eq(Address::getUserId, order.getCustomerId().longValue())
                    .eq(Address::getIsDefault, 1)
            );
            if (addr == null) {
                throw new RuntimeException("顾客未设置默认收货地址: " + orderId);
            }
            if (addr.getLongitude() == null || addr.getLatitude() == null) {
                throw new RuntimeException("默认收货地址缺少坐标信息: " + orderId);
            }

            orders.add(order);
            addresses.add(addr);
        }

        // 构建高德API请求参数
        String origin = warehouse.getLongitude().toPlainString() + "," + warehouse.getLatitude().toPlainString();
        String destination;
        String waypoints = null;
        if (addresses.size() == 1) {
            Address dest = addresses.get(0);
            destination = dest.getLongitude().toPlainString() + "," + dest.getLatitude().toPlainString();
        } else {
            Address dest = addresses.get(addresses.size() - 1);
            destination = dest.getLongitude().toPlainString() + "," + dest.getLatitude().toPlainString();
            waypoints = addresses.subList(0, addresses.size() - 1).stream()
                .map(a -> a.getLongitude().toPlainString() + "," + a.getLatitude().toPlainString())
                .collect(Collectors.joining(";"));
        }

        String apiUrl = String.format("%s?key=%s&origin=%s&destination=%s&strategy=0",
            amapDirectionApiUrl, amapKey, origin, destination);
        if (waypoints != null && !waypoints.isEmpty()) {
            apiUrl += "&waypoints=" + waypoints;
        }

        // 先用 String 接收原始响应，便于调试
        String rawResponse;
        try {
            rawResponse = restTemplate.getForObject(apiUrl, String.class);
            System.out.println("高德API原始响应: " + rawResponse);
        } catch (Exception e) {
            System.err.println("高德API调用失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("调用高德路径规划API失败: " + e.getMessage());
        }

        // 将 JSON 字符串反序列化为 DTO
        AmapRouteResponse routeResp;
        try {
            ObjectMapper mapper = new ObjectMapper();
            routeResp = mapper.readValue(rawResponse, AmapRouteResponse.class);
        } catch (Exception e) {
            System.err.println("高德API响应解析失败: " + e.getMessage());
            System.err.println("原始响应: " + rawResponse);
            e.printStackTrace();
            throw new RuntimeException("解析高德API响应失败: " + e.getMessage());
        }
        
        if (routeResp == null || !"1".equals(routeResp.getStatus()) || routeResp.getRoute() == null ||
            routeResp.getRoute().getPaths() == null || routeResp.getRoute().getPaths().isEmpty()) {
            String errMsg = routeResp != null ? "状态: " + routeResp.getStatus() + ", 信息: " + routeResp.getInfo() : "响应为null";
            throw new RuntimeException("高德API返回错误: " + errMsg);
        }

        AmapRouteResponse.Path path = routeResp.getRoute().getPaths().get(0);
        Integer totalDistance = path.getDistance() != null ? Integer.parseInt(path.getDistance()) : null;
        Integer totalDuration = path.getDuration() != null ? Integer.parseInt(path.getDuration()) : null;
        String routePolyline = path.getSteps() != null ? path.getSteps().stream()
            .map(AmapRouteResponse.Step::getPolyline)
            .filter(p -> p != null && !p.isEmpty())
            .collect(Collectors.joining(";")) : "";

        // 保存批次
        DeliveryBatch batch = new DeliveryBatch();
        batch.setDriverId(personnel.getId());
        batch.setWarehouseId(warehouse.getId());
        batch.setStatus(0);
        batch.setCreatedAt(LocalDateTime.now());
        batch.setRoutePolyline(routePolyline);
        batch.setTotalDistance(totalDistance);
        batch.setTotalDuration(totalDuration);
        deliveryBatchMapper.insert(batch);

        // 计算停靠顺序
        List<Integer> stopOrder = new ArrayList<>();
        if (path.getWaypointOrder() != null && !path.getWaypointOrder().isEmpty()) {
            for (String idx : path.getWaypointOrder().split(",")) {
                stopOrder.add(Integer.parseInt(idx.trim()));
            }
            stopOrder.add(addresses.size() - 1);
        } else {
            for (int i = 0; i < addresses.size(); i++) stopOrder.add(i);
        }

        // 保存批次-订单关联
        for (int seq = 0; seq < stopOrder.size(); seq++) {
            int addrIdx = stopOrder.get(seq);
            Order o = orders.get(addrIdx);
            DeliveryBatchOrder bo = new DeliveryBatchOrder();
            bo.setBatchId(batch.getId());
            bo.setOrderId(o.getOrderId());
            bo.setStopSequence(seq + 1);
            deliveryBatchOrderMapper.insert(bo);
        }

        // 更新订单状态为运输中
        for (Order o : orders) {
            o.setStatus(3);
            o.setDeliveryTime(LocalDateTime.now());
            orderMapper.updateById(o);
        }

        // 构建响应
        com.logistics.dto.CreateBatchResponse resp = new com.logistics.dto.CreateBatchResponse();
        resp.setBatchId(batch.getId());
        resp.setTotalDistance(totalDistance);
        resp.setTotalDuration(totalDuration);
        resp.setOrderCount(orders.size());
        // 将停靠顺序映射为订单ID序列
        java.util.List<Integer> stopOrderByOrderId = new java.util.ArrayList<>();
        for (int idx : stopOrder) {
            stopOrderByOrderId.add(orders.get(idx).getOrderId());
        }
        resp.setStopOrder(stopOrderByOrderId);
        return resp;
    }
    
    @Override
    public List<Order> getDeliveryBatchOrders(Integer warehouseId) {
        List<Order> orders = orderMapper.selectDeliveryBatchOrders(warehouseId);
        
        // 为每个订单加载Address信息
        for (Order order : orders) {
            if (order.getAddressId() != null) {
                Address address = addressMapper.selectById(order.getAddressId());
                order.setAddress(address);
            }
        }
        
        return orders;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeDelivery(List<Integer> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            throw new RuntimeException("订单列表不能为空");
        }
        
        // 批量更新订单状态
        for (Integer orderId : orderIds) {
            Order order = orderMapper.selectById(orderId);
            if (order == null) {
                throw new RuntimeException("订单不存在: " + orderId);
            }
            
            // 验证订单状态（只有运输中的订单才能完成送货）
            if (order.getStatus() != 3) {
                throw new RuntimeException("订单状态不正确，无法完成送货: " + orderId);
            }
            
            // 更新订单状态为已到达(4)
            order.setStatus(4);
            orderMapper.updateById(order);
        }
    }
}
