package com.lovelyshades.jpa.service;

import com.lovelyshades.jpa.entity.ProductoUtilEntity;
import com.lovelyshades.jpa.repository.ProductoUtilJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ProductoUtilUtilJpaServiceImpl implements ProductoUtilJpaService {

    // Inyectamos la interfaz, no la implementación concreta (SOLID - DIP)
    private final ProductoUtilJpaRepository productoUtilJpaRepository;

    public ProductoUtilUtilJpaServiceImpl(ProductoUtilJpaRepository productoUtilJpaRepository) {
        this.productoUtilJpaRepository = productoUtilJpaRepository;
    }

    @Override
    @Transactional
    public ProductoUtilEntity guardar(ProductoUtilEntity producto) {
        return productoUtilJpaRepository.save(producto);
    }

    @Override
    public Optional<ProductoUtilEntity> buscarPorId(Integer id) {
        return productoUtilJpaRepository.findById(id);
    }

    @Override
    public List<ProductoUtilEntity> listarTodos() {
        return productoUtilJpaRepository.findAll();
    }

    @Override
    @Transactional
    public ProductoUtilEntity actualizar(ProductoUtilEntity producto) {
        if (!productoUtilJpaRepository.existsById(producto.getIdProducto())) {
            throw new IllegalArgumentException("Producto no encontrado con id: " + producto.getIdProducto());
        }
        return productoUtilJpaRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        productoUtilJpaRepository.deleteById(id);
    }

    @Override
    public List<ProductoUtilEntity> listarConStockBajo(int stockMinimo) {
        return productoUtilJpaRepository.findByStockLessThan(stockMinimo);
    }

    @Override
    public List<ProductoUtilEntity> buscarPorNombre(String nombre) {
        return productoUtilJpaRepository.buscarPorNombre(nombre);
    }
}