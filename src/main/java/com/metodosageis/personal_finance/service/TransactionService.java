package com.metodosageis.personal_finance.service;

import com.metodosageis.personal_finance.dto.TransactionDTO;
import com.metodosageis.personal_finance.dto.TransactionWithBalance;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.model.Transaction;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import com.metodosageis.personal_finance.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.metodosageis.personal_finance.enums.TransactionType.DESPESA;
import static com.metodosageis.personal_finance.enums.TransactionType.RECEITA;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public TransactionWithBalance create(TransactionDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Transaction transaction = new Transaction(
                null,
                dto.getAmount(),
                dto.getType(),
                category,
                dto.getDate(),
                dto.getDescription()
        );
        transaction = transactionRepository.save(transaction);

        BigDecimal saldoAtualizado = calcularSaldo();
        return new TransactionWithBalance(transaction, saldoAtualizado);
    }

    public TransactionWithBalance update(Long id, TransactionDTO dto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        Category category = categoryRepository.findById(dto.getIdcategory())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setCategory(category);
        transaction.setDate(dto.getDate());
        transaction.setDescription(dto.getDescription());

        transaction = transactionRepository.save(transaction);

        BigDecimal saldoAtualizado = calcularSaldo();
        return new TransactionWithBalance(transaction, saldoAtualizado);
    }

    public BigDecimal delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException("Transação não encontrada");
        }

        transactionRepository.deleteById(id);

        return calcularSaldo();
    }

    public List<Transaction> list() {
        return transactionRepository.findAll();
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
    }

    public BigDecimal calcularSaldo() {
        List<Transaction> transactionList = transactionRepository.findAll();

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal outcome = BigDecimal.ZERO;

        for (Transaction transaction : transactionList) {
            if (transaction.getType() == RECEITA) {
                income = income.add(BigDecimal.valueOf(transaction.getAmount()));
            } else if (transaction.getType() == DESPESA) {
                outcome = outcome.add(BigDecimal.valueOf(transaction.getAmount()));
            }
        }

        return income.subtract(outcome).setScale(2, RoundingMode.HALF_UP);
    }
}

