package com.lovelyshades.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovelyshades.model.Cliente;
import com.lovelyshades.service.cliente.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private Cliente clienteEjemplo;

    @BeforeEach
    void setUp() {
        clienteEjemplo = new Cliente(1, "Ana López", "3001234567", "ana@email.com");
    }

    // ── GET /api/clientes ─────────────────────────────────────

    @Test
    @DisplayName("GET /api/clientes - debe retornar lista de clientes")
    void listar_debeRetornarListaDeClientes() throws Exception {
        List<Cliente> clientes = List.of(
                clienteEjemplo,
                new Cliente(2, "Juan Pérez", "3109876543", "juan@email.com")
        );
        when(clienteService.listarTodos()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Ana López"))
                .andExpect(jsonPath("$[1].nombre").value("Juan Pérez"));

        verify(clienteService, times(1)).listarTodos();
    }

    @Test
    @DisplayName("GET /api/clientes - lista vacía retorna array vacío")
    void listar_sinClientes_debeRetornarListaVacia() throws Exception {
        when(clienteService.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET /api/clientes/{id} ────────────────────────────────

    @Test
    @DisplayName("GET /api/clientes/{id} - cliente existente retorna el cliente")
    void obtener_clienteExistente_debeRetornarCliente() throws Exception {
        when(clienteService.buscarPorId(1)).thenReturn(Optional.of(clienteEjemplo));

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCliente").value(1))
                .andExpect(jsonPath("$.nombre").value("Ana López"))
                .andExpect(jsonPath("$.email").value("ana@email.com"));

        verify(clienteService, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("GET /api/clientes/{id} - cliente inexistente retorna null")
    void obtener_clienteInexistente_debeRetornarNull() throws Exception {
        when(clienteService.buscarPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/99"))
                .andExpect(status().isOk());
    }

    // ── POST /api/clientes ────────────────────────────────────

    @Test
    @DisplayName("POST /api/clientes - debe crear y retornar el cliente")
    void crear_debeGuardarYRetornarCliente() throws Exception {
        Cliente nuevo = new Cliente(null, "María García", "3201112233", "maria@email.com");
        when(clienteService.guardar(any(Cliente.class))).thenReturn(nuevo);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("María García"))
                .andExpect(jsonPath("$.email").value("maria@email.com"));

        verify(clienteService, times(1)).guardar(any(Cliente.class));
    }

    // ── PUT /api/clientes ─────────────────────────────────────

    @Test
    @DisplayName("PUT /api/clientes - debe actualizar y retornar el cliente")
    void actualizar_debeActualizarYRetornarCliente() throws Exception {
        Cliente actualizado = new Cliente(1, "Ana Actualizada", "3001111111", "ana_new@email.com");
        when(clienteService.actualizar(any(Cliente.class))).thenReturn(true);

        mockMvc.perform(put("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana Actualizada"));

        verify(clienteService, times(1)).actualizar(any(Cliente.class));
    }

    // ── DELETE /api/clientes/{id} ─────────────────────────────

    @Test
    @DisplayName("DELETE /api/clientes/{id} - debe eliminar y retornar mensaje")
    void eliminar_debeEliminarYRetornarMensaje() throws Exception {
        when(clienteService.eliminar(1)).thenReturn(true);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente eliminado"));

        verify(clienteService, times(1)).eliminar(1);
    }
}