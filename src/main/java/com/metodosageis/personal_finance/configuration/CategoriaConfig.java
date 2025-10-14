package com.metodosageis.personal_finance.configuration;

import com.metodosageis.personal_finance.model.Categoria;
import com.metodosageis.personal_finance.repository.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoriaConfig {

    // Pode usar o flyway para realizar a inserção monitorada, abaixo é apenas um exemplo
    @Bean
    CommandLineRunner carregarCategoriasPadrao(CategoriaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Categoria(null, "Alimentação", 1000.0),
                        new Categoria(null, "Transporte", 800.0),
                        new Categoria(null, "Lazer", 600.0),
                        new Categoria(null, "Saúde", 1200.0)
                ));
            }
        };
    }
}