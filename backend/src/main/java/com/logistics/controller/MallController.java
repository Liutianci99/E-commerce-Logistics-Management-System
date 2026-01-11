package com.logistics.controller;

import com.logistics.common.Result;
import com.logistics.dto.ProductListingRequest;
import com.logistics.entity.Inventory;
import com.logistics.entity.Mall;
import com.logistics.service.MallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商城管理Controller
 */
@RestController
@RequestMapping("/mall")
@RequiredArgsConstructor
public class MallController {
    
    private final MallService mallService;
    
    /**
     * 获取未上架的商品列表
     * @param userId 用户ID
     * @return 未上架商品列表
     */
    @GetMapping("/offline-products")
    public Result<List<Inventory>> getOfflineProducts(@RequestParam Integer userId) {
        List<Inventory> products = mallService.getOfflineProducts(userId);
        return Result.success(products);
    }
    
    /**
     * 商品上架
     * @param request 上架请求数据
     * @return 上架结果
     */
    @PostMapping("/publish")
    public Result<Mall> publishProduct(@Valid @RequestBody ProductListingRequest request) {
        Mall mall = mallService.publishProduct(request);
        return Result.success(mall);
    }
    
    /**
     * 获取商城商品列表
     * @return 商城商品列表
     */
    @GetMapping("/products")
    public Result<List<Mall>> getMallProducts() {
        List<Mall> products = mallService.getMallProducts();
        return Result.success(products);
    }
}
