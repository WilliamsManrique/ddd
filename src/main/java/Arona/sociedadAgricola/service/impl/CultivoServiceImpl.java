package Arona.sociedadAgricola.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Arona.sociedadAgricola.model.Cultivo;
import Arona.sociedadAgricola.repository.CultivoRepository;
import Arona.sociedadAgricola.service.CultivoService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class CultivoServiceImpl implements CultivoService {

    private static final ZoneId ZONA_LIMA = ZoneId.of("America/Lima");

    @Autowired
    private CultivoRepository repository;

    @Override
    public List<Cultivo> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Cultivo> listarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Cultivo> listarPorEstado(Boolean estado) {
        return repository.findByEstado(estado);
    }

    @Override
    public List<Cultivo> listarPorTipoCultivo(String tipoCultivo) {
        return repository.findByTipoCultivo(tipoCultivo);
    }

    @Override
    public List<Cultivo> listarPorEstadoSalud(String estadoSalud) {
        return repository.findByEstadoSalud(estadoSalud);
    }

    @Override
    public List<Cultivo> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Cultivo crear(Cultivo cultivo) {
        cultivo.setEstado(true);
        if (cultivo.getEstadoSalud() == null) {
            cultivo.setEstadoSalud("BUENO");
        }
        cultivo.setCreatedAt(LocalDateTime.now(ZONA_LIMA));
        return repository.save(cultivo);
    }

    @Override
    public Cultivo editar(Long id, Cultivo datos) {
        return repository.findById(id).map(c -> {
            c.setNombre(datos.getNombre());
            c.setTipoCultivo(datos.getTipoCultivo());
            c.setFrecuenciaRiegoDias(datos.getFrecuenciaRiegoDias());
            c.setTemperaturaIdeal(datos.getTemperaturaIdeal());
            c.setFechaSiembra(datos.getFechaSiembra());
            c.setRequiereSombra(datos.getRequiereSombra());
            c.setEstadoSalud(datos.getEstadoSalud());
            c.setObservaciones(datos.getObservaciones());
            c.setIdCampo(datos.getIdCampo());
            c.setUpdatedAt(LocalDateTime.now(ZONA_LIMA));
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Cultivo no encontrado"));
    }

    @Override
    public Cultivo eliminar(Long id) {
        return repository.findById(id).map(c -> {
            c.setEstado(false);
            c.setDeletedAt(LocalDateTime.now(ZONA_LIMA));
            return repository.save(c);
        }).orElse(null);
    }

    @Override
    public Cultivo restaurar(Long id) {
        return repository.findById(id).map(c -> {
            c.setEstado(true);
            c.setRestoredAt(LocalDateTime.now(ZONA_LIMA));
            return repository.save(c);
        }).orElse(null);
    }
}
