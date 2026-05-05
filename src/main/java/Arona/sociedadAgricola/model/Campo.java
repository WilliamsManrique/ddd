package Arona.sociedadAgricola.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad Campo - Terrenos de cultivo de Sociedad Agrícola Arona S.A.
 * 300+ hectáreas en el valle de Cañete, Perú.
 * Migración desde la antigua entidad Parcela.
 */
@Entity
@Table(name = "campos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Campo / terreno de cultivo de Arona S.A.")
public class Campo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campo")
    @Schema(description = "ID único del campo", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idCampo;

    @NotBlank(message = "El nombre del campo es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    @Schema(description = "Nombre único del campo", example = "Campo Norte A")
    private String nombre;

    @Column(name = "ubicacion", length = 200)
    @Schema(description = "Ubicación del campo", example = "Valle de Cañete - Sector Norte")
    private String ubicacion;

    @NotNull(message = "Las hectáreas son obligatorias")
    @DecimalMin(value = "0.01", message = "Las hectáreas deben ser mayores a 0")
    @Column(name = "hectareas", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Extensión en hectáreas (debe ser > 0)", example = "50.00")
    private BigDecimal hectareas;

    @Column(name = "tipo_cultivo", length = 80)
    @Schema(description = "Tipo de cultivo principal", example = "MANDARINA")
    private String tipoCultivo;

    @Column(name = "estado")
    @Schema(description = "Estado del campo (true=activo, false=inactivo)", example = "true")
    private Boolean estado = true;

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
