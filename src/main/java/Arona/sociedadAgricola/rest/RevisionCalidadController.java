package Arona.sociedadAgricola.rest;

import Arona.sociedadAgricola.model.RevisionCalidad;
import Arona.sociedadAgricola.service.RevisionCalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/revisiones-calidad")
@Tag(name = "RevisionCalidad-Controller", description = "HU6: Revisión de calidad antes del despacho")
public class RevisionCalidadController {

    @Autowired
    private RevisionCalidadService service;

    @GetMapping
    @Operation(summary = "Listar todas las revisiones")
    public ResponseEntity<List<RevisionCalidad>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<RevisionCalidad> listarPorId(@PathVariable Long id) {
        return service.listarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cosecha/{idCosecha}")
    @Operation(summary = "Revisiones por cosecha")
    public ResponseEntity<List<RevisionCalidad>> listarPorCosecha(@PathVariable Long idCosecha) {
        return ResponseEntity.ok(service.listarPorCosecha(idCosecha));
    }

    @PostMapping
    @Operation(summary = "Registrar revisión", description = "Si no cumple, se notifica automáticamente al encargado")
    public ResponseEntity<RevisionCalidad> crear(@RequestBody RevisionCalidad revision) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(revision));
    }
}
