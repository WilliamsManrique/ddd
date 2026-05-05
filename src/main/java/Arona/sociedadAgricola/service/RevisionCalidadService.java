package Arona.sociedadAgricola.service;

import Arona.sociedadAgricola.model.RevisionCalidad;

import java.util.List;
import java.util.Optional;

public interface RevisionCalidadService {
    List<RevisionCalidad> listarTodos();
    Optional<RevisionCalidad> listarPorId(Long id);
    List<RevisionCalidad> listarPorCosecha(Long idCosecha);
    RevisionCalidad crear(RevisionCalidad revision);
}
