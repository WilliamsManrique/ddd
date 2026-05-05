package Arona.sociedadAgricola.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cultivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cultivo registrado en un campo de Arona S.A.")
public class Cultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cultivo")
    private Long idCultivo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo_cultivo", nullable = false, length = 80)
    private String tipoCultivo;

    @Column(name = "frecuencia_riego_dias", nullable = false)
    private Integer frecuenciaRiegoDias;

    @Column(name = "temperatura_ideal", nullable = false)
    private Double temperaturaIdeal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_siembra")
    private LocalDate fechaSiembra;

    @Column(name = "requiere_sombra")
    private Boolean requiereSombra = false;

    @Column(name = "estado_salud", length = 20)
    private String estadoSalud = "BUENO"; // BUENO, EN_RIESGO, CON_PROBLEMAS

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "id_campo")
    private Long idCampo;

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
