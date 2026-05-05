package Arona.sociedadAgricola.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad preparatoria.
 * Roles definidos: ENCARGADO y ADMINISTRADOR.
 * Actualmente permite todo el acceso (permitAll) para desarrollo.
 * Listo para activar autenticación JWT/Basic en producción.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        // Swagger UI - acceso libre
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api-docs/**"
                        ).permitAll()
                        // API endpoints - acceso libre (preparatorio)
                        // TODO: Activar restricciones por rol:
                        // .requestMatchers("/api/admin/**").hasRole("ADMINISTRADOR")
                        // .requestMatchers("/api/**").hasAnyRole("ENCARGADO", "ADMINISTRADOR")
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
