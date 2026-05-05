package Arona.sociedadAgricola.service;

import Arona.sociedadAgricola.model.Envio;

import java.util.List;
import java.util.Optional;

public interface EnvioService {
    List<Envio> listarTodos();
    Optional<Envio> listarPorId(Long id);
    List<Envio> listarPorEstadoEnvio(String estadoEnvio);
    Envio crear(Envio envio);
    Envio actualizarEstado(Long id, String nuevoEstado);
    Envio eliminar(Long id);
}
