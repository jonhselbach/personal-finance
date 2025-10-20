package com.metodosageis.personal_finance.controller;

import com.metodosageis.personal_finance.dto.TransactionDTO;
import com.metodosageis.personal_finance.dto.TransactionWithBalance;
import com.metodosageis.personal_finance.model.Transaction;
import com.metodosageis.personal_finance.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransactionWithBalance> create(@Valid @RequestBody TransactionDTO dto) {
        TransactionWithBalance response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionWithBalance> update(@PathVariable Long id, @Valid @RequestBody TransactionDTO dto) {
        TransactionWithBalance response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BigDecimal> delete(@PathVariable Long id) {
        BigDecimal updatedBalance = service.delete(id);
        return ResponseEntity.noContent().header("Saldo Atual", updatedBalance.toString()).build();
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> list() {
        List<Transaction> transactions = service.list();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id) {
        Transaction transaction = service.getById(id);
        return ResponseEntity.ok(transaction);
    }
}
