package Arona.sociedadAgricola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Arona.sociedadAgricola.model.Usuario;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByEstado(Boolean estado);
    List<Usuario> findByArea(String area);
    List<Usuario> findByRol(String rol);
    Optional<Usuario> findByUsername(String username);
}
