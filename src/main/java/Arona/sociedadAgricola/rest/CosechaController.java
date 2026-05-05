package Arona.sociedadAgricola.rest;

import Arona.sociedadAgricola.model.Cosecha;
import Arona.sociedadAgricola.service.CosechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cosechas")
@Tag(name = "Cosecha-Controller", description = "HU3: Registro de la cosecha")
public class CosechaController {

    @Autowired
    private CosechaService service;

    @GetMapping
    @Operation(summary = "Listar todas las cosechas")
    public ResponseEntity<List<Cosecha>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<Cosecha> listarPorId(@PathVariable Long id) {
        return service.listarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/resumen")
    @Operation(summary = "Resumen de cosechas", description = "Total cosechado agrupado por tipo de cultivo")
    public ResponseEntity<List<Map<String, Object>>> obtenerResumen() {
        return ResponseEntity.ok(service.obtenerResumen());
    }

    @PostMapping
    @Operation(summary = "Registrar cosecha", description = "Valida que el cultivo esté listo para cosechar")
    public ResponseEntity<Cosecha> crear(@RequestBody Cosecha cosecha) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(cosecha));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar cosecha")
    public ResponseEntity<Cosecha> editar(@PathVariable Long id, @RequestBody Cosecha cosecha) {
        return ResponseEntity.ok(service.editar(id, cosecha));
    }

    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar (lógico)")
    public ResponseEntity<Cosecha> eliminar(@PathVariable Long id) {
        Cosecha eliminada = service.eliminar(id);
        return (eliminada != null) ? ResponseEntity.ok(eliminada) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/restaurar")
    @Operation(summary = "Restaurar")
    public ResponseEntity<Cosecha> restaurar(@PathVariable Long id) {
        Cosecha restaurada = service.restaurar(id);
        return (restaurada != null) ? ResponseEntity.ok(restaurada) : ResponseEntity.notFound().build();
    }
}
