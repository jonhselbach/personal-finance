package com.metodosageis.personal_finance.controller;

import com.metodosageis.personal_finance.model.Categoria;
import com.metodosageis.personal_finance.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria) {

        return ResponseEntity.ok(service.criarCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(service.listarCategorias());
    }

    @PutMapping("/{id}/limite")
    public ResponseEntity<Categoria> atualizarLimite(
            @PathVariable Long id,
            @RequestParam Double novoLimite
    ) {
        return ResponseEntity.ok(service.atualizarLimite(id, novoLimite));
    }
}
