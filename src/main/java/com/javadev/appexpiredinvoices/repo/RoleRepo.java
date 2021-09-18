package com.javadev.appexpiredinvoices.repo;

import com.javadev.appexpiredinvoices.entity.Role;
import com.javadev.appexpiredinvoices.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {
    Role findByRoleName(EnumRole roleName);
}
