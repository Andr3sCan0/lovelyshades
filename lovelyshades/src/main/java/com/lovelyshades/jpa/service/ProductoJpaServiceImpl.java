package com.lovelyshades.jpa.service;

import com.lovelyshades.jpa.entity.ProductoEntity;
import com.lovelyshades.jpa.repository.ProductoJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ProductoJpaServiceImpl implements ProductoJpaService {

    // Inyectamos la interfaz, no la implementación concreta (SOLID - DIP)
    private final ProductoJpaRepository productoJpaRepository;

    public ProductoJpaServiceImpl(ProductoJpaRepository productoJpaRepository) {
        this.productoJpaRepository = productoJpaRepository;
    }

    @Override
    @Transactional
    public ProductoEntity guardar(ProductoEntity producto) {
        return productoJpaRepository.save(producto);
    }

    @Override
    public Optional<ProductoEntity> buscarPorId(Integer id) {
        return productoJpaRepository.findById(id);
    }

    @Override
    public List<ProductoEntity> listarTodos() {
        return productoJpaRepository.findAll();
    }

    @Override
    @Transactional
    public ProductoEntity actualizar(ProductoEntity producto) {
        if (!productoJpaRepository.existsById(producto.getIdProducto())) {
            throw new IllegalArgumentException("Producto no encontrado con id: " + producto.getIdProducto());
        }
        return productoJpaRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        productoJpaRepository.deleteById(id);
    }

    @Override
    public List<ProductoEntity> listarConStockBajo(int stockMinimo) {
        return productoJpaRepository.findByStockLessThan(stockMinimo);
    }

    @Override
    public List<ProductoEntity> buscarPorNombre(String nombre) {
        return productoJpaRepository.buscarPorNombre(nombre);
    }
}