package com.metodosageis.personal_finance.dto;

import com.metodosageis.personal_finance.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionDTO {

    private Long id;

    @NotNull(message = "Valor é obrigatório")
    private Double amount;

    @NotNull(message = "Tipo é obrigatório")
    private TransactionType type;

    @NotNull(message = "Categoria é obrigatória")
    private Long categoryId;

    @NotNull(message = "Data é obrigatória")
    private LocalDate date;

    private String description;
}
