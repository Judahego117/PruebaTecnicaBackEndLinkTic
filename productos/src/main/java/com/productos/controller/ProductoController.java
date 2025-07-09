package com.productos.controller;

import com.productos.dto.ProductoRequestDto;
import com.productos.dto.ProductoResponseDto;
import com.productos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.productos.utils.JsonApiWrapper.wrap;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public ResponseEntity<?> saveProducto(@Valid @RequestBody ProductoRequestDto request){
        ProductoResponseDto response = service.saveProducto(request);
        return ResponseEntity.ok(wrap("producto", response.getId(), response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        ProductoResponseDto response = service.findById(id);
        return ResponseEntity.ok(wrap("producto", response.getId(), response));
    }

}
