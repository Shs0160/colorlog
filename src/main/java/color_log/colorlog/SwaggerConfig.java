package color_log.colorlog;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Colorlog",
                description = "colorlog api명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("colorlog-public")
                .pathsToMatch("/**")  // Swagger에 표시될 경로를 지정합니다.
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Colorlog API")
                        .version("v1")
                        .description("colorlog api명세서")
                        .contact(new Contact()
                                .name("songheesu")
                                .url("http://localhost:8080")
                                .email("bsthss0160@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }


}