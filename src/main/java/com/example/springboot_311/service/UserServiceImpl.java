package com.example.springboot_311.service;

import com.example.springboot_311.dao.RoleDao;
import com.example.springboot_311.dao.UserDao;
import com.example.springboot_311.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public User findByUserName(String name) {
        return userDao.findByUserName(name);
    }

    @Override
    public User findByEmail(String name) {
        return userDao.findByEmail(name);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Override
    public List<User> listUser() {
        return userDao.listAllUser();
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

       User user = userDao.findByEmail(s);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not FOUND !!!!!");
        }
        return user;
    }
}
