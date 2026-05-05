package Arona.sociedadAgricola.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad Envio - Registro de envíos de fruta al extranjero.
 * HU5: Registro de envíos al extranjero.
 * Estados: EN_PREPARACION, DESPACHADO, ENTREGADO
 */
@Entity
@Table(name = "envios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Registro de envío de fruta al extranjero")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_envio")
    private Long idEnvio;

    @Column(name = "pais_destino", nullable = false, length = 100)
    private String paisDestino;

    @Column(name = "tipo_cultivo", nullable = false, length = 80)
    private String tipoCultivo;

    @Column(name = "cantidad_kg", precision = 10, scale = 2)
    private BigDecimal cantidadKg;

    @Column(name = "estado_envio", length = 20)
    private String estadoEnvio = "EN_PREPARACION";

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_envio")
    private LocalDate fechaEnvio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "documento_detalle", columnDefinition = "TEXT")
    private String documentoDetalle;

    @Column(name = "estado")
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
