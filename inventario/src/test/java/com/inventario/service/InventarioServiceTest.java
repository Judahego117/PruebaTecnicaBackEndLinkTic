package com.inventario.service;

import com.inventario.dto.InventarioRequestDto;
import com.inventario.dto.InventarioResponseDto;
import com.inventario.entity.Inventario;
import com.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository repository;

    @InjectMocks
    private InventarioService service;

    @Mock
    private HistorialInventarioService historialInventarioService;

    private Inventario inventario;
    private InventarioRequestDto request;


    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProductoId(1L);
        inventario.setCantidad(10);

        request = new InventarioRequestDto();
        request.setProductoId(1L);
        request.setCantidad(10);
    }

    @Test
    void testGuardarInventario_exitoso() {
        when(repository.save(any(Inventario.class))).thenReturn(inventario);

        InventarioResponseDto response = service.saveInventario(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getProductoId());
        assertEquals(10, response.getCantidad());
    }

    @Test
    void testObtenerInventarioPorProductoId_exitoso() {
        when(repository.findByProductoId(1L)).thenReturn(Optional.of(inventario));

        InventarioResponseDto response = service.obtenerPorProductoId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getProductoId());
    }

    @Test
    void testObtenerInventarioPorProductoId_fallido_noExiste() {
        when(repository.findByProductoId(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.obtenerPorProductoId(1L)
        );

        assertEquals("Inventario no encontrado para producto ID: 1", exception.getMessage());
    }

    @Test
    void guardarInventario_deberiaLanzarExcepcionCuandoFallaElGuardado() {
        InventarioRequestDto request = new InventarioRequestDto();
        request.setProductoId(1L);
        request.setCantidad(10);

        when(repository.save(any(Inventario.class)))
                .thenThrow(new RuntimeException("Error al guardar inventario"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.saveInventario(request));

        assertEquals("Error al guardar inventario", exception.getMessage());
        verify(repository, times(1)).save(any(Inventario.class));
    }

}
