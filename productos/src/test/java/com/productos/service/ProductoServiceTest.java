package com.productos.service;

import com.productos.dto.ProductoRequestDto;
import com.productos.dto.ProductoResponseDto;
import com.productos.entity.Producto;
import com.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoService service;

    public ProductoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearProducto() {
        ProductoRequestDto request = new ProductoRequestDto();
        request.setNombre("Teclado");
        request.setPrecio(new BigDecimal("150.00"));
        request.setDescripcion("Mecánico");

        Producto producto = new Producto(1L, "Teclado", new BigDecimal("150.00"), "Mecánico");
        when(repository.save(any(Producto.class))).thenReturn(producto);

        ProductoResponseDto response = service.saveProducto(request);

        assertNotNull(response);
        assertEquals("Teclado", response.getNombre());
        assertEquals(new BigDecimal("150.00"), response.getPrecio());
    }

    @Test
    void testCrearProductoFallaEnRepositorio() {
        ProductoRequestDto request = new ProductoRequestDto();
        request.setNombre("Pantalla");
        request.setPrecio(new BigDecimal("250.00"));
        request.setDescripcion("LED");

        when(repository.save(any(Producto.class))).thenThrow(new RuntimeException("Error al guardar en base de datos"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.saveProducto(request);
        });

        assertEquals("Error al guardar en base de datos", ex.getMessage());
    }

    @Test
    void testObtenerPorId() {
        Producto producto = new Producto(1L, "Mouse", new BigDecimal("80.00"), "Inalámbrico");
        when(repository.findById(1L)).thenReturn(Optional.of(producto));

        ProductoResponseDto dto = service.findById(1L);

        assertEquals(1L, dto.getId());
        assertEquals("Mouse", dto.getNombre());
    }

    @Test
    void testObtenerPorIdNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.findById(99L);
        });

        assertEquals("Producto no encontrado", ex.getMessage());
    }
}
