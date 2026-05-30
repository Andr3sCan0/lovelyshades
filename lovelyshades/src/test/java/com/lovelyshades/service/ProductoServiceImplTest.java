package com.lovelyshades.service;

import com.lovelyshades.dao.producto.ProductoDao;
import com.lovelyshades.model.Producto;
import com.lovelyshades.service.producto.ProductoServiceImpl;
import com.lovelyshades.utils.DatabaseException;
import com.lovelyshades.utils.ProductoNoEncontradoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoDao productoDao;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {

        producto = new Producto();

        producto.setIdProducto(1);
        producto.setNombre("Labial Mate");
        producto.setDescripcion("Labial de larga duración");
        producto.setPrecio(new BigDecimal("35000"));
        producto.setStock(20);
    }

    @Test
    @DisplayName("Debe guardar producto correctamente")
    void debeGuardarProducto() {

        when(productoDao.guardar(any(Producto.class)))
                .thenReturn(producto);

        Producto resultado =
                productoService.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Labial Mate",
                resultado.getNombre());

        verify(productoDao)
                .guardar(producto);
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al guardar")
    void debeLanzarDatabaseExceptionGuardar() {

        when(productoDao.guardar(any(Producto.class)))
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> productoService.guardar(producto)
        );
    }

    @Test
    @DisplayName("Debe buscar producto por id")
    void debeBuscarProductoPorId() {

        when(productoDao.buscarPorId(1))
                .thenReturn(Optional.of(producto));

        Optional<Producto> resultado =
                productoService.buscarPorId(1);

        assertTrue(resultado.isPresent());

        assertEquals(
                "Labial Mate",
                resultado.get().getNombre()
        );
    }

    @Test
    @DisplayName("Debe lanzar ProductoNoEncontradoException")
    void debeLanzarProductoNoEncontrado() {

        when(productoDao.buscarPorId(1))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductoNoEncontradoException.class,
                () -> productoService.buscarPorId(1)
        );
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al buscar")
    void debeLanzarDatabaseExceptionBuscar() {

        when(productoDao.buscarPorId(1))
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> productoService.buscarPorId(1)
        );
    }

    @Test
    @DisplayName("Debe listar todos los productos")
    void debeListarProductos() {

        when(productoDao.listarTodos())
                .thenReturn(List.of(producto));

        List<Producto> resultado =
                productoService.listarTodos();

        assertEquals(1, resultado.size());

        verify(productoDao)
                .listarTodos();
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al listar")
    void debeLanzarDatabaseExceptionListar() {

        when(productoDao.listarTodos())
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> productoService.listarTodos()
        );
    }

    @Test
    @DisplayName("Debe actualizar producto")
    void debeActualizarProducto() {

        when(productoDao.actualizar(producto))
                .thenReturn(true);

        boolean resultado =
                productoService.actualizar(producto);

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe lanzar ProductoNoEncontradoException al actualizar")
    void debeLanzarProductoNoEncontradoActualizar() {

        when(productoDao.actualizar(producto))
                .thenReturn(false);

        assertThrows(
                ProductoNoEncontradoException.class,
                () -> productoService.actualizar(producto)
        );
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al actualizar")
    void debeLanzarDatabaseExceptionActualizar() {

        when(productoDao.actualizar(producto))
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> productoService.actualizar(producto)
        );
    }

    @Test
    @DisplayName("Debe eliminar producto")
    void debeEliminarProducto() {

        when(productoDao.eliminar(1))
                .thenReturn(true);

        boolean resultado =
                productoService.eliminar(1);

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe lanzar ProductoNoEncontradoException al eliminar")
    void debeLanzarProductoNoEncontradoEliminar() {

        when(productoDao.eliminar(1))
                .thenReturn(false);

        assertThrows(
                ProductoNoEncontradoException.class,
                () -> productoService.eliminar(1)
        );
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException al eliminar")
    void debeLanzarDatabaseExceptionEliminar() {

        when(productoDao.eliminar(1))
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> productoService.eliminar(1)
        );
    }

    @Test
    @DisplayName("Debe listar productos con stock bajo")
    void debeListarStockBajo() {

        when(productoDao.listarConStockBajo(10))
                .thenReturn(List.of(producto));

        List<Producto> resultado =
                productoService.listarConStockBajo(10);

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe lanzar DatabaseException en stock bajo")
    void debeLanzarDatabaseExceptionStockBajo() {

        when(productoDao.listarConStockBajo(10))
                .thenThrow(new DataAccessException("Error BD") {});

        assertThrows(
                DatabaseException.class,
                () -> productoService.listarConStockBajo(10)
        );
    }

    @Test
    @DisplayName("Debe actualizar stock")
    void debeActualizarStock() {

        when(productoDao.actualizarStock(1, 50))
                .thenReturn(true);

        boolean resultado =
                productoService.actualizarStock(1, 50);

        assertTrue(resultado);

        verify(productoDao)
                .actualizarStock(1, 50);
    }
}
