package com.sook.backend.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sook.backend.common.annotations.NoApiAuth;
import com.sook.backend.security.auth.dto.AuthDto;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String API_TITLE = "So-ok API";
    private static final String API_DESCRIPTION = "So-ok API Document";
    private static final String API_VERSION = "Version 1";
    private static final String SWAGGER_PATH = "/swagger-ui/index.html";
    private static final String API_KEY_NAME = "JWT";
    private static final String SCOPE_GLOBAL = "global";
    private static final String SCOPE_GLOBAL_DESCRIPTION = "to access resources";

    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                .docExpansion(DocExpansion.LIST)
                .build();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(AuthDto.class)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(securityScheme()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docs", SWAGGER_PATH);
        registry.addRedirectViewController("/swagger-ui", SWAGGER_PATH);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .build();
    }

    private SecurityScheme securityScheme() {
        return HttpAuthenticationScheme
                .JWT_BEARER_BUILDER
                .name(API_KEY_NAME)
                .description("`eyJhbGciOiJIUzUxMiJ9.xxxx`")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(securityReferences())
                .operationSelector(operationContext -> operationContext.findAnnotation(NoApiAuth.class).isEmpty())
                .build();
    }

    private List<SecurityReference> securityReferences() {
        return List.of(new SecurityReference(API_KEY_NAME, authorizationScopes()));
    }

    private AuthorizationScope[] authorizationScopes() {
        AuthorizationScope globalScope = new AuthorizationScopeBuilder()
                .scope(SCOPE_GLOBAL)
                .description(SCOPE_GLOBAL_DESCRIPTION)
                .build();
        return new AuthorizationScope[] {globalScope};
    }
}