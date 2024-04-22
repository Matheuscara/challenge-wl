package comspringboot.challengewl.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Challenge-wl",
                description = "Crud to challenge about Spring",
                contact = @Contact(
                        name = "matheus dias",
                        url = "https://github.com/Matheuscara",
                        email = "matheus.dias.dev@gmail.com"
                ),
                termsOfService = "T&C",
                version = "0.0.1",
                license = @License(
                        name = "Licence Matheus Dias"
                )
        ),
        servers = {
                @Server(
                        description = "Dev",
                        url = "http://localhost:1000"
                ),
                @Server(
                        description = "Test",
                        url = "http://localhost:1000"
                )
        },
        security = @SecurityRequirement(
                name = "JWT"
        )
)
@SecurityScheme(
        name = "JWT",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Secutiry"

)
public class OpenApiConfig {}