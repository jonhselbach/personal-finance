package com.metodosageis.personal_finance.repository;

import com.metodosageis.personal_finance.enums.TransactionType;
import com.metodosageis.personal_finance.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction> findByType(TransactionType type);
}
