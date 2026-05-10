package com.lovelyshades.lovelyshades.service;

import com.lovelyshades.service.producto.ProductServiceImpl;
import com.lovelyshades.utils.DatabaseException;
import com.lovelyshades.utils.ProductoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.lovelyshades.dao.producto.ProductoDao;
import com.lovelyshades.model.Producto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.dao.DataAccessResourceFailureException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductoDao productoDao;

    @InjectMocks
    private ProductServiceImpl productService;

    private Producto producto;

    @BeforeEach
    void setUp() {

        producto = new Producto();

        producto.setIdProducto(1);
        producto.setNombre("Labial");
        producto.setStock(5);
    }

    @Test
    void listar_RetornaListaProductos() {

        when(productoDao.listarTodos())
                .thenReturn(List.of(producto));

        List<Producto> resultado =
                productService.listar();

        assertFalse(resultado.isEmpty());

        assertEquals(1, resultado.size());

        verify(productoDao).listarTodos();
    }

    @Test
    void listar_ErrorBD_LanzaDatabaseException() {

        when(productoDao.listarTodos())
                .thenThrow(
                        new DataAccessResourceFailureException(
                                "SQL Server caído"));

        assertThrows(
                DatabaseException.class,
                () -> productService.listar()
        );
    }

    @Test
    void obtener_ProductoExiste_RetornaProducto() {

        when(productoDao.buscarPorId(1))
                .thenReturn(Optional.of(producto));

        Producto resultado =
                productService.obtener(1);

        assertNotNull(resultado);

        assertEquals("Labial",
                resultado.getNombre());
    }

    @Test
    void obtener_ProductoNoExiste_LanzaException() {

        when(productoDao.buscarPorId(1))
                .thenReturn(Optional.empty());

        ProductoNoEncontradoException exception =
                assertThrows(
                        ProductoNoEncontradoException.class,
                        () -> productService.obtener(1)
                );

        assertEquals(
                "Producto no encontrado con id: 1",
                exception.getMessage()
        );
    }

    @Test
    void crear_ProductoValido_RetornaProducto() {

        doNothing().when(productoDao)
                .guardar(producto);

        Producto resultado =
                productService.crear(producto);

        assertNotNull(resultado);

        assertEquals("Labial",
                resultado.getNombre());

        verify(productoDao)
                .guardar(producto);
    }

    @Test
    void actualizar_ProductoValido_RetornaProducto() {

        when(productoDao.actualizar(producto))
                .thenReturn(true);

        Producto resultado =
                productService.actualizar(producto);

        assertNotNull(resultado);

        verify(productoDao)
                .actualizar(producto);
    }

    @Test
    void actualizar_Error_LanzaException() {

        when(productoDao.actualizar(producto))
                .thenReturn(false);

        assertThrows(
                ProductoNoEncontradoException.class,
                () -> productService.actualizar(producto)
        );
    }

    @Test
    void eliminar_ProductoExiste_RetornaMensaje() {

        when(productoDao.eliminar(1))
                .thenReturn(true);

        String resultado =
                productService.eliminar(1);

        assertEquals(
                "Producto eliminado",
                resultado);
    }

    @Test
    void eliminar_ProductoNoExiste_LanzaException() {

        when(productoDao.eliminar(1))
                .thenReturn(false);

        assertThrows(
                ProductoNoEncontradoException.class,
                () -> productService.eliminar(1)
        );
    }

    @Test
    void obtenerBajoStock_RetornaLista() {

        when(productoDao.listarConStockBajo(10))
                .thenReturn(List.of(producto));

        List<Producto> resultado =
                productService.obtenerBajoStock();

        assertFalse(resultado.isEmpty());

        verify(productoDao)
                .listarConStockBajo(10);
    }
}