package Arona.sociedadAgricola.rest;

import Arona.sociedadAgricola.model.Envio;
import Arona.sociedadAgricola.service.EnvioService;
import Arona.sociedadAgricola.service.impl.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "Envio-Controller", description = "HU5: Registro de envíos al extranjero")
public class EnvioController {

    @Autowired
    private EnvioService service;

    @Autowired
    private PdfGeneratorService pdfService;

    @GetMapping
    @Operation(summary = "Listar todos los envíos")
    public ResponseEntity<List<Envio>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<Envio> listarPorId(@PathVariable Long id) {
        return service.listarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estadoEnvio}")
    @Operation(summary = "Filtrar por estado", description = "EN_PREPARACION, DESPACHADO, ENTREGADO")
    public ResponseEntity<List<Envio>> listarPorEstado(@PathVariable String estadoEnvio) {
        return ResponseEntity.ok(service.listarPorEstadoEnvio(estadoEnvio));
    }

    @PostMapping
    @Operation(summary = "Crear envío")
    public ResponseEntity<Envio> crear(@RequestBody Envio envio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(envio));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado del envío")
    public ResponseEntity<Envio> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estadoEnvio");
        return ResponseEntity.ok(service.actualizarEstado(id, nuevoEstado));
    }

    @PatchMapping("/{id}/eliminar")
    @Operation(summary = "Eliminar (lógico)")
    public ResponseEntity<Envio> eliminar(@PathVariable Long id) {
        Envio eliminado = service.eliminar(id);
        return (eliminado != null) ? ResponseEntity.ok(eliminado) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/documento")
    @Operation(summary = "Generar documento PDF", description = "Genera un PDF con el detalle del envío")
    public ResponseEntity<byte[]> generarDocumento(@PathVariable Long id) {
        return service.listarPorId(id).map(envio -> {
            byte[] pdf = pdfService.generarDocumentoEnvio(envio);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("envio_" + id + ".pdf")
                    .build());
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        }).orElse(ResponseEntity.notFound().build());
    }
}
