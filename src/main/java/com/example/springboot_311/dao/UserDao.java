package com.example.springboot_311.dao;

import com.example.springboot_311.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao {
    List<User> listAllUser();
    void addUser(User user);
    void removeUser(Long id);
    void updateUser(User user);
    User findByUserName(String name);
    User findByEmail(String name);
    User getUser(Long id);

}
