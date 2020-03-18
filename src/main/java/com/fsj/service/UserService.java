package com.fsj.service;

import com.fsj.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

    User checkUser(String username, String password);
    String checkUser1(User user);
    //注册
    User saveUser(User user);
    User findByUsername(String username);
    //用户管理
    Page<User> findAll(Pageable pageable);
    //删除用户
    void deleteUser(Long id);
}
