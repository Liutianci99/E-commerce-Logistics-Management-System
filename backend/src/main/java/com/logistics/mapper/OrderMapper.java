package com.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.logistics.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * 查询待揽收订单列表（状态=1），支持仓库和商品名称筛选
     */
    @Select("<script>" +
            "SELECT o.*, w.name AS warehouseName " +
            "FROM orders o " +
            "LEFT JOIN warehouse w ON o.warehouse_id = w.id " +
            "WHERE o.status = 1 " +
            "<if test='warehouseId != null'>" +
            "  AND o.warehouse_id = #{warehouseId} " +
            "</if>" +
            "<if test='search != null and search != \"\"'>" +
            "  AND o.product_name LIKE CONCAT('%', #{search}, '%') " +
            "</if>" +
            "ORDER BY o.ship_time DESC" +
            "</script>")
    List<Order> selectPendingPickupOrders(@Param("warehouseId") Integer warehouseId, 
                                          @Param("search") String search);
}
