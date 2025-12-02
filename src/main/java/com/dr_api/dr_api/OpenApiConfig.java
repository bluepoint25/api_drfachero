package com.dr_api.dr_api;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "Bearer Authentication", // Nombre que aparecerá en el botón "Authorize"
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer" // Indica que el esquema es 'Bearer '
)
public class OpenApiConfig {

    // Bean para agrupar endpoints y mejorar la organización de Swagger UI
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Dr Fachero API")
                .pathsToMatch("/api/**")
                .build();
    }
    
    // Bean para definir la información principal de la API
    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
            .info(new Info().title("Dr. Fachero API")
            .version("1.0")
            .description("API para la gestión de citas, pacientes y recetas médicas. Requiere JWT para rutas protegidas."));
    }
}