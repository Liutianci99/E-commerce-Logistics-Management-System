package com.logistics.controller;

import com.logistics.common.Result;
import com.logistics.entity.Address;
import com.logistics.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 地址管理 Controller
 */
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
@CrossOrigin
public class AddressController {
    
    private final AddressService addressService;
    
    /**
     * 获取当前用户的所有地址
     */
    @GetMapping("/list")
    public Result<List<Address>> list(HttpServletRequest request) {
        System.out.println("========== 地址列表查询接口被调用 ==========");
        
        Long userId = (Long) request.getAttribute("userId");
        System.out.println("从请求中获取的 userId: " + userId);
        
        if (userId == null) {
            System.out.println("userId 为空，返回未登录错误");
            return Result.error("未登录");
        }
        
        List<Address> addresses = addressService.listByUserId(userId);
        System.out.println("查询到的地址数量: " + (addresses != null ? addresses.size() : 0));
        System.out.println("地址数据: " + addresses);
        
        return Result.success(addresses);
    }
    
    /**
     * 添加地址
     */
    @PostMapping
    public Result<Address> add(@RequestBody Address address, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录");
        }
        
        address.setUserId(userId);
        return addressService.add(address);
    }
    
    /**
     * 更新地址
     */
    @PutMapping("/{id}")
    public Result<Address> update(@PathVariable Long id, 
                                   @RequestBody Address address, 
                                   HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录");
        }
        
        address.setId(id);
        address.setUserId(userId);
        return addressService.update(address);
    }
    
    /**
     * 删除地址
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录");
        }
        
        return addressService.delete(id, userId);
    }
    
    /**
     * 设置默认地址
     */
    @PutMapping("/{id}/default")
    public Result<Void> setDefault(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录");
        }
        
        return addressService.setDefault(id, userId);
    }
}
