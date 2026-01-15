package com.logistics.controller;

import com.logistics.common.Result;
import com.logistics.entity.Warehouse;
import com.logistics.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 仓库管理 Controller
 */
@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
@CrossOrigin
public class WarehouseController {
    
    private final WarehouseService warehouseService;
    
    /**
     * 获取所有仓库列表
     */
    @GetMapping("/list")
    public Result<List<Warehouse>> list() {
        List<Warehouse> warehouses = warehouseService.listAll();
        return Result.success(warehouses);
    }
}
