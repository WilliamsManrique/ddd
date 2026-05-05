package Agropacayales.valleGrande.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Agropacayales.valleGrande.model.Cultivo;
import Agropacayales.valleGrande.repository.CultivoRepository;
import Agropacayales.valleGrande.service.CultivoService; 

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CultivoServiceImpl implements CultivoService { 

    @Autowired
    private CultivoRepository repository;

    @Override
    public List<Cultivo> listarTodos() { 
        return repository.findAll(); 
    }

    @Override
    public Optional<Cultivo> listarPorId(Long id) { 
        return repository.findById(id); 
    }

    @Override
    public List<Cultivo> listarPorEstado(Boolean estado) {
        return repository.findByEstado(estado);
    }

    @Override
    public Cultivo crear(Cultivo cultivo) { 
        cultivo.setEstado(true);
        cultivo.setCreatedAt(LocalDateTime.now());
        return repository.save(cultivo);
    }

    @Override
    public Cultivo editar(Long id, Cultivo datos) { 
        return repository.findById(id).map(c -> {
            c.setNombre(datos.getNombre());
            c.setTipoCultivo(datos.getTipoCultivo());
            c.setFrecuenciaRiegoDias(datos.getFrecuenciaRiegoDias());
            c.setTemperaturaIdeal(datos.getTemperaturaIdeal());
            c.setFechaSiembra(datos.getFechaSiembra());
            c.setRequiereSombra(datos.getRequiereSombra());
            c.setObservaciones(datos.getObservaciones());
            c.setUpdatedAt(LocalDateTime.now());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Cultivo no encontrado"));
    }

    @Override
    public Cultivo eliminar(Long id) { 
        return repository.findById(id).map(c -> {
            c.setEstado(false);
            c.setDeletedAt(LocalDateTime.now());
            return repository.save(c);
        }).orElse(null);
    }

    @Override
    public Cultivo restaurar(Long id) {
        return repository.findById(id).map(c -> {
            c.setEstado(true);
            c.setRestoredAt(LocalDateTime.now());
            return repository.save(c);
        }).orElse(null);
    }
}