package com.panimalar.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.MoneyTransaction;

@Repository
public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction,Long>{

	@Query("select m from MoneyTransaction m where m.fromAccount = :accountNumber or m.toAccount = :accountNumber")
	public List<MoneyTransaction> findAllTransaction(@Param("accountNumber") Long accountNumber);
}
