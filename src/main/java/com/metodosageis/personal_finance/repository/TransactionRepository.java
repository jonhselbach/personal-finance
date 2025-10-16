package com.metodosageis.personal_finance.repository;

import com.metodosageis.personal_finance.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
