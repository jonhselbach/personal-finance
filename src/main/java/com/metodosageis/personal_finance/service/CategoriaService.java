package com.metodosageis.personal_finance.service;

import com.metodosageis.personal_finance.dto.CategoriaDTO;
import com.metodosageis.personal_finance.model.Categoria;
import com.metodosageis.personal_finance.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Categoria criarCategoria(Categoria categoria) {
        return repository.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return repository.findAll();
    }

    public Categoria editarCategoria(CategoriaDTO dto) throws Exception {
        Boolean temCategoria = repository.existsById(dto.getId());

        if (!temCategoria) {
            throw new Exception("Categoria não encontrada");
        }

        return repository.save(dto.toModel(modelMapper));
    }


    public Categoria deletarCategoria(CategoriaDTO dto) throws Exception {
        Categoria categoria = repository.findById(dto.getId()).orElseThrow(() -> new Exception("Categoria não encontrada"));
        repository.delete(categoria);
        return categoria;
    }

    public Categoria atualizarLimite(Long id, Double novoLimite) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        categoria.setLimiteGastos(novoLimite);
        return repository.save(categoria);
    }
}