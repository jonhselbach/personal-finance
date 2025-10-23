package com.metodosageis.personal_finance.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // converte objetos em JSON

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        categoryRepository.deleteAll(); // limpa o banco antes de cada teste
    }

    // 1️⃣ Criar categoria (POST)
    @Test
    void deveCriarCategoriaComSucesso() throws Exception {
        Category category = Category.builder()
                .name("Alimentação")
                .spendingLimit(500.0)
                .build();

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Alimentação"))
                .andExpect(jsonPath("$.spendingLimit").value(500.0));
    }

    // 2️⃣ Listar categorias (GET)
    @Test
    void deveListarTodasAsCategorias() throws Exception {
        categoryRepository.saveAll(List.of(
                new Category(null, "Transporte", 300.0),
                new Category(null, "Lazer", 200.0)
        ));

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Transporte"))
                .andExpect(jsonPath("$[1].name").value("Lazer"));
    }

    // 3️⃣ Atualizar categoria (PUT)
    @Test
    void deveAtualizarCategoria() throws Exception {
        Category original = categoryRepository.save(
                new Category(null, "Saúde", 400.0)
        );

        Category atualizado = Category.builder()
                .id(original.getId())
                .name("Saúde e Bem-Estar")
                .spendingLimit(600.0)
                .build();

        mockMvc.perform(put("/categorias/" + original.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Saúde e Bem-Estar"))
                .andExpect(jsonPath("$.spendingLimit").value(600.0));
    }

    // 4️⃣ Atualizar limite (PATCH)
    @Test
    void deveAtualizarLimiteDeGasto() throws Exception {
        Category category = categoryRepository.save(new Category(null, "Educação", 800.0));

        mockMvc.perform(patch("/categorias/" + category.getId() + "/limite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"spendingLimit\": 1200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spendingLimit").value(1200.0));

        // Confirma que salvou no banco
        Category atualizado = categoryRepository.findById(category.getId()).get();
        assertThat(atualizado.getSpendingLimit()).isEqualTo(1200.0);
    }

    // 5️⃣ Excluir categoria (DELETE)
    @Test
    void deveExcluirCategoria() throws Exception {
        Category category = categoryRepository.save(new Category(null, "Viagem", 1000.0));

        mockMvc.perform(delete("/categorias/" + category.getId()))
                .andExpect(status().isNoContent());

        assertThat(categoryRepository.findById(category.getId())).isEmpty();
    }
}
