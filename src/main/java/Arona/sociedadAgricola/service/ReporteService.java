package Arona.sociedadAgricola.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Servicio de reportes de producción y exportación.
 * HU9: Reportes de producción y exportación.
 * Provee datos agregados para que el frontend genere gráficos.
 */
public interface ReporteService {
    Map<String, Object> obtenerReporteProduccion(LocalDate fechaInicio, LocalDate fechaFin);
    Map<String, Object> obtenerReporteExportacion(LocalDate fechaInicio, LocalDate fechaFin);
    Map<String, Object> obtenerResumenGeneral(LocalDate fechaInicio, LocalDate fechaFin);
}
