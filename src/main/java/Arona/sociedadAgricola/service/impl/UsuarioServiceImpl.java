package Arona.sociedadAgricola.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Arona.sociedadAgricola.model.Usuario;
import Arona.sociedadAgricola.repository.UsuarioRepository;
import Arona.sociedadAgricola.service.IUsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Usuario> listarPorEstado(Boolean estado) {
        return repository.findByEstado(estado);
    }

    @Override
    public List<Usuario> listarPorArea(String area) {
        return repository.findByArea(area);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEstado(true);
        return repository.save(usuario);
    }

    @Override
    public Usuario actualizar(Integer id, Usuario datos) {
        return repository.findById(id).map(u -> {
            u.setNombreCompleto(datos.getNombreCompleto());
            u.setUsername(datos.getUsername());
            u.setPassword(datos.getPassword());
            u.setCorreo(datos.getCorreo());
            u.setRol(datos.getRol());
            u.setArea(datos.getArea());
            return repository.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Usuario eliminarLogico(Integer id) {
        return repository.findById(id).map(u -> {
            u.setEstado(false);
            return repository.save(u);
        }).orElse(null);
    }

    @Override
    public Usuario restaurarLogico(Integer id) {
        return repository.findById(id).map(u -> {
            u.setEstado(true);
            return repository.save(u);
        }).orElse(null);
    }
}
