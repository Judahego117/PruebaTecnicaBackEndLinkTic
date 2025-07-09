package com.inventario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarioRequestDto {
    private Long productoId;
    private Integer cantidad;
}
