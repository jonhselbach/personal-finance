package com.metodosageis.personal_finance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_categoria", nullable = false, unique = true, length = 50)
    private String nome;

    @Column(name = "limite_gastos", nullable = false)
    private Double limiteGastos;
}
