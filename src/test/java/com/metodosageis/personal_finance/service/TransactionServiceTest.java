package com.metodosageis.personal_finance.service;

import com.metodosageis.personal_finance.dto.TransactionDTO;
import com.metodosageis.personal_finance.dto.TransactionWithBalance;
import com.metodosageis.personal_finance.enums.TransactionType;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.model.Transaction;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import com.metodosageis.personal_finance.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.metodosageis.personal_finance.enums.TransactionType.DESPESA;
import static com.metodosageis.personal_finance.enums.TransactionType.RECEITA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes de unidade da camada de serviço de Transaction.
 * Aplica TDD: cada teste define o comportamento esperado antes da implementação.
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction createTransaction(double amount, TransactionType type) {
        return new Transaction(
                amount,
                type,
                new Category(),
                LocalDate.now(),
                "Teste"
        );
    }

    @Test
    void testCreateTransactionUpdatesBalance() {
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(100.00);
        dto.setType(TransactionType.RECEITA);
        dto.setCategoryId(1L);
        dto.setDate(LocalDate.now());
        dto.setDescription("Receita de teste");

        Category mockCategory = new Category();
        when(categoryRepository.findById(dto.getCategoryId()))
                .thenReturn(Optional.of(mockCategory));

        Transaction savedTransaction = new Transaction(
                dto.getAmount(),
                dto.getType(),
                mockCategory,
                dto.getDate(),
                dto.getDescription()
        );
        savedTransaction.setId(1L);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);

        when(transactionRepository.findAll()).thenReturn(List.of(savedTransaction));

        var result = transactionService.create(dto);

        assertNotNull(result);
        assertEquals(new BigDecimal("0.01"), result.getBalance());
    }

    @Test
    void testDeleteTransactionUpdatesBalance() {
        Long transactionId = 1L;
        when(transactionRepository.existsById(transactionId)).thenReturn(true);
        transactionService.delete(transactionId);

        BigDecimal updatedBalance = transactionService.calcularSaldo();
        assertNotNull(updatedBalance);
    }

    @Test
    void deveCalcularSaldoQuandoExistemApenasReceitas() {
        List<Transaction> receitas = Arrays.asList(
                createTransaction(100.0, RECEITA),
                createTransaction(200.0, RECEITA)
        );

        when(transactionRepository.findAll()).thenReturn(receitas);

        BigDecimal saldo = transactionService.calcularSaldo();

        assertEquals(new BigDecimal("300.00"), saldo);
    }

    @Test
    void deveCalcularSaldoNegativoQuandoExistemApenasDespesas() {
        List<Transaction> despesas = Arrays.asList(
                createTransaction(50.0, DESPESA),
                createTransaction(70.0, DESPESA)
        );

        when(transactionRepository.findAll()).thenReturn(despesas);

        BigDecimal saldo = transactionService.calcularSaldo();

        assertEquals(new BigDecimal("-120.00"), saldo);
    }

    @Test
    void deveCalcularSaldoQuandoExistemReceitasEDespesas() {
        List<Transaction> transacoes = Arrays.asList(
                createTransaction(500.0, RECEITA),
                createTransaction(150.0, DESPESA),
                createTransaction(100.0, DESPESA)
        );

        when(transactionRepository.findAll()).thenReturn(transacoes);

        BigDecimal saldo = transactionService.calcularSaldo();

        assertEquals(new BigDecimal("250.00"), saldo);
    }

    @Test
    void deveRetornarSaldoZeroQuandoNaoExistemTransacoes() {
        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        BigDecimal saldo = transactionService.calcularSaldo();

        assertEquals(new BigDecimal("0.00"), saldo);
    }
}