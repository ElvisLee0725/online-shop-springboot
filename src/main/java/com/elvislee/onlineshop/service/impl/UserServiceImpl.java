package com.elvislee.onlineshop.service.impl;

import com.elvislee.onlineshop.dao.UserDao;
import com.elvislee.onlineshop.dto.UserLoginRequest;
import com.elvislee.onlineshop.dto.UserRegisterRequest;
import com.elvislee.onlineshop.model.User;
import com.elvislee.onlineshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if(user != null) {
            log.warn("This email account \"{}\" has been registered", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        if(user == null) {
            log.warn("{} is not registered", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(user.getPassword().equals(userLoginRequest.getPassword())) {
            return user;
        }

        log.warn("The password for {} is not correct", userLoginRequest.getEmail());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
