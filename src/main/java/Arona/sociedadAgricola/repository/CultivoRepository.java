package Arona.sociedadAgricola.repository;

import Arona.sociedadAgricola.model.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Long> {
    List<Cultivo> findByEstado(Boolean estado);
    List<Cultivo> findByTipoCultivo(String tipoCultivo);
    List<Cultivo> findByEstadoSalud(String estadoSalud);
    List<Cultivo> findByIdCampo(Long idCampo);
    List<Cultivo> findByNombreContainingIgnoreCase(String nombre);
}
