package com.panimalar.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
	
	public Optional<Account> findByAccountNumber(Long accountNumber);
}
