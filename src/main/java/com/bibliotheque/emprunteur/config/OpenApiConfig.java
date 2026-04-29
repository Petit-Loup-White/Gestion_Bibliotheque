package com.bibliotheque.emprunteur.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI serviceEmprunteurOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Service Emprunteur API")
                        .description("Documentation Swagger du microservice de gestion des emprunteurs")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Bibliotheque")
                                .email("support@bibliotheque.local"))
                        .license(new License().name("Usage interne")));
    }
}
