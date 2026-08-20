package com.targetcart.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Minimal OpenAPI documentation setup.
 *
 * <p>Springdoc auto-discovers the REST controllers and DTO records; this bean
 * only supplies the top-level API metadata. No credentials or datasource
 * details are ever exposed through the documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI targetCartOpenApi() {
        return new OpenAPI().info(new Info()
                .title("TargetCart-AI REST API")
                .description("""
                        Read APIs for the TargetCart-AI abandoned-cart recovery engine.

                        Exposes users, products, carts (including abandoned carts backed by
                        the persisted carts.status column), recovery campaigns and their
                        execution logs. Swagger UI is available at /swagger-ui.html.
                        """)
                .version("v0.1.0")
                .contact(new Contact().name("TargetCart-AI"))
                .license(new License().name("Proprietary")));
    }
}