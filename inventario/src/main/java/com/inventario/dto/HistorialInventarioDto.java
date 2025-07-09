package com.inventario.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistorialInventarioDto {

    private Long id;
    private Long productoId;
    private Integer cantidadAnterior;
    private Integer cantidadNueva;
    private LocalDateTime fechaCambio;
    private String motivo;
}
