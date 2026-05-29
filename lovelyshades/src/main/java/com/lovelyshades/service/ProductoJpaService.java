package com.lovelyshades.service;

import com.lovelyshades.entity.ProductoEntity;

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