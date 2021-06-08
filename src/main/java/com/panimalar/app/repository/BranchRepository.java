package com.panimalar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long>{

}
