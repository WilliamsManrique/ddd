package Arona.sociedadAgricola.service.impl;

import Arona.sociedadAgricola.model.Cosecha;
import Arona.sociedadAgricola.model.Cultivo;
import Arona.sociedadAgricola.repository.CosechaRepository;
import Arona.sociedadAgricola.repository.CultivoRepository;
import Arona.sociedadAgricola.service.CosechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CosechaServiceImpl implements CosechaService {

    private static final ZoneId ZONA_LIMA = ZoneId.of("America/Lima");

    @Autowired
    private CosechaRepository repository;

    @Autowired
    private CultivoRepository cultivoRepository;

    @Override
    public List<Cosecha> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Cosecha> listarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Cosecha> listarPorEstado(Boolean estado) {
        return repository.findByEstado(estado);
    }

    @Override
    public List<Map<String, Object>> obtenerResumen() {
        // Resumen agrupado por tipo de cultivo
        List<Cosecha> cosechas = repository.findByEstado(true);
        Map<String, List<Cosecha>> porTipo = cosechas.stream()
                .collect(Collectors.groupingBy(Cosecha::getTipoCultivo));

        List<Map<String, Object>> resumen = new ArrayList<>();
        porTipo.forEach((tipo, lista) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("tipoCultivo", tipo);
            item.put("totalCosechas", lista.size());
            item.put("totalKg", lista.stream()
                    .map(Cosecha::getCantidadKg)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            item.put("totalUnidades", lista.stream()
                    .map(Cosecha::getCantidadUnidades)
                    .filter(Objects::nonNull)
                    .reduce(0, Integer::sum));
            resumen.add(item);
        });
        return resumen;
    }

    @Override
    public Cosecha crear(Cosecha cosecha) {
        // HU3: Validar que el cultivo esté en estado listo para cosechar
        Optional<Cultivo> cultivoOpt = cultivoRepository.findById(cosecha.getIdCultivo());
        if (cultivoOpt.isPresent()) {
            Cultivo cultivo = cultivoOpt.get();
            if ("CON_PROBLEMAS".equals(cultivo.getEstadoSalud())) {
                throw new RuntimeException("ADVERTENCIA: El cultivo '" + cultivo.getNombre()
                        + "' tiene problemas. Verifique antes de cosechar.");
            }
        }

        cosecha.setEstado(true);
        cosecha.setCreatedAt(LocalDateTime.now(ZONA_LIMA));
        return repository.save(cosecha);
    }

    @Override
    public Cosecha editar(Long id, Cosecha datos) {
        return repository.findById(id).map(c -> {
            c.setFecha(datos.getFecha());
            c.setIdCampo(datos.getIdCampo());
            c.setIdCultivo(datos.getIdCultivo());
            c.setCantidadKg(datos.getCantidadKg());
            c.setCantidadUnidades(datos.getCantidadUnidades());
            c.setTipoCultivo(datos.getTipoCultivo());
            c.setObservaciones(datos.getObservaciones());
            c.setUpdatedAt(LocalDateTime.now(ZONA_LIMA));
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Cosecha no encontrada"));
    }

    @Override
    public Cosecha eliminar(Long id) {
        return repository.findById(id).map(c -> {
            c.setEstado(false);
            c.setDeletedAt(LocalDateTime.now(ZONA_LIMA));
            return repository.save(c);
        }).orElse(null);
    }

    @Override
    public Cosecha restaurar(Long id) {
        return repository.findById(id).map(c -> {
            c.setEstado(true);
            c.setRestoredAt(LocalDateTime.now(ZONA_LIMA));
            return repository.save(c);
        }).orElse(null);
    }
}
