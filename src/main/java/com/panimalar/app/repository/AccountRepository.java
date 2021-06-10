package com.panimalar.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.Account;
import com.panimalar.app.model.Branch;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
	
	public Optional<Account> findByAccountNumber(Long accountNumber);
	
	@Query("select a from Account a where a.isActive = false and a.accountBranch.branchId = :branchId")
	public List<Account> findByNonActive(@Param("branchId") Long branchId);
	
	@Query("select a from Account a where a.isActive = true and a.accountBranch.branchId = :branchId")
	public List<Account> findByActive(@Param("branchId") Long branchId);	
}
