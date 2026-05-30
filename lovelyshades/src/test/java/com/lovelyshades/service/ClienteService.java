package com.lovelyshades.service;

import com.lovelyshades.dao.cliente.ClienteDao;
import com.lovelyshades.model.Cliente;
import com.lovelyshades.service.cliente.ClienteServiceImpl;
import com.lovelyshades.utils.ClienteNoEncontradoException;
import com.lovelyshades.utils.DatabaseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNombre("Carlos");
        cliente.setEmail("carlos@test.com");
        cliente.setTelefono("3001234567");
    }


    @Test
    @DisplayName("Debe lanzar DatabaseException al guardar")
    void debeLanzarDatabaseExceptionGuardar() {

        doThrow(new RuntimeException())
                .when(clienteDao)
                .guardar(any(Cliente.class));

        assertThrows(
                DatabaseException.class,
                () -> clienteService.guardar(cliente)
        );
    }

    @Test
    @DisplayName("Debe buscar cliente por ID")
    void debeBuscarClientePorId() {

        when(clienteDao.buscarPorId(1))
                .thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado =
                clienteService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Carlos",
                resultado.get().getNombre());

        verify(clienteDao).buscarPorId(1);
    }

    @Test
    @DisplayName("Debe lanzar ClienteNoEncontradoException cuando no existe")
    void debeLanzarClienteNoEncontrado() {

        when(clienteDao.buscarPorId(1))
                .thenReturn(Optional.empty());

        assertThrows(
                ClienteNoEncontradoException.class,
                () -> clienteService.buscarPorId(1)
        );
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al consultar cliente")
    void debeLanzarDatabaseExceptionBuscarPorId() {

        when(clienteDao.buscarPorId(1))
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> clienteService.buscarPorId(1)
        );
    }

    @Test
    @DisplayName("Debe listar todos los clientes")
    void debeListarClientes() {

        when(clienteDao.listarTodos())
                .thenReturn(List.of(cliente));

        List<Cliente> resultado =
                clienteService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Carlos",
                resultado.get(0).getNombre());

        verify(clienteDao).listarTodos();
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al listar clientes")
    void debeLanzarDatabaseExceptionListar() {

        when(clienteDao.listarTodos())
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> clienteService.listarTodos()
        );
    }

    @Test
    @DisplayName("Debe actualizar cliente")
    void debeActualizarCliente() {

        when(clienteDao.actualizar(cliente))
                .thenReturn(true);

        boolean resultado =
                clienteService.actualizar(cliente);

        assertTrue(resultado);

        verify(clienteDao).actualizar(cliente);
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al actualizar")
    void debeLanzarDatabaseExceptionActualizar() {

        when(clienteDao.actualizar(cliente))
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> clienteService.actualizar(cliente)
        );
    }

    @Test
    @DisplayName("Debe eliminar cliente")
    void debeEliminarCliente() {

        when(clienteDao.eliminar(1))
                .thenReturn(true);

        boolean resultado =
                clienteService.eliminar(1);

        assertTrue(resultado);

        verify(clienteDao).eliminar(1);
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al eliminar")
    void debeLanzarDatabaseExceptionEliminar() {

        when(clienteDao.eliminar(1))
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> clienteService.eliminar(1)
        );
    }

    @Test
    @DisplayName("Debe buscar cliente por email")
    void debeBuscarPorEmail() {

        when(clienteDao.buscarPorEmail("carlos@test.com"))
                .thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado =
                clienteService.buscarPorEmail("carlos@test.com");

        assertTrue(resultado.isPresent());
        assertEquals(
                "Carlos",
                resultado.get().getNombre()
        );

        verify(clienteDao)
                .buscarPorEmail("carlos@test.com");
    }
}