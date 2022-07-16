package com.elvislee.onlineshop.service;

import com.elvislee.onlineshop.dto.UserLoginRequest;
import com.elvislee.onlineshop.dto.UserRegisterRequest;
import com.elvislee.onlineshop.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);

    User getUserById(Integer userId);
}
