package com.inventario.controller;

import com.inventario.dto.InventarioRequestDto;
import com.inventario.dto.InventarioResponseDto;
import com.inventario.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDto> crear(@RequestBody InventarioRequestDto request) {
        return ResponseEntity.ok(service.saveInventario(request));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<InventarioResponseDto> obtenerPorProductoId(@PathVariable Long productoId) {
        return ResponseEntity.ok(service.obtenerPorProductoId(productoId));
    }

    @PostMapping("/descontar/{productoId}/{cantidad}")
    public ResponseEntity<Boolean> descontarStock(@PathVariable Long productoId, @PathVariable int cantidad) {
        return ResponseEntity.ok(service.descontarStock(productoId, cantidad));
    }

}
