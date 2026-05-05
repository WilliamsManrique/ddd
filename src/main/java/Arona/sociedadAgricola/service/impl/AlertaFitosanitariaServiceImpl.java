package Arona.sociedadAgricola.service.impl;

import Arona.sociedadAgricola.model.AlertaFitosanitaria;
import Arona.sociedadAgricola.repository.AlertaFitosanitariaRepository;
import Arona.sociedadAgricola.service.AlertaFitosanitariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaFitosanitariaServiceImpl implements AlertaFitosanitariaService {

    private static final ZoneId ZONA_LIMA = ZoneId.of("America/Lima");

    @Autowired
    private AlertaFitosanitariaRepository repository;

    @Override
    public List<AlertaFitosanitaria> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<AlertaFitosanitaria> listarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<AlertaFitosanitaria> listarPendientes() {
        return repository.findByEstadoAlerta("PENDIENTE");
    }

    @Override
    public List<AlertaFitosanitaria> listarPorCampo(Long idCampo) {
        return repository.findByIdCampo(idCampo);
    }

    @Override
    public AlertaFitosanitaria crear(AlertaFitosanitaria alerta) {
        alerta.setEstadoAlerta("PENDIENTE");
        alerta.setEstado(true);
        alerta.setCreatedAt(LocalDateTime.now(ZONA_LIMA));
        return repository.save(alerta);
    }

    @Override
    public AlertaFitosanitaria atender(Long id, String solucionAplicada) {
        return repository.findById(id).map(a -> {
            a.setEstadoAlerta("ATENDIDO");
            a.setSolucionAplicada(solucionAplicada);
            a.setFechaResolucion(LocalDate.now());
            a.setUpdatedAt(LocalDateTime.now(ZONA_LIMA));
            return repository.save(a);
        }).orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
    }
}
