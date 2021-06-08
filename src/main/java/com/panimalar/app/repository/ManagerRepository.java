package com.panimalar.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long>{
    public Optional<Manager> findByEmail(String email);
}
