package com.panimalar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

}
