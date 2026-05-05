package Arona.sociedadAgricola.service;

import Arona.sociedadAgricola.model.AlertaFitosanitaria;

import java.util.List;
import java.util.Optional;

public interface AlertaFitosanitariaService {
    List<AlertaFitosanitaria> listarTodos();
    Optional<AlertaFitosanitaria> listarPorId(Long id);
    List<AlertaFitosanitaria> listarPendientes();
    List<AlertaFitosanitaria> listarPorCampo(Long idCampo);
    AlertaFitosanitaria crear(AlertaFitosanitaria alerta);
    AlertaFitosanitaria atender(Long id, String solucionAplicada);
}
