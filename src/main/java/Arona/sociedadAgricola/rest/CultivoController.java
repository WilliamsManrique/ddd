package Arona.sociedadAgricola.rest;

import Arona.sociedadAgricola.model.Cultivo;
import Arona.sociedadAgricola.service.CultivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/cultivos")
@Tag(name = "Cultivo-Controller", description = "HU1: Consulta del estado de los cultivos")
public class CultivoController {

    @Autowired
    private CultivoService cultivoService;

    @GetMapping
    @Operation(summary = "Listar cultivos", description = "Obtiene todos los cultivos registrados")
    public ResponseEntity<List<Cultivo>> listarTodos() {
        return ResponseEntity.ok(cultivoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<Cultivo> listarPorId(@PathVariable Long id) {
        return cultivoService.listarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar por estado", description = "Filtra cultivos activos o inactivos")
    public ResponseEntity<List<Cultivo>> listarPorEstado(@PathVariable Boolean estado) {
        return ResponseEntity.ok(cultivoService.listarPorEstado(estado));
    }

    @GetMapping("/tipo/{tipoCultivo}")
    @Operation(summary = "Filtrar por tipo de fruta", description = "MANDARINA, PALTA, ARANDANO, CAQUI")
    public ResponseEntity<List<Cultivo>> listarPorTipo(@PathVariable String tipoCultivo) {
        return ResponseEntity.ok(cultivoService.listarPorTipoCultivo(tipoCultivo));
    }

    @GetMapping("/estado-salud/{estadoSalud}")
    @Operation(summary = "Filtrar por estado de salud", description = "BUENO, EN_RIESGO, CON_PROBLEMAS")
    public ResponseEntity<List<Cultivo>> listarPorEstadoSalud(@PathVariable String estadoSalud) {
        return ResponseEntity.ok(cultivoService.listarPorEstadoSalud(estadoSalud));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar por nombre", description = "Búsqueda parcial por nombre (LIKE %nombre%) ignorando mayúsculas/minúsculas")
    public ResponseEntity<List<Cultivo>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(cultivoService.buscarPorNombre(nombre));
    }

    @PostMapping
    @Operation(summary = "Crear cultivo")
    public ResponseEntity<Cultivo> crear(@RequestBody Cultivo cultivo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cultivoService.crear(cultivo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar cultivo")
    public ResponseEntity<Cultivo> editar(@PathVariable Long id, @RequestBody Cultivo cultivo) {
        Cultivo editado = cultivoService.editar(id, cultivo);
        return (editado != null) ? ResponseEntity.ok(editado) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar (lógico)")
    public ResponseEntity<Cultivo> eliminar(@PathVariable Long id) {
        Cultivo eliminado = cultivoService.eliminar(id);
        return (eliminado != null) ? ResponseEntity.ok(eliminado) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/restaurar")
    @Operation(summary = "Restaurar cultivo")
    public ResponseEntity<Cultivo> restaurar(@PathVariable Long id) {
        Cultivo restaurado = cultivoService.restaurar(id);
        return (restaurado != null) ? ResponseEntity.ok(restaurado) : ResponseEntity.notFound().build();
    }
}
