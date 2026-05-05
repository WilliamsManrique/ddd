package Agropacayales.valleGrande.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cultivos")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "estado")
    private Boolean estado = true;

    // CAMPOS DE AUDITORÍA
    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @JsonIgnore
    @Column(name = "restored_at")
    private LocalDateTime restoredAt;
}