package com.metodosageis.personal_finance.controller;

import com.metodosageis.personal_finance.dto.CategoriaDTO;
import com.metodosageis.personal_finance.model.Categoria;
import com.metodosageis.personal_finance.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO) throws Exception {

        if(!id.equals(categoriaDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso restrito");
        };

        return ResponseEntity.ok(service.editarCategoria(categoriaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> deletar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO) throws Exception {
        if(!id.equals(categoriaDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso restrito");
        };

        return ResponseEntity.ok(service.deletarCategoria(categoriaDTO));
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(service.listarCategorias());
    }

    @PatchMapping("/{id}/limite")
    public ResponseEntity<Categoria> atualizarLimite(
            @PathVariable Long id,
            @RequestParam Double novoLimite
    ) {
        return ResponseEntity.ok(service.atualizarLimite(id, novoLimite));
    }
}
