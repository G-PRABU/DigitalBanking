package com.panimalar.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panimalar.app.model.MoneyTransaction;

@Repository
public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction,Long>{

}
