package com.lovelyshades.lovelyshades.service;

import com.lovelyshades.dao.cliente.ClienteDao;
import com.lovelyshades.dao.cliente.ClienteRepository;
import com.lovelyshades.model.Cliente;

import com.lovelyshades.service.cliente.ClienteServiceImpl;
import com.lovelyshades.utils.ClienteNoEncontradoException;
import com.lovelyshades.utils.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteDao clienteDao;

    @Mock
    private ClienteRepository clienteRepositoryRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {

        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setNombre("Diego");
        cliente.setEmail("diego@gmail.com");
    }

    @Test
    void guardarJpa_ClienteValido_RetornaCliente() {

        when(clienteRepositoryRepository.save(cliente))
                .thenReturn(cliente);

        Cliente resultado =
                clienteService.guardarJpa(cliente);

        assertNotNull(resultado);
        assertEquals("Diego", resultado.getNombre());

        verify(clienteRepositoryRepository)
                .save(cliente);
    }

    @Test
    void guardarJpa_ErrorBD_LanzaDatabaseException() {

        when(clienteRepositoryRepository.save(cliente))
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "SQL Server caído"));

        DatabaseException exception = assertThrows(
                DatabaseException.class,
                () -> clienteService.guardarJpa(cliente)
        );

        assertTrue(exception.getMessage()
                .contains("Error guardando cliente"));
    }

    @Test
    void buscarPorId_ClienteExiste_RetornaCliente() {

        when(clienteDao.buscarPorId(1))
                .thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado =
                clienteService.buscarPorId(1);

        assertTrue(resultado.isPresent());

        assertEquals("Diego",
                resultado.get().getNombre());
    }

    @Test
    void buscarPorId_ClienteNoExiste_LanzaException() {

        when(clienteDao.buscarPorId(1))
                .thenReturn(Optional.empty());

        ClienteNoEncontradoException exception =
                assertThrows(
                        ClienteNoEncontradoException.class,
                        () -> clienteService.buscarPorId(1)
                );

        assertEquals(
                "Cliente no encontrado con id: 1",
                exception.getMessage()
        );
    }

    @Test
    void listarTodos_ErrorBD_LanzaDatabaseException() {

        when(clienteDao.listarTodos())
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "Error SQL"));

        assertThrows(
                DatabaseException.class,
                () -> clienteService.listarTodos()
        );
    }
}
