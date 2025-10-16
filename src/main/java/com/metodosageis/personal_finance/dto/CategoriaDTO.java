package com.metodosageis.personal_finance.dto;
import com.metodosageis.personal_finance.model.Categoria;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    @NotNull(message = "Id obrigatório")
    private Long id;
    private String nome;
    private Double limiteGastos;

    public Categoria toModel(ModelMapper mapper) {
        return mapper.map(this, Categoria.class);
    }
}
