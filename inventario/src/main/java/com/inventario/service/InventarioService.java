package com.inventario.service;

import com.inventario.dto.InventarioRequestDto;
import com.inventario.dto.InventarioResponseDto;
import com.inventario.entity.Inventario;
import com.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository repository;

    private final HistorialInventarioService historialInventarioService;

    public InventarioService(HistorialInventarioService historialInventarioService) {
        this.historialInventarioService = historialInventarioService;
    }
    public InventarioResponseDto saveInventario (InventarioRequestDto request){
        Optional<Inventario> inventarioOptional = repository.findByProductoAndCantidad(request.getProductoId(), request.getCantidad());

        InventarioResponseDto response = new InventarioResponseDto();

        if(inventarioOptional.isEmpty()) {
            Inventario inventario = new Inventario();
            inventario.setProductoId(request.getProductoId());
            inventario.setCantidad(request.getCantidad());

            Inventario inventarioGuardado = repository.save(inventario);

            historialInventarioService.saveHistorial(inventario.getProductoId(), 0,
                    inventarioGuardado.getCantidad(), "Agregar nuevo producto");

            response.setId(inventarioGuardado.getId());
            response.setProductoId(inventarioGuardado.getProductoId());
            response.setCantidad(inventarioGuardado.getCantidad());
        }else {
            response.setId(inventarioOptional.get().getId());
            response.setProductoId(inventarioOptional.get().getProductoId());
            response.setCantidad(inventarioOptional.get().getCantidad());
        }
        return response;
    }

    public InventarioResponseDto obtenerPorProductoId(Long productoId) {
        Inventario inventario = repository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para producto ID: " + productoId));

        InventarioResponseDto response = new InventarioResponseDto();
        response.setId(inventario.getId());
        response.setProductoId(inventario.getProductoId());
        response.setCantidad(inventario.getCantidad());

        return response;
    }

    public boolean descontarStock(Long productoId, int cantidad) {
        Optional<Inventario> optional = repository.findByProductoId(productoId);

        if (optional.isEmpty()) return false;

        Inventario inventario = optional.get();
        if (inventario.getCantidad() < cantidad) return false;

        inventario.setCantidad(inventario.getCantidad() - cantidad);
        repository.save(inventario);

        historialInventarioService.saveHistorial(
                inventario.getProductoId(),
                optional.get().getCantidad(),
                inventario.getCantidad(),
                "Descuento por compra"
        );

        return true;
    }
}
