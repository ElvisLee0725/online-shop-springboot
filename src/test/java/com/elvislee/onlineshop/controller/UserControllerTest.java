package com.elvislee.onlineshop.controller;

import com.elvislee.onlineshop.dao.UserDao;
import com.elvislee.onlineshop.dto.UserLoginRequest;
import com.elvislee.onlineshop.dto.UserRegisterRequest;
import com.elvislee.onlineshop.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void emailAndPassword_register_registerSuccess() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("testing@gmail.com");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.userAccount", equalTo("testing@gmail.com")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        assertNotEquals(user.getPassword(), userRegisterRequest.getPassword());
    }

    @Test
    public void invalidEmailFormat_register_returnBadRequest() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("abcde");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void emailAccountRegistered_register_returnBadRequest() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test1@gmail.com");
        userRegisterRequest.setPassword("12345");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void correctEmailPassword_login_loginSuccess() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("myemail@gmail.com");
        userRegisterRequest.setPassword("123");
        register(userRegisterRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("myemail@gmail.com");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.userAccount", equalTo(userLoginRequest.getEmail())))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));

    }

    @Test
    public void wrongPassword_login_loginFail() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test@gmail.com");
        userRegisterRequest.setPassword("123");
        register(userRegisterRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@gmail.com");
        userLoginRequest.setPassword("abc");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidEmail_login_loginFail() throws Exception {

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("sdfsagerage");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void emailNotExist_login_loginFail() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("unknown@gmail.com");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    private void register(UserRegisterRequest userRegisterRequest) throws Exception {
        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }
}