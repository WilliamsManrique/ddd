package Arona.sociedadAgricola.service.impl;

import Arona.sociedadAgricola.model.RevisionCalidad;
import Arona.sociedadAgricola.repository.RevisionCalidadRepository;
import Arona.sociedadAgricola.service.RevisionCalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RevisionCalidadServiceImpl implements RevisionCalidadService {

    @Autowired
    private RevisionCalidadRepository repository;

    @Override
    public List<RevisionCalidad> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<RevisionCalidad> listarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<RevisionCalidad> listarPorCosecha(Long idCosecha) {
        return repository.findByIdCosecha(idCosecha);
    }

    @Override
    public RevisionCalidad crear(RevisionCalidad revision) {
        revision.setEstado(true);
        revision.setCreatedAt(LocalDateTime.now());
        // HU6: Si no cumple requisitos, se marca como notificado (alerta automática)
        if (!Boolean.TRUE.equals(revision.getCumpleRequisitos())) {
            revision.setNotificado(true);
        }
        return repository.save(revision);
    }
}
