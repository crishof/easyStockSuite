package com.crishof.apigateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WebFluxTest
class CorsFilterTests {

    @Autowired
    private WebTestClient webTestClient;

    @Configuration
    static class TestConfig {
        @Bean
        public CorsWebFilter corsFilter() {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.addAllowedOrigin("*");
            corsConfig.addAllowedMethod("*");
            corsConfig.addAllowedHeader("*");

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfig);

            return new CorsWebFilter(source);
        }
    }

    @Test
    void corsFilterAllowsAllOriginsMethodsAndHeaders() {
        webTestClient
                .mutate()
                .baseUrl("http://localhost")
                .build()
                .options()
                .uri("/test")
                .header("Origin", "http://example.com")
                .header("Access-Control-Request-Method", "GET")
                .header("Access-Control-Request-Headers", "*") // Asegura que se envíe el encabezado
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Access-Control-Allow-Origin", "*")
                // Permite el método GET
                .expectHeader().value("Access-Control-Allow-Methods", value -> assertThat(value).contains("GET"))
                // Permite cualquier cabecera
                .expectHeader().value("Access-Control-Allow-Headers", value -> assertThat(value).contains("*"));
    }
}