package com.lovelyshades.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovelyshades.jpa.controller.ProductoJpaController;
import com.lovelyshades.jpa.entity.ProductoEntity;
import com.lovelyshades.jpa.service.ProductoJpaService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoJpaController.class)
class ProductoJpaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoJpaService productoJpaService;

    private ProductoEntity crearProducto() {

        ProductoEntity producto = new ProductoEntity();

        producto.setIdProducto(1);
        producto.setNombre("Labial Mate");
        producto.setDescripcion("Labial rojo mate");
        producto.setPrecio(new BigDecimal("25000"));
        producto.setStock(20);

        return producto;
    }

    @Test
    @DisplayName("Debe listar todos los productos")
    void debeListarProductos() throws Exception {

        when(productoJpaService.listarTodos())
                .thenReturn(List.of(crearProducto()));

        mockMvc.perform(get("/api/jpa/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Labial Mate"));
    }

    @Test
    @DisplayName("Debe buscar producto por id")
    void debeBuscarProductoPorId() throws Exception {

        when(productoJpaService.buscarPorId(1))
                .thenReturn(Optional.of(crearProducto()));

        mockMvc.perform(get("/api/jpa/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombre").value("Labial Mate"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando no existe")
    void debeRetornar404() throws Exception {

        when(productoJpaService.buscarPorId(99))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/jpa/productos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe crear producto")
    void debeCrearProducto() throws Exception {

        ProductoEntity producto = crearProducto();

        when(productoJpaService.guardar(any()))
                .thenReturn(producto);

        mockMvc.perform(post("/api/jpa/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Labial Mate"));
    }

    @Test
    @DisplayName("Debe actualizar producto")
    void debeActualizarProducto() throws Exception {

        ProductoEntity producto = crearProducto();

        when(productoJpaService.actualizar(any()))
                .thenReturn(producto);

        mockMvc.perform(put("/api/jpa/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1));
    }

    @Test
    @DisplayName("Debe eliminar producto")
    void debeEliminarProducto() throws Exception {

        doNothing().when(productoJpaService)
                .eliminar(1);

        mockMvc.perform(delete("/api/jpa/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado"));
    }

    @Test
    @DisplayName("Debe obtener productos con bajo stock")
    void debeListarBajoStock() throws Exception {

        when(productoJpaService.listarConStockBajo(10))
                .thenReturn(List.of(crearProducto()));

        mockMvc.perform(get("/api/jpa/productos/bajo-stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Labial Mate"));
    }

    @Test
    @DisplayName("Debe buscar por nombre")
    void debeBuscarPorNombre() throws Exception {

        when(productoJpaService.buscarPorNombre("Labial"))
                .thenReturn(List.of(crearProducto()));

        mockMvc.perform(
                        get("/api/jpa/productos/buscar")
                                .param("nombre", "Labial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre")
                        .value("Labial Mate"));
    }
}