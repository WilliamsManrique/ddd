package Arona.sociedadAgricola.service;

import Arona.sociedadAgricola.model.Cultivo;
import java.util.List;
import java.util.Optional;

public interface CultivoService {
    List<Cultivo> listarTodos();
    Optional<Cultivo> listarPorId(Long id);
    List<Cultivo> listarPorEstado(Boolean estado);
    List<Cultivo> listarPorTipoCultivo(String tipoCultivo);
    List<Cultivo> listarPorEstadoSalud(String estadoSalud);
    List<Cultivo> buscarPorNombre(String nombre);
    Cultivo crear(Cultivo cultivo);
    Cultivo editar(Long id, Cultivo cultivo);
    Cultivo eliminar(Long id);
    Cultivo restaurar(Long id);
}
