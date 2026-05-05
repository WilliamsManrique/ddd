package Arona.sociedadAgricola.rest;

import Arona.sociedadAgricola.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reporte-Controller", description = "HU9: Reportes de producción y exportación")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @GetMapping("/produccion")
    @Operation(summary = "Reporte de producción", description = "Datos de cosecha agrupados por tipo de cultivo")
    public ResponseEntity<Map<String, Object>> reporteProduccion(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        return ResponseEntity.ok(service.obtenerReporteProduccion(fechaInicio, fechaFin));
    }

    @GetMapping("/exportacion")
    @Operation(summary = "Reporte de exportación", description = "Datos de envíos agrupados por país y tipo")
    public ResponseEntity<Map<String, Object>> reporteExportacion(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        return ResponseEntity.ok(service.obtenerReporteExportacion(fechaInicio, fechaFin));
    }

    @GetMapping("/resumen-general")
    @Operation(summary = "Resumen general", description = "Producción + exportación en un solo reporte")
    public ResponseEntity<Map<String, Object>> resumenGeneral(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        return ResponseEntity.ok(service.obtenerResumenGeneral(fechaInicio, fechaFin));
    }
}
