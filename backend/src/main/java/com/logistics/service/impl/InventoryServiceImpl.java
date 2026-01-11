package com.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logistics.dto.InventoryQueryRequest;
import com.logistics.dto.StockInRequest;
import com.logistics.entity.Inventory;
import com.logistics.mapper.InventoryMapper;
import com.logistics.service.InventoryService;
import com.logistics.service.R2StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 库存服务实现类
 */
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    
    private final InventoryMapper inventoryMapper;
    private final R2StorageService r2StorageService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Inventory stockIn(StockInRequest request, MultipartFile imageFile) {
        // 上传图片到Cloudflare R2并获取URL
        String imageUrl = r2StorageService.uploadFile(imageFile);
        
        // 创建库存记录
        Inventory inventory = new Inventory();
        inventory.setUserId(request.getUserId());
        inventory.setProductName(request.getProductName());
        inventory.setQuantity(request.getQuantity());
        inventory.setStockInDate(request.getStockInDate());
        inventory.setImageUrl(imageUrl);
        inventory.setIsPublished(0); // 默认未上架
        
        // 保存到数据库
        inventoryMapper.insert(inventory);
        
        return inventory;
    }
    
    @Override
    public List<Inventory> queryInventory(InventoryQueryRequest request) {
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        
        // 根据用户ID查询
        if (request.getUserId() != null) {
            queryWrapper.eq("user_id", request.getUserId());
        }
        
        // 根据商品名称模糊查询
        if (request.getProductName() != null && !request.getProductName().trim().isEmpty()) {
            queryWrapper.like("product_name", request.getProductName());
        }
        
        // 根据库存范围查询
        if (request.getMinStock() != null) {
            queryWrapper.ge("quantity", request.getMinStock());
        }
        if (request.getMaxStock() != null) {
            queryWrapper.le("quantity", request.getMaxStock());
        }
        
        // 根据上架状态查询
        if (request.getIsPublished() != null) {
            queryWrapper.eq("is_published", request.getIsPublished());
        }
        
        // 按入库时间倒序排列
        queryWrapper.orderByDesc("stock_in_date");
        
        return inventoryMapper.selectList(queryWrapper);
    }
}
