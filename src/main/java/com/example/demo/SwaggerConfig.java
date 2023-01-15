package com.example.demo;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    //"application/vnd.company.app-v2+json"
    @Bean
    Docket swaggerApi10() {
        return new Docket(DocumentationType.SWAGGER_2)
        .groupName("api-1.0")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
        .apis(p -> {
            return p.produces().stream().map(MediaType::toString).collect(Collectors.toList()).contains("application/json;version=1");
        })
        .paths(PathSelectors.any())
        .build()
        .apiInfo(new ApiInfoBuilder().version("2.0").title("API").description("Documentation API v2.0").build());
    }

    @Bean
    Docket swaggerApi20() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("api-2.0")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
            .apis(p -> {
                return p.produces().stream().map(MediaType::toString).collect(Collectors.toList()).contains("application/json;version=2");
            })
            .build()
            .apiInfo(new ApiInfoBuilder().version("2.0").title("API").description("Documentation API v2.0").build());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        System.out.println("******************************Configuring swagger resource handler");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
