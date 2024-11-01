package com.crishof.brandsv.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info()
                .title("Api StockSuite")
                .version("1.0.0")
                .contact(new Contact().name("Cristian")
                        .url("https://www.stocksuite.com")
                        .email("www.info@stocksuite.com"))
                .license(new License().url("https://www.stocksuite.com/licence").name("Licence"))
                .termsOfService("https://www.stocksuite/.com/termsofservice")
                .description("General commercial management API"));
    }
}