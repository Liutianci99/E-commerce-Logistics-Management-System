package com.logistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.logistics.dto.LoginRequest;
import com.logistics.dto.LoginResponse;
import com.logistics.entity.User;
import com.logistics.mapper.UserMapper;
import com.logistics.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public LoginResponse login(LoginRequest req) {
        System.out.println("开始查询用户: username=" + req.getUsername() + ", role=" + req.getRole());
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
            .eq(User::getUsername, req.getUsername())
            .eq(User::getRole, req.getRole()));
        System.out.println("查询结果: " + (user == null ? "null" : user.toString()));
        if (user == null) {
            return new LoginResponse(false, "用户或角色不存在", null);
        }
        if (!user.getPassword().equals(req.getPassword())) {
            return new LoginResponse(false, "密码错误", null);
        }
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
            user.getId(), 
            user.getUsername(), 
            user.getRole()
        );
        return new LoginResponse(true, "登录成功", userInfo);
    }
}