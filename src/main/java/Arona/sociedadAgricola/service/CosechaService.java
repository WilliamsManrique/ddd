package Arona.sociedadAgricola.service;

import Arona.sociedadAgricola.model.Cosecha;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CosechaService {
    List<Cosecha> listarTodos();
    Optional<Cosecha> listarPorId(Long id);
    List<Cosecha> listarPorEstado(Boolean estado);
    List<Map<String, Object>> obtenerResumen();
    Cosecha crear(Cosecha cosecha);
    Cosecha editar(Long id, Cosecha cosecha);
    Cosecha eliminar(Long id);
    Cosecha restaurar(Long id);
}
