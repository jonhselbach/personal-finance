package com.metodosageis.personal_finance.service;

import com.metodosageis.personal_finance.model.Categoria;
import com.metodosageis.personal_finance.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria criarCategoria(Categoria categoria) {
        return repository.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return repository.findAll();
    }

    public Categoria atualizarLimite(Long id, Double novoLimite) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        categoria.setLimiteGastos(novoLimite);
        return repository.save(categoria);
    }
}