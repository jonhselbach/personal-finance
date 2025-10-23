package com.metodosageis.personal_finance.service;

import com.metodosageis.personal_finance.dto.TransactionDTO;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.model.Transaction;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import com.metodosageis.personal_finance.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public Transaction create(TransactionDTO dto) {
        Category category = categoryRepository.findById(dto.idcategory())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Transaction transaction = new Transaction(
                null,
                dto.amount(),
                dto.type(),
                category,
                dto.date(),
                dto.description()
        );
        return transactionRepository.save(transaction);
    }

    public Transaction update(Long id, TransactionDTO dto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        Category category = categoryRepository.findById(dto.idcategory())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());
        transaction.setCategory(category);
        transaction.setDate(dto.date());
        transaction.setDescription(dto.description());

        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException("Transação não encontrada");
        }
        transactionRepository.deleteById(id);
    }

    public List<Transaction> list() {
        return transactionRepository.findAll();
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
    }
}
