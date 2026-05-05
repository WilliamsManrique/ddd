package Arona.sociedadAgricola.repository;

import Arona.sociedadAgricola.model.Cosecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CosechaRepository extends JpaRepository<Cosecha, Long> {
    List<Cosecha> findByEstado(Boolean estado);
    List<Cosecha> findByTipoCultivo(String tipoCultivo);
    List<Cosecha> findByIdCampo(Long idCampo);

    @Query("SELECT c FROM Cosecha c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin AND c.estado = true")
    List<Cosecha> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                      @Param("fechaFin") LocalDate fechaFin);
}
