package com.example.springboot_311.dao;


import com.example.springboot_311.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Transactional
    @Override
    public void save(Role role) {
        Role managed = entityManager.merge(role);
        entityManager.persist(managed);
    }

    @Transactional
    @Override
    public void delete(Role role) {
        Role managed = entityManager.merge(role);
        entityManager.remove(managed);
    }

    @Override
    public Role getById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String rolename) {
        return entityManager.createQuery("SELECT r FROM Role r where r.name = :name", Role.class)
                .setParameter("name", rolename)
                .getSingleResult();
    }
}
