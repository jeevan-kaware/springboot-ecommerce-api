package com.jeevan.ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {

    private static final String SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                .servers(List.of(
                        new Server()
                                .url("springboot-ecommerce-api-production-0832.up.railway.app")
                ))
                .info(new Info()
                        .title("springboot ecommerce api")
                        .version("1.0")
                        .description("springboot ecommerce REST API"))

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(SCHEME_NAME)
                )

                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );

    }
}