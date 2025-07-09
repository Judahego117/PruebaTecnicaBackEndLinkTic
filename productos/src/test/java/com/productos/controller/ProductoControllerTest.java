package com.productos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productos.dto.ProductoRequestDto;
import com.productos.dto.ProductoResponseDto;
import com.productos.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearProducto() throws Exception {
        ProductoRequestDto request = new ProductoRequestDto();
        request.setNombre("Monitor");
        request.setPrecio(new BigDecimal("300.00"));
        request.setDescripcion("Full HD");

        ProductoResponseDto response = new ProductoResponseDto();
        response.setId(1L);
        response.setNombre("Monitor");
        response.setPrecio(new BigDecimal("300.00"));
        response.setDescripcion("Full HD");

        Mockito.when(service.saveProducto(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data._type").value("producto"))
                .andExpect(jsonPath("$.data._id").value(1))
                .andExpect(jsonPath("$.data._attributes.nombre").value("Monitor"));
    }

    @Test
    void testObtenerProductoPorId() throws Exception {
        ProductoResponseDto response = new ProductoResponseDto();
        response.setId(2L);
        response.setNombre("Tablet");
        response.setPrecio(new BigDecimal("400.00"));
        response.setDescripcion("10 pulgadas");

        Mockito.when(service.findById(2L)).thenReturn(response);

        mockMvc.perform(get("/productos/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data._type").value("producto"))
                .andExpect(jsonPath("$.data._id").value(2))
                .andExpect(jsonPath("$.data._attributes.id").value(2))
                .andExpect(jsonPath("$.data._attributes.nombre").value("Tablet"));
    }

    @Test
    void testCrearProductoInvalido() throws Exception {
        ProductoRequestDto request = new ProductoRequestDto();
        request.setNombre("");
        request.setPrecio(new BigDecimal("0"));
        request.setDescripcion("Sin nombre ni precio valido");

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").value("Datos inválidos"));
    }

    @Test
    void testObtenerProductoPorIdNoExiste() throws Exception {
        Mockito.when(service.findById(999L)).thenThrow(new RuntimeException("Producto no encontrado"));

        mockMvc.perform(get("/productos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors.detail").value("Producto no encontrado"));
    }
}
