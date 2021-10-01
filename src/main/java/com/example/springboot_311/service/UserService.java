package com.example.springboot_311.service;

import com.example.springboot_311.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService  {
    List<User> listUser();
    void addUser(User user);
    void removeUser(Long id);
    public User getUser(Long id);
    public void updateUser(User user);
    User findByUserName(String name);
    User findByEmail(String name);
}
