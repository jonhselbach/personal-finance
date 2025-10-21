package com.metodosageis.personal_finance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_category", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "limit_spent", nullable = false)
    private Double spendingLimit;
}
