package com.metodosageis.personal_finance.service;

import com.metodosageis.personal_finance.dto.TransactionDTO;
import com.metodosageis.personal_finance.enums.TransactionType;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.model.Transaction;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import com.metodosageis.personal_finance.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private Category category;
    private TransactionDTO dto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        category = new Category(1L, "Alimentação", 500.0);

        transaction = new Transaction(
                1L, 200.0, TransactionType.EXPENSE, category,
                LocalDate.of(2025, 10, 21), "Supermercado"
        );

        dto = new TransactionDTO(
                1L,
                200.0,
                TransactionType.EXPENSE,
                1L,
                LocalDate.of(2025, 10, 21),
                "Supermercado"
        );
    }

    // 1️⃣ Criar transação
    @Test
    void deveCriarTransacaoComSucesso() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.create(dto);

        assertThat(result.getAmount()).isEqualTo(200.0);
        assertThat(result.getCategory().getName()).isEqualTo("Alimentação");

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    // 2️⃣ Criar transação com categoria inexistente
    @Test
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.create(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Categoria não encontrada");

        verify(transactionRepository, never()).save(any());
    }

    // 3️⃣ Atualizar transação existente
    @Test
    void deveAtualizarTransacao() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.update(1L, dto);

        assertThat(result.getDescription()).isEqualTo("Supermercado");
        assertThat(result.getCategory().getName()).isEqualTo("Alimentação");
        verify(transactionRepository, times(1)).save(transaction);
    }

    // 4️⃣ Atualizar transação inexistente
    @Test
    void deveLancarExcecaoQuandoAtualizarTransacaoInexistente() {
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.update(99L, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Transação não encontrada");
    }

    // 5️⃣ Listar transações
    @Test
    void deveListarTransacoes() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<Transaction> result = transactionService.list();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).isEqualTo("Supermercado");
    }

    // 6️⃣ Buscar por ID existente
    @Test
    void deveBuscarTransacaoPorId() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(200.0);
    }

    // 7️⃣ Deletar transação existente
    @Test
    void deveDeletarTransacaoComSucesso() {
        when(transactionRepository.existsById(1L)).thenReturn(true);

        transactionService.delete(1L);

        verify(transactionRepository, times(1)).deleteById(1L);
    }

    // 8️⃣ Deletar transação inexistente
    @Test
    void deveLancarExcecaoAoDeletarTransacaoInexistente() {
        when(transactionRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> transactionService.delete(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Transação não encontrada");

        verify(transactionRepository, never()).deleteById(any());
    }

}
