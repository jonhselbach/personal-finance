package com.metodosageis.personal_finance.service;


import com.metodosageis.personal_finance.dto.CategoryDTO;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = Category.builder()
                .id(1L)
                .name("Alimentação")
                .spendingLimit(500.00)
                .build();
    }

    // Criação
    @Test
    void deveCriarCategoriaComSucesso() {

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertNotNull(result);
        assertEquals("Alimentação", result.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    // Listagem
    @Test
    void deveListarTodasAsCategorias() {

        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> result = categoryService.listCategory();

        assertEquals(1, result.size());
        assertEquals("Alimentação", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();

    }

    // 3️⃣ Edição
    @Test
    void deveEditarCategoriaComSucesso() throws Exception {
        CategoryDTO dto = new CategoryDTO(category.getId(), "Transporte", 300.0);
        when(categoryRepository.existsById(dto.getId())).thenReturn(true);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        when(modelMapper.map(dto, Category.class)).thenReturn(category);

        Category result = categoryService.editCategory(dto);

        assertNotNull(result);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deveLancarExcecaoAoEditarCategoriaInexistente() {
        CategoryDTO dto = new CategoryDTO(99L, "Teste", 100.0);
        when(categoryRepository.existsById(dto.getId())).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> categoryService.editCategory(dto));
        assertEquals("Categoria não encontrada", exception.getMessage());
    }

    // 4️⃣ Exclusão
    @Test
    void deveExcluirCategoriaComSucesso() throws Exception {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.deleteCategory(1L);

        assertNotNull(result);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void deveLancarExcecaoAoExcluirCategoriaInexistente() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> categoryService.deleteCategory(1L));
    }

    // 5️⃣ Atualizar limite
    @Test
    void deveAtualizarLimiteDeCategoria() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.updateLimit(1L, 1000.0);

        assertEquals(1000.0, result.getSpendingLimit());
        verify(categoryRepository).save(category);
    }

    @Test
    void deveLancarErroQuandoCategoriaNaoExisteAoAtualizarLimite() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> categoryService.updateLimit(99L, 200.0));

        assertEquals("Categoria não encontrada", exception.getMessage());
    }
}
