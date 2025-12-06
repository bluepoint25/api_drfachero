package com.dr_api.dr_api;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement; 
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Dr Fachero API")
                .pathsToMatch("/api/**")
                .build();
    }
    
    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
            // ESTA ES LA LÍNEA QUE FALTABA Y SOLUCIONA EL ERROR 401 EN SWAGGER:
            .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication")) 
            .info(new Info()
                .title("Dr. Fachero API")
                .version("1.0")
                .description("API para la gestión de citas, pacientes y recetas. Requiere JWT."));
    }
}