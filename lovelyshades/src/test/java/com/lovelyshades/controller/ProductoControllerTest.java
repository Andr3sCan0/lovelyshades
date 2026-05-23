package com.lovelyshades.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovelyshades.model.Producto;
import com.lovelyshades.service.producto.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoService productoService;

    private Producto productoEjemplo;

    @BeforeEach
    void setUp() {
        productoEjemplo = new Producto(1, "Labial Rojo", "Labial matte", new BigDecimal("35000"), 50);
    }

    // ── GET /api/productos ────────────────────────────────────

    @Test
    @DisplayName("GET /api/productos - debe retornar lista de productos")
    void listar_debeRetornarListaDeProductos() throws Exception {
        List<Producto> productos = List.of(
                productoEjemplo,
                new Producto(2, "Base Líquida", "Base tono beige", new BigDecimal("65000"), 30)
        );
        when(productoService.listarTodos()).thenReturn(productos);

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Labial Rojo"))
                .andExpect(jsonPath("$[1].nombre").value("Base Líquida"));

        verify(productoService, times(1)).listarTodos();
    }

    @Test
    @DisplayName("GET /api/productos - lista vacía retorna array vacío")
    void listar_sinProductos_debeRetornarListaVacia() throws Exception {
        when(productoService.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/productos/{id} ───────────────────────────────

    @Test
    @DisplayName("GET /api/productos/{id} - producto existente retorna el producto")
    void obtener_productoExistente_debeRetornarProducto() throws Exception {
        when(productoService.buscarPorId(1)).thenReturn(Optional.of(productoEjemplo));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombre").value("Labial Rojo"))
                .andExpect(jsonPath("$.stock").value(50));

        verify(productoService, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("GET /api/productos/{id} - producto inexistente retorna null")
    void obtener_productoInexistente_debeRetornarNull() throws Exception {
        when(productoService.buscarPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/99"))
                .andExpect(status().isOk());
    }

    // ── POST /api/productos ───────────────────────────────────

    @Test
    @DisplayName("POST /api/productos - debe crear y retornar el producto")
    void crear_debeGuardarYRetornarProducto() throws Exception {
        Producto nuevo = new Producto(null, "Sombra", "Paleta nude", new BigDecimal("45000"), 20);
        when(productoService.guardar(any(Producto.class))).thenReturn(nuevo);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sombra"));

        verify(productoService, times(1)).guardar(any(Producto.class));
    }

    // ── PUT /api/productos ────────────────────────────────────

    @Test
    @DisplayName("PUT /api/productos - debe actualizar y retornar el producto")
    void actualizar_debeActualizarProducto() throws Exception {
        Producto actualizado = new Producto(1, "Labial Updated", "Nueva desc", new BigDecimal("40000"), 45);
        when(productoService.actualizar(any(Producto.class))).thenReturn(true);

        mockMvc.perform(put("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Labial Updated"));

        verify(productoService, times(1)).actualizar(any(Producto.class));
    }

    // ── DELETE /api/productos/{id} ────────────────────────────

    @Test
    @DisplayName("DELETE /api/productos/{id} - debe eliminar y retornar mensaje")
    void eliminar_debeEliminarYRetornarMensaje() throws Exception {
        when(productoService.eliminar(1)).thenReturn(true);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado"));

        verify(productoService, times(1)).eliminar(1);
    }

    // ── GET /api/productos/bajo-stock ─────────────────────────

    @Test
    @DisplayName("GET /api/productos/bajo-stock - retorna productos con stock bajo")
    void bajoStock_debeRetornarProductosConStockBajo() throws Exception {
        List<Producto> bajoStock = List.of(
                new Producto(3, "Delineador", "Negro", new BigDecimal("20000"), 5),
                new Producto(4, "Corrector", "Beige", new BigDecimal("25000"), 3)
        );
        when(productoService.listarConStockBajo(10)).thenReturn(bajoStock);

        mockMvc.perform(get("/api/productos/bajo-stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].stock").value(5))
                .andExpect(jsonPath("$[1].stock").value(3));

        verify(productoService, times(1)).listarConStockBajo(10);
    }

    @Test
    @DisplayName("GET /api/productos/bajo-stock - sin productos bajo stock retorna lista vacía")
    void bajoStock_sinProductos_debeRetornarListaVacia() throws Exception {
        when(productoService.listarConStockBajo(10)).thenReturn(List.of());

        mockMvc.perform(get("/api/productos/bajo-stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}