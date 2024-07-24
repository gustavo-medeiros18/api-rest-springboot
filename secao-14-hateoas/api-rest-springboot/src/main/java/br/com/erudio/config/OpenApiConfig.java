package br.com.erudio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  /*
    A bean is an object that is instantiated, assembled, and otherwise
    managed by a Spring IoC container.

    The IOC container search for information across the application about
    how the beans are created, configured, and managed.

    Spring beans can easily interact with each other, and the container
    can manage the entire lifecycle of the beans.

    There are several forms of creating beans in Spring, but the main one
    is using annotations like @Bean, @Component, @Service, @Repository, etc.

    In this example, we're creating a bean of type OpenAPI, which is a class
    that represents the OpenAPI specification. We're setting the title, version,
    description, and license of the API.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("REST API with Spring Boot")
            .version("v1")
            .description("REST API with Spring Boot - Udemy Course")
            .license(new License()
                .name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
        );
  }
}
