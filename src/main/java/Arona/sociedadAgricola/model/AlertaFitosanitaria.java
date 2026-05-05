package Arona.sociedadAgricola.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad AlertaFitosanitaria - Alertas de plagas o enfermedades en cultivos.
 * HU7: Aviso de plagas o enfermedades.
 * Tipos: PLAGA, ENFERMEDAD, OTRO
 * Estados: PENDIENTE, ATENDIDO
 */
@Entity
@Table(name = "alertas_fitosanitarias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Alerta de plaga o enfermedad detectada en un campo")
public class AlertaFitosanitaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long idAlerta;

    @Column(name = "id_campo", nullable = false)
    private Long idCampo;

    @Column(name = "id_cultivo")
    private Long idCultivo;

    @Column(name = "descripcion_problema", nullable = false, columnDefinition = "TEXT")
    private String descripcionProblema;

    @Column(name = "tipo_problema", nullable = false, length = 20)
    private String tipoProblema;

    @Column(name = "estado_alerta", length = 20)
    private String estadoAlerta = "PENDIENTE";

    @Column(name = "solucion_aplicada", columnDefinition = "TEXT")
    private String solucionAplicada;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_deteccion", nullable = false)
    private LocalDate fechaDeteccion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_resolucion")
    private LocalDate fechaResolucion;

    @Column(name = "id_usuario_reporta", nullable = false)
    private Integer idUsuarioReporta;

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
