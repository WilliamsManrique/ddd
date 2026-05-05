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
 * Entidad RevisionCalidad - Revisión de calidad antes del despacho.
 * HU6: Revisión de calidad antes del despacho.
 * Los lotes que no pasan la revisión no pueden incluirse en envíos.
 */
@Entity
@Table(name = "revisiones_calidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Revisión de calidad de lote antes del despacho")
public class RevisionCalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_revision")
    private Long idRevision;

    @Column(name = "id_cosecha", nullable = false)
    private Long idCosecha;

    @Column(name = "cumple_requisitos", nullable = false)
    private Boolean cumpleRequisitos;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "id_usuario_supervisor", nullable = false)
    private Integer idUsuarioSupervisor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "notificado")
    private Boolean notificado = false;

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
