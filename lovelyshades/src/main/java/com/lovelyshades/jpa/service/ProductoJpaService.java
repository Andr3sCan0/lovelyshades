package com.lovelyshades.jpa.service;

import com.lovelyshades.jpa.entity.ProductoEntity;

import java.util.List;
import java.util.Optional;


public interface ProductoJpaService {

    ProductoEntity guardar(ProductoEntity producto);

    Optional<ProductoEntity> buscarPorId(Integer id);

    List<ProductoEntity> listarTodos();

    ProductoEntity actualizar(ProductoEntity producto);

    void eliminar(Integer id);

    List<ProductoEntity> listarConStockBajo(int stockMinimo);

    List<ProductoEntity> buscarPorNombre(String nombre);
}