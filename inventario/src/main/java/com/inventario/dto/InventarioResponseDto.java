package com.inventario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarioResponseDto {
    private Long id;
    private Long productoId;
    private Integer cantidad;
}
