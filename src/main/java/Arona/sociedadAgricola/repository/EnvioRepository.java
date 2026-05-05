package Arona.sociedadAgricola.repository;

import Arona.sociedadAgricola.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByEstado(Boolean estado);
    List<Envio> findByEstadoEnvio(String estadoEnvio);
    List<Envio> findByTipoCultivo(String tipoCultivo);

    @Query("SELECT e FROM Envio e WHERE e.fechaEnvio BETWEEN :fechaInicio AND :fechaFin AND e.estado = true")
    List<Envio> findByFechaEnvioBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                         @Param("fechaFin") LocalDate fechaFin);
}
