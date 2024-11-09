package com.crishof.apigateway.config;

import org.junit.jupiter.api.Test;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SwaggerConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void groupedOpenApiBeanExists() {
        // Verifica que el bean GroupedOpenApi está presente en el contexto de Spring
        GroupedOpenApi groupedOpenApi = applicationContext.getBean("api", GroupedOpenApi.class);
        assertThat(groupedOpenApi).isNotNull();

        // Verifica que el grupo está configurado como "brand-sv"
        assertThat(groupedOpenApi.getGroup()).isEqualTo("brand-sv");

        // Verifica que los paths contienen "/brand-sv/**"
        assertThat(groupedOpenApi.getPathsToMatch()).contains("/brand-sv/**");
    }
}