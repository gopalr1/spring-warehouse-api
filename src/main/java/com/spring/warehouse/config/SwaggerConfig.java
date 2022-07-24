package com.spring.warehouse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gopal_re
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info().title("Warehouse Rest API").description("Service API for Warehouse application").version("v1").license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));


    }
}
