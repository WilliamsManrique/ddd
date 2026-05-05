package Arona.sociedadAgricola.repository;

import Arona.sociedadAgricola.model.EnvioDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvioDetalleRepository extends JpaRepository<EnvioDetalle, Long> {
    List<EnvioDetalle> findByIdEnvio(Long idEnvio);
    List<EnvioDetalle> findByIdClasificacion(Long idClasificacion);
}
