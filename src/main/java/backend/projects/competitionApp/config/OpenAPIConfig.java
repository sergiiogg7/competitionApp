package backend.projects.competitionApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "srg7",
                        email = "sergiogarciagasco14@gmail.com",
                        url = "https://www.google.com"
                ),
                description = "OpenApi documentation for Fullstack Trading Backend",
                title = "OpenApi specification - sg7",
                version =  "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "http://localhost:8081"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Put here your JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@SecurityScheme(
        name = "basicAuth",
        description = "Add here your email and password",
        scheme = "basic",
        type = SecuritySchemeType.HTTP
)
public class OpenAPIConfig {
}
