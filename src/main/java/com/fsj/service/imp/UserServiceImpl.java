package com.fsj.service.imp;

import com.fsj.dao.UserRepository;
import com.fsj.po.User;
import com.fsj.service.UserService;
import com.fsj.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public String checkUser1(User user) {
        String dbPass = user.getPassword();
        if(dbPass.equals( userRepository.findByUsernameAndPassword(user.getPassword(),MD5Utils.code(dbPass))));
        return null;
    }

    //注册功能
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
