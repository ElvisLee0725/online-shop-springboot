package com.elvislee.onlineshop.dao;

import com.elvislee.onlineshop.dto.UserRegisterRequest;
import com.elvislee.onlineshop.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
