package com.metodosageis.personal_finance.controller;

import com.metodosageis.personal_finance.dto.CategoryDTO;
import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> edit(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) throws Exception {

        if(!id.equals(categoryDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso restrito");
        };

        return ResponseEntity.ok(service.editCategory(categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Category>> list() {
        return ResponseEntity.ok(service.listCategory());
    }

    @PatchMapping("/{id}/limite")
    public ResponseEntity<Category> updateLimit(
            @PathVariable Long id,
            @RequestBody Map<String, Double> body
    ) {
        if (!body.containsKey("spendingLimit")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo 'spendingLimit' é obrigatório");
        }

        Double newLimit = body.get("spendingLimit");
        return ResponseEntity.ok(service.updateLimit(id, newLimit));
    }

}
