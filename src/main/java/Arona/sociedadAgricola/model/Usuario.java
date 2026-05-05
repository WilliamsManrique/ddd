package Arona.sociedadAgricola.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad Usuario - Personal de Sociedad Agrícola Arona S.A.
 * Roles: ENCARGADO, ADMINISTRADOR
 * Áreas: CAMPO, PLANTA, ALMACEN, ADMINISTRACION, CALIDAD
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Usuario del sistema Arona S.A.")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "rol", length = 30)
    @Schema(description = "Rol del usuario", example = "ENCARGADO", allowableValues = {"ENCARGADO", "ADMINISTRADOR"})
    private String rol;

    @Column(name = "area", length = 20)
    @Schema(description = "Área de trabajo", example = "CAMPO", allowableValues = {"CAMPO", "PLANTA", "ALMACEN", "ADMINISTRACION", "CALIDAD"})
    private String area;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "fecha_registro")
    @Schema(description = "Fecha de registro del usuario", example = "04/05/2026 10:30:00")
    private LocalDateTime fechaRegistro;

    @Column(name = "estado")
    private Boolean estado;

    // ===================== CAMPOS DE AUDITORÍA =====================

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "created_at")
    @Schema(description = "Fecha y hora de creación del registro", example = "04/05/2026 10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "updated_at")
    @Schema(description = "Fecha y hora de la última modificación", example = "04/05/2026 14:15:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "deleted_at")
    @Schema(description = "Fecha y hora de eliminación lógica", example = "05/05/2026 09:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime deletedAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
    @Column(name = "restored_at")
    @Schema(description = "Fecha y hora de restauración del registro", example = "06/05/2026 08:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime restoredAt;
}
