package com.metodosageis.personal_finance.dto;

import com.metodosageis.personal_finance.model.Transaction;

import java.math.BigDecimal;

public class TransactionWithBalance {
    private Transaction transaction;
    private BigDecimal balance;

    public TransactionWithBalance(Transaction transaction, BigDecimal balance) {
        this.transaction = transaction;
        this.balance = balance;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
