package fr.epita.trackmoviesback.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * classe pour la config du swagger
 * swagger accessible sur: http://localhost:8080/swagger-ui.html
 *
 * pour etre pris en compte il a fallu ajouter:
 * dans application properties: spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER
 * et dire a spring: spring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerSpringFoxConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.apis(RequestHandlerSelectors.basePackage("fr.epita.trackmoviesback"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.ant("/trackmovies/v1/**"))
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext())) //
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("track movie swagger")
                .description("Projet fil rouge track movie backend")
                .version("v0.1")
                .license("BCEFIT")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

}
