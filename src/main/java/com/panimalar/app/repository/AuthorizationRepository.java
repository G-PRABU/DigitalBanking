package com.panimalar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.Authorization;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization,Long>{

}
