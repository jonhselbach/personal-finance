package com.metodosageis.personal_finance.dto;

import com.metodosageis.personal_finance.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


public record TransactionDTO (

        Long id,

        @NotNull(message = "Valor é obrigatório")
        Double amount,

        @NotNull(message = "Tipo é obrigatório")
        TransactionType type,

        @NotNull(message = "Categoria é obrigatória")
        Long idcategory,

        @NotNull(message = "Data é obrigatória")
        LocalDate date,

        String description
)
{ }
