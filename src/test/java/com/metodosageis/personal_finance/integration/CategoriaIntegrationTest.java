package com.metodosageis.personal_finance.integration;

import com.metodosageis.personal_finance.model.Categoria;
import com.metodosageis.personal_finance.repository.CategoriaRepository;
import com.metodosageis.personal_finance.service.CategoriaService;
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
    private CategoriaRepository repository;

    @Autowired
    private CategoriaService service;

    @BeforeEach
    void setUp() {
        repository.deleteAll(); // limpa o banco antes de cada teste
    }

    @Test
    void deveSalvarCategoriaPersonalizada() {
        Categoria categoria = new Categoria(null, "EducacaoTeste", 500.0);
        Categoria salva = service.criarCategoria(categoria);

        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getNome()).isEqualTo("EducacaoTeste");
        assertThat(salva.getLimiteGastos()).isEqualTo(500.0);
    }

    @Test
    void deveListarCategoriasPreDefinidas() {
        // Inserindo categorias únicas para este teste
        Categoria c1 = new Categoria(null, "AlimentacaoTeste", 1000.0);
        Categoria c2 = new Categoria(null, "TransporteTeste", 800.0);
        repository.saveAll(List.of(c1, c2));

        List<Categoria> categorias = service.listarCategorias();

        assertThat(categorias).hasSize(2);
        assertThat(categorias).extracting("nome")
                .containsExactlyInAnyOrder("AlimentacaoTeste", "TransporteTeste");
    }

    @Test
    void deveAtualizarLimiteDeGastos() {
        Categoria categoria = new Categoria(null, "LazerTeste", 300.0);
        Categoria salva = repository.save(categoria);

        Categoria atualizada = service.atualizarLimite(salva.getId(), 600.0);

        assertThat(atualizada.getLimiteGastos()).isEqualTo(600.0);
    }
}
