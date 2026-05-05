package Arona.sociedadAgricola.service.impl;

import Arona.sociedadAgricola.model.Envio;
import Arona.sociedadAgricola.repository.EnvioRepository;
import Arona.sociedadAgricola.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioServiceImpl implements EnvioService {

    @Autowired
    private EnvioRepository repository;

    @Override
    public List<Envio> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Envio> listarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Envio> listarPorEstadoEnvio(String estadoEnvio) {
        return repository.findByEstadoEnvio(estadoEnvio);
    }

    @Override
    public Envio crear(Envio envio) {
        envio.setEstadoEnvio("EN_PREPARACION");
        envio.setEstado(true);
        envio.setCreatedAt(LocalDateTime.now());
        return repository.save(envio);
    }

    @Override
    public Envio actualizarEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(e -> {
            e.setEstadoEnvio(nuevoEstado);
            e.setUpdatedAt(LocalDateTime.now());
            return repository.save(e);
        }).orElseThrow(() -> new RuntimeException("Envío no encontrado"));
    }

    @Override
    public Envio eliminar(Long id) {
        return repository.findById(id).map(e -> {
            e.setEstado(false);
            e.setDeletedAt(LocalDateTime.now());
            return repository.save(e);
        }).orElse(null);
    }
}
