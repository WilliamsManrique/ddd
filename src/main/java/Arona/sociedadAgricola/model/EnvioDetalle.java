package Arona.sociedadAgricola.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidad EnvioDetalle - Tabla intermedia entre envíos y clasificaciones.
 * Permite que un envío incluya múltiples clasificaciones de distintas cosechas.
 */
@Entity
@Table(name = "envios_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_envio_detalle")
    private Long idEnvioDetalle;

    @Column(name = "id_envio", nullable = false)
    private Long idEnvio;

    @Column(name = "id_clasificacion", nullable = false)
    private Long idClasificacion;

    @Column(name = "cantidad_kg", precision = 10, scale = 2)
    private BigDecimal cantidadKg;
}
