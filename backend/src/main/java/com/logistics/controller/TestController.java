package com.logistics.controller;

import com.logistics.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public Result<Map<String, String>> hello() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "Hello from E-commerce Logistics Management System!");
        data.put("version", "1.0.0");
        return Result.success(data);
    }
}
