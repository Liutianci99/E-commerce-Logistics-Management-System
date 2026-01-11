package com.logistics.controller;

import com.logistics.common.Result;
import com.logistics.dto.InventoryQueryRequest;
import com.logistics.dto.StockInRequest;
import com.logistics.entity.Inventory;
import com.logistics.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 库存管理Controller
 */
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    /**
     * 商品入库
     * @param request 入库请求数据
     * @param imageFile 商品图片文件
     * @return 入库结果
     */
    @PostMapping("/stock-in")
    public Result<Inventory> stockIn(
            @Valid @ModelAttribute StockInRequest request,
            @RequestParam("image") MultipartFile imageFile) {
        
        Inventory inventory = inventoryService.stockIn(request, imageFile);
        return Result.success(inventory);
    }
    
    /**
     * 查询库存列表
     * @param request 查询条件
     * @return 库存列表
     */
    @GetMapping("/list")
    public Result<List<Inventory>> queryInventory(InventoryQueryRequest request) {
        List<Inventory> list = inventoryService.queryInventory(request);
        return Result.success(list);
    }
}
