package com.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.logistics.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存Mapper接口
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {
}
