package com.logistics.controller;

import com.logistics.dto.LoginRequest;
import com.logistics.dto.LoginResponse;
import com.logistics.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        System.out.println("收到登录请求: username=" + req.getUsername() + ", role=" + req.getRole());
        return userService.login(req);
    }
    
    @GetMapping("/test")
    public String test() {
        return "Backend is running!";
    }
}