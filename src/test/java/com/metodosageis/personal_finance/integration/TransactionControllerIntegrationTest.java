package com.metodosageis.personal_finance.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metodosageis.personal_finance.dto.TransactionDTO;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.model.Transaction;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import com.metodosageis.personal_finance.repository.TransactionRepository;
import com.metodosageis.personal_finance.enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setup() {
        transactionRepository.deleteAll();
        categoryRepository.deleteAll();

        category = categoryRepository.save(new Category(null, "Alimentação", 500.0));
    }

    // 1️⃣ Criar transação
    @Test
    void deveCriarTransacao() throws Exception {
        TransactionDTO dto = new TransactionDTO(
                null, 200.0, TransactionType.DESPESA,
                category.getId(), LocalDate.of(2025, 10, 21), "Supermercado"
        );

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.amount").value(200.0))
                .andExpect(jsonPath("$.type").value("DESPESA"))
                .andExpect(jsonPath("$.category.id").value(category.getId()))
                .andExpect(jsonPath("$.description").value("Supermercado"));
    }

    // 2️⃣ Listar transações
    @Test
    void deveListarTransacoes() throws Exception {
        Transaction transaction = new Transaction(null, 150.0, TransactionType.DESPESA, category,
                LocalDate.of(2025, 10, 21), "Padaria");
        transactionRepository.save(transaction);

        mockMvc.perform(get("/transacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].description").value("Padaria"));
    }

    // 3️⃣ Buscar transação por ID
    @Test
    void deveBuscarTransacaoPorId() throws Exception {
        Transaction transaction = transactionRepository.save(
                new Transaction(null, 120.0, TransactionType.RECEITA, category,
                        LocalDate.of(2025, 10, 21), "Salário")
        );

        mockMvc.perform(get("/transacoes/" + transaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Salário"))
                .andExpect(jsonPath("$.amount").value(120.0));
    }

    // 4️⃣ Atualizar transação
    @Test
    void deveAtualizarTransacao() throws Exception {
        Transaction transaction = transactionRepository.save(
                new Transaction(null, 50.0, TransactionType.DESPESA, category,
                        LocalDate.of(2025, 10, 21), "Café")
        );

        TransactionDTO dtoAtualizado = new TransactionDTO(
                transaction.getId(), 60.0, TransactionType.DESPESA,
                category.getId(), LocalDate.of(2025, 10, 22), "Café da manhã"
        );

        mockMvc.perform(put("/transacoes/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(60.0))
                .andExpect(jsonPath("$.description").value("Café da manhã"))
                .andExpect(jsonPath("$.date").value("2025-10-22"));
    }

    // 5️⃣ Deletar transação
    @Test
    void deveDeletarTransacao() throws Exception {
        Transaction transaction = transactionRepository.save(
                new Transaction(null, 80.0, TransactionType.DESPESA, category,
                        LocalDate.of(2025, 10, 21), "Lanche")
        );

        mockMvc.perform(delete("/transacoes/" + transaction.getId()))
                .andExpect(status().isNoContent());

        assertThat(transactionRepository.findById(transaction.getId())).isEmpty();
    }
}