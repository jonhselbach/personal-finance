package com.metodosageis.personal_finance.integration;

import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import com.metodosageis.personal_finance.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // usa application-test.properties
@Transactional // rollback automático após cada teste
class CategoriaIntegrationTest {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryService service;

    @BeforeEach
    void setUp() {
        repository.deleteAll(); // limpa o banco antes de cada teste
    }

    @Test
    void deveSalvarCategoriaPersonalizada() {
        Category categoria = new Category(null, "EducacaoTeste", 500.0);
        Category salva = service.createCategory(categoria);

        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getNome()).isEqualTo("EducacaoTeste");
        assertThat(salva.getLimiteGastos()).isEqualTo(500.0);
    }

    @Test
    void deveListarCategoriasPreDefinidas() {
        // Inserindo categorias únicas para este teste
        Category c1 = new Category(null, "AlimentacaoTeste", 1000.0);
        Category c2 = new Category(null, "TransporteTeste", 800.0);
        repository.saveAll(List.of(c1, c2));

        List<Category> categorias = service.listCategory();

        assertThat(categorias).hasSize(2);
        assertThat(categorias).extracting("nome")
                .containsExactlyInAnyOrder("AlimentacaoTeste", "TransporteTeste");
    }

    @Test
    void deveAtualizarLimiteDeGastos() {
        Category categoria = new Category(null, "LazerTeste", 300.0);
        Category salva = repository.save(categoria);

        Category atualizada = service.updateLimit(salva.getId(), 600.0);

        assertThat(atualizada.getLimiteGastos()).isEqualTo(600.0);
    }
}
