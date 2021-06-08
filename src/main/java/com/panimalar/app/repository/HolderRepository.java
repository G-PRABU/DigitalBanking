package com.panimalar.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.Holder;
import com.panimalar.app.model.Account;

@Repository
public interface HolderRepository extends JpaRepository<Holder,Long>{
	
	public Optional<Holder> findByHolderAccount(Account account);
}
