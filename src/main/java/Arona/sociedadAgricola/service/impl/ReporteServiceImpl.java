package Arona.sociedadAgricola.service.impl;

import Arona.sociedadAgricola.model.Cosecha;
import Arona.sociedadAgricola.model.Envio;
import Arona.sociedadAgricola.repository.CosechaRepository;
import Arona.sociedadAgricola.repository.EnvioRepository;
import Arona.sociedadAgricola.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio de reportes de producción y exportación.
 * HU9: Provee datos agregados (JSON) para que el frontend genere gráficos.
 */
@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private CosechaRepository cosechaRepository;

    @Autowired
    private EnvioRepository envioRepository;

    @Override
    public Map<String, Object> obtenerReporteProduccion(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Cosecha> cosechas = cosechaRepository.findByFechaBetween(fechaInicio, fechaFin);

        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("periodo", Map.of("inicio", fechaInicio.toString(), "fin", fechaFin.toString()));
        reporte.put("totalCosechas", cosechas.size());
        reporte.put("totalKg", cosechas.stream()
                .map(Cosecha::getCantidadKg)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        // Desglose por tipo de cultivo
        Map<String, List<Cosecha>> porTipo = cosechas.stream()
                .collect(Collectors.groupingBy(Cosecha::getTipoCultivo));

        List<Map<String, Object>> desglose = new ArrayList<>();
        porTipo.forEach((tipo, lista) -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("tipoCultivo", tipo);
            item.put("cantidadCosechas", lista.size());
            item.put("totalKg", lista.stream()
                    .map(Cosecha::getCantidadKg)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            desglose.add(item);
        });
        reporte.put("desglosePorTipo", desglose);

        return reporte;
    }

    @Override
    public Map<String, Object> obtenerReporteExportacion(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Envio> envios = envioRepository.findByFechaEnvioBetween(fechaInicio, fechaFin);

        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("periodo", Map.of("inicio", fechaInicio.toString(), "fin", fechaFin.toString()));
        reporte.put("totalEnvios", envios.size());
        reporte.put("totalKgExportados", envios.stream()
                .map(Envio::getCantidadKg)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        // Desglose por país destino
        Map<String, List<Envio>> porPais = envios.stream()
                .collect(Collectors.groupingBy(Envio::getPaisDestino));

        List<Map<String, Object>> desglosePais = new ArrayList<>();
        porPais.forEach((pais, lista) -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("pais", pais);
            item.put("cantidadEnvios", lista.size());
            item.put("totalKg", lista.stream()
                    .map(Envio::getCantidadKg)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            desglosePais.add(item);
        });
        reporte.put("desglosePorPais", desglosePais);

        // Desglose por tipo de cultivo
        Map<String, List<Envio>> porTipo = envios.stream()
                .collect(Collectors.groupingBy(Envio::getTipoCultivo));

        List<Map<String, Object>> desgloseTipo = new ArrayList<>();
        porTipo.forEach((tipo, lista) -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("tipoCultivo", tipo);
            item.put("cantidadEnvios", lista.size());
            item.put("totalKg", lista.stream()
                    .map(Envio::getCantidadKg)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            desgloseTipo.add(item);
        });
        reporte.put("desglosePorTipo", desgloseTipo);

        return reporte;
    }

    @Override
    public Map<String, Object> obtenerResumenGeneral(LocalDate fechaInicio, LocalDate fechaFin) {
        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("produccion", obtenerReporteProduccion(fechaInicio, fechaFin));
        resumen.put("exportacion", obtenerReporteExportacion(fechaInicio, fechaFin));
        return resumen;
    }
}
