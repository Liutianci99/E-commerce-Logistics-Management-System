package com.logistics.service;

import com.logistics.entity.Warehouse;

import java.util.List;

/**
 * 仓库服务接口
 */
public interface WarehouseService {
    
    /**
     * 获取所有仓库列表
     */
    List<Warehouse> listAll();
}
