package com.example.springboot_311.dao;


import com.example.springboot_311.model.Role;

import java.util.List;

public interface RoleDao {
    void save(Role role);
    void delete(Role role);
    Role getById(Long id);
    Role getRoleByName(String rolename);
    List<Role> getAllRoles();
}
