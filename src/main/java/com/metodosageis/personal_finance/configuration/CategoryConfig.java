package com.metodosageis.personal_finance.configuration;

import com.metodosageis.personal_finance.model.Category;
import com.metodosageis.personal_finance.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoryConfig {

    @Bean
    CommandLineRunner loadDefaultCategories(CategoryRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Category(null, "Alimentação", 1000.0),
                        new Category(null, "Transporte", 800.0),
                        new Category(null, "Lazer", 600.0),
                        new Category(null, "Saúde", 1200.0)
                ));
            }
        };
    }
}