package com.productos.service;

import com.productos.dto.ProductoRequestDto;
import com.productos.dto.ProductoResponseDto;
import com.productos.entity.Producto;
import com.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public ProductoResponseDto saveProducto(ProductoRequestDto request){
        Producto producto =  new Producto();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setDescripcion(request.getDescripcion());

        Producto productoGuardado = repository.save(producto);

        ProductoResponseDto response = new ProductoResponseDto();

        response.setId(productoGuardado.getId());
        response.setNombre(productoGuardado.getNombre());
        response.setPrecio(productoGuardado.getPrecio());
        response.setDescripcion(productoGuardado.getDescripcion());

        return response;
    }

    public ProductoResponseDto findById(Long id){
        Producto producto = repository.findById(id).orElseThrow(()-> new RuntimeException("Producto no encontrado"));

        ProductoResponseDto response = new ProductoResponseDto();

        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setPrecio(producto.getPrecio());
        response.setDescripcion(producto.getDescripcion());

        return response;
    }

}
