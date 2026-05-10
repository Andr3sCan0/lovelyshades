
package com.lovelyshades.jpa.repository;

import com.lovelyshades.jpa.entity.ProductoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class ProductoJpaRepositoryTest {

    @Autowired
    private ProductoJpaRepository productoJpaRepository;

    @Test
    @DisplayName("Debe guardar correctamente un producto")
    void guardarProducto() {

        ProductoEntity producto = new ProductoEntity();

        producto.setNombre("Labial Mate");
        producto.setDescripcion("Labial rojo mate");
        producto.setPrecio(new BigDecimal("25000"));
        producto.setStock(10);

        ProductoEntity guardado =
                productoJpaRepository.save(producto);

        assertNotNull(guardado.getIdProducto());
        assertEquals("Labial Mate", guardado.getNombre());
    }

    @Test
    @DisplayName("Debe buscar un producto por ID")
    void buscarProductoPorId() {

        ProductoEntity producto = new ProductoEntity();

        producto.setNombre("Base Líquida");
        producto.setDescripcion("Base tono beige");
        producto.setPrecio(new BigDecimal("45000"));
        producto.setStock(5);

        ProductoEntity guardado =
                productoJpaRepository.save(producto);

        Optional<ProductoEntity> encontrado =
                productoJpaRepository.findById(
                        guardado.getIdProducto());

        assertTrue(encontrado.isPresent());
        assertEquals("Base Líquida",
                encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Debe listar productos")
    void listarProductos() {

        ProductoEntity p1 = new ProductoEntity();
        p1.setNombre("Polvo Compacto");
        p1.setDescripcion("Polvo facial");
        p1.setPrecio(new BigDecimal("30000"));
        p1.setStock(3);

        ProductoEntity p2 = new ProductoEntity();
        p2.setNombre("Delineador");
        p2.setDescripcion("Delineador negro");
        p2.setPrecio(new BigDecimal("18000"));
        p2.setStock(8);

        productoJpaRepository.save(p1);
        productoJpaRepository.save(p2);

        List<ProductoEntity> lista =
                productoJpaRepository.findAll();

        assertFalse(lista.isEmpty());
        assertTrue(lista.size() >= 2);
    }

    @Test
    @DisplayName("Debe eliminar un producto")
    void eliminarProducto() {

        ProductoEntity producto = new ProductoEntity();

        producto.setNombre("Corrector");
        producto.setDescripcion("Corrector líquido");
        producto.setPrecio(new BigDecimal("22000"));
        producto.setStock(4);

        ProductoEntity guardado =
                productoJpaRepository.save(producto);

        productoJpaRepository.deleteById(
                guardado.getIdProducto());

        Optional<ProductoEntity> eliminado =
                productoJpaRepository.findById(
                        guardado.getIdProducto());

        assertFalse(eliminado.isPresent());
    }

    @Test
    @DisplayName("Debe buscar productos con stock menor")
    void buscarPorStockMenor() {

        ProductoEntity producto = new ProductoEntity();

        producto.setNombre("Rubor");
        producto.setDescripcion("Rubor rosado");
        producto.setPrecio(new BigDecimal("27000"));
        producto.setStock(2);

        productoJpaRepository.save(producto);

        List<ProductoEntity> lista =
                productoJpaRepository.findByStockLessThan(5);

        assertFalse(lista.isEmpty());
    }

    @Test
    @DisplayName("Debe buscar productos por nombre")
    void buscarPorNombre() {

        ProductoEntity producto = new ProductoEntity();

        producto.setNombre("Máscara de pestañas");
        producto.setDescripcion("Volumen extremo");
        producto.setPrecio(new BigDecimal("35000"));
        producto.setStock(6);

        productoJpaRepository.save(producto);

        List<ProductoEntity> lista =
                productoJpaRepository.buscarPorNombre("pestañas");

        assertFalse(lista.isEmpty());
    }
}