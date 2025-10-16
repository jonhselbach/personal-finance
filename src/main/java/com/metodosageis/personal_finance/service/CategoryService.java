package com.metodosageis.personal_finance.service;

import com.metodosageis.personal_finance.dto.CategoryDTO;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Category createCategory(Category categoria) {
        return repository.save(categoria);
    }

    public List<Category> listCategory() {
        return repository.findAll();
    }

    public Category editCategory(CategoryDTO dto) throws Exception {
        Boolean hasCategory = repository.existsById(dto.getId());

        if (!hasCategory) {
            throw new Exception("Categoria não encontrada");
        }

        return repository.save(dto.toModel(modelMapper));
    }


    public Category deleteCategory(Long id) throws Exception {
        Category categoria = repository.findById(id)
                .orElseThrow(() -> new Exception("Categoria não encontrada"));

        repository.delete(categoria);
        return categoria;
    }

    public Category updateLimit(Long id, Double novoLimite) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        category.setLimiteGastos(novoLimite);
        return repository.save(category);
    }
}