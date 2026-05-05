package Arona.sociedadAgricola.rest;

import Arona.sociedadAgricola.model.AlertaFitosanitaria;
import Arona.sociedadAgricola.service.AlertaFitosanitariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alertas")
@Tag(name = "AlertaFitosanitaria-Controller", description = "HU7: Aviso de plagas o enfermedades")
public class AlertaFitosanitariaController {

    @Autowired
    private AlertaFitosanitariaService service;

    @GetMapping
    @Operation(summary = "Listar todas las alertas")
    public ResponseEntity<List<AlertaFitosanitaria>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<AlertaFitosanitaria> listarPorId(@PathVariable Long id) {
        return service.listarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pendientes")
    @Operation(summary = "Alertas pendientes", description = "Solo alertas que no han sido atendidas")
    public ResponseEntity<List<AlertaFitosanitaria>> listarPendientes() {
        return ResponseEntity.ok(service.listarPendientes());
    }

    @GetMapping("/campo/{idCampo}")
    @Operation(summary = "Alertas por campo")
    public ResponseEntity<List<AlertaFitosanitaria>> listarPorCampo(@PathVariable Long idCampo) {
        return ResponseEntity.ok(service.listarPorCampo(idCampo));
    }

    @PostMapping
    @Operation(summary = "Crear alerta fitosanitaria")
    public ResponseEntity<AlertaFitosanitaria> crear(@RequestBody AlertaFitosanitaria alerta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(alerta));
    }

    @PatchMapping("/{id}/atender")
    @Operation(summary = "Marcar como atendido", description = "Registra la solución aplicada y cambia el estado")
    public ResponseEntity<AlertaFitosanitaria> atender(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String solucion = body.get("solucionAplicada");
        return ResponseEntity.ok(service.atender(id, solucion));
    }
}
