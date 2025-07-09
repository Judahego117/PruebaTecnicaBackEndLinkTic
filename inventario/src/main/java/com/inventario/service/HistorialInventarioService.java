package com.inventario.service;

import com.inventario.entity.HistorialInventario;
import com.inventario.repository.HistorialInventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistorialInventarioService {

    @Autowired
    private HistorialInventarioRepository historialRepository;

    public void saveHistorial(Long productoId, Integer cantidadAnterior, Integer cantidadNueva, String motivo) {
        HistorialInventario historial = new HistorialInventario();
        historial.setProductoId(productoId);
        historial.setCantidadAnterior(cantidadAnterior);
        historial.setCantidadNueva(cantidadNueva);
        historial.setFechaCambio(LocalDateTime.now());
        historial.setMotivo(motivo);

        historialRepository.save(historial);
    }

}
