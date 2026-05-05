package Arona.sociedadAgricola.repository;

import Arona.sociedadAgricola.model.RevisionCalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevisionCalidadRepository extends JpaRepository<RevisionCalidad, Long> {
    List<RevisionCalidad> findByEstado(Boolean estado);
    List<RevisionCalidad> findByIdCosecha(Long idCosecha);
    List<RevisionCalidad> findByCumpleRequisitos(Boolean cumple);
}
