package com.productos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoResponseDto {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;

}
