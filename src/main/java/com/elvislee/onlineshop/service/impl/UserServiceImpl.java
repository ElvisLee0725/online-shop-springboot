package com.elvislee.onlineshop.service.impl;

import com.elvislee.onlineshop.dao.UserDao;
import com.elvislee.onlineshop.dto.UserRegisterRequest;
import com.elvislee.onlineshop.model.User;
import com.elvislee.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
