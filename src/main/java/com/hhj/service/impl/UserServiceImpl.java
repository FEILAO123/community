package com.hhj.service.impl;

import com.hhj.dao.UserMapper;
import com.hhj.entity.User;
import com.hhj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserById(int id) {
        User user = userMapper.selectUserById(id);
        return user;
    }
}
