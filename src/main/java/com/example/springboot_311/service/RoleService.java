package com.example.springboot_311.service;


import com.example.springboot_311.model.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);
    void delete(Role role);
    Role getById(Long id);
    Role getRoleByName(String rolename);
    List<Role> getAllRoles();
}
