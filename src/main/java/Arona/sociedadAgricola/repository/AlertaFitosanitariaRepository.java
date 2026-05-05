package Arona.sociedadAgricola.repository;

import Arona.sociedadAgricola.model.AlertaFitosanitaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaFitosanitariaRepository extends JpaRepository<AlertaFitosanitaria, Long> {
    List<AlertaFitosanitaria> findByEstado(Boolean estado);
    List<AlertaFitosanitaria> findByEstadoAlerta(String estadoAlerta);
    List<AlertaFitosanitaria> findByIdCampo(Long idCampo);
    List<AlertaFitosanitaria> findByIdCultivo(Long idCultivo);
}
