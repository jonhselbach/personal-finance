package com.metodosageis.personal_finance.service;

import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes de unidade da camada de serviço de Categoria.
 * Aplica TDD: cada teste define o comportamento esperado antes da implementação.
 */
@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    @Test
    void deveCriarCategoriaPersonalizada() {
        Category categoria = new Category(null, "Educação", 500.0);
        when(repository.save(any())).thenReturn(new Category(1L, "Educação", 500.0));

        Category criada = service.createCategory(categoria);

        assertNotNull(criada.getId());
        assertEquals("Educação", criada.getNome());
        assertEquals(500.0, criada.getLimiteGastos());
        verify(repository).save(categoria);
    }

    @Test
    void deveRetornarCategoriasPreDefinidas() {

        when(repository.findAll()).thenReturn(List.of(
                new Category(1L, "Alimentação", 1000.0),
                new Category(2L, "Transporte", 500.0)
        ));

        List<Category> categorias = service.listCategory();

        assertFalse(categorias.isEmpty());
        assertEquals(2, categorias.size());
        assertEquals("Alimentação", categorias.get(0).getNome());
    }

    @Test
    void deveAtualizarLimiteDeGastos() {
        Category existente = new Category(1L, "Lazer", 300.0);
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenReturn(existente);

        Category atualizada = service.updateLimit(1L, 600.0);

        assertEquals(600.0, atualizada.getLimiteGastos());
        verify(repository).save(existente);
    }
}
