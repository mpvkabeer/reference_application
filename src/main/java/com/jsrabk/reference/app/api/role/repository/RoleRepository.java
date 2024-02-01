package com.jsrabk.reference.app.api.role.repository;

import com.jsrabk.reference.app.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}