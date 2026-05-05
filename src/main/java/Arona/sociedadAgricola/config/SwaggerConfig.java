package Arona.sociedadAgricola.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para el sistema Arona.
 * Los campos de auditoría (createdAt, updatedAt, deletedAt, restoredAt)
 * son VISIBLES en los esquemas gracias a las anotaciones @Schema en cada entidad.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI aronaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema Agroexportador Arona S.A.")
                        .description("API REST del sistema de gestión agroexportadora de Sociedad Agrícola Arona S.A. "
                                + "Módulos: Inicio, Mis Datos, Producción, Mis Campos, Enfermedades, Clientes Export, Panel Admin. "
                                + "Todos los esquemas incluyen campos de auditoría (createdAt, updatedAt, deletedAt, restoredAt) "
                                + "con formato dd/MM/yyyy HH:mm:ss en zona horaria America/Lima.")
                        .version("2.0.0")
                        .contact(new Contact()
                                .name("Sociedad Agrícola Arona S.A.")
                                .email("admin@arona.com.pe"))
                        .license(new License()
                                .name("Privado")
                                .url("https://arona.com.pe")));
    }
}
