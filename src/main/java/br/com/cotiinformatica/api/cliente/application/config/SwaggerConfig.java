package br.com.cotiinformatica.api.cliente.application.config;

import java.util.Arrays;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
    
    @Value("${urlExterna}")
    String urlExterna;
    
    @Bean
    public GroupedOpenApi actuatorGroupedOpenApi() {
        OpenApiCustomizer customizer = openApi -> {
            openApi.info(actuatorInfo());
            openApi.servers(Arrays.asList(new Server().url(urlExterna)));
        };
        return GroupedOpenApi.builder().group("Actuator").pathsToMatch("/actuator/**")
                .addOpenApiCustomizer(customizer).build();
    }
    
    @Bean
    public GroupedOpenApi clienteGroupedOpenApi() {
        OpenApiCustomizer customizer = openApi -> {
            openApi.info(commonsInfo());
            openApi.servers(Arrays.asList(new Server().url(urlExterna)));
        };
        return GroupedOpenApi.builder().group("Cliente").pathsToMatch("/**")
                .addOpenApiCustomizer(customizer)
                .packagesToScan("br.com.cotiinformatica.api.cliente.application.controller").build();
    }
    
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().components(new Components()).info(new Info().title("API para controle de cliente")
                .description("Projeto final Java Arquiteto - COTI Informática").version("v1"));
    }
    
    private Info commonsInfo() {
        return new Info().title("API Cliente").description("Documentação API de Cliente.")
                .license(new License().name("Apache License Version 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"))
                .contact(new Contact().name("DBS").url("https://www.dbs.com.br/").email("diegobizerra@gmail.com"));
    }
    
    private Info actuatorInfo() {
        return new Info().title("Actuator API").description("Actuator API Documentation.")
                .license(new License().name("Apache License Version 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"))
                .contact(new Contact().name("DBS").url("https://www.dbs.com.br/").email("diegobizerra@gmail.com"));
    }
}
