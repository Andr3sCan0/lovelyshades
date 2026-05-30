package com.lovelyshades.jpa.service;

import com.lovelyshades.jpa.entity.ProductoUtilEntity;

import java.util.List;
import java.util.Optional;


public interface ProductoUtilJpaService {

    ProductoUtilEntity guardar(ProductoUtilEntity producto);

    Optional<ProductoUtilEntity> buscarPorId(Integer id);

    List<ProductoUtilEntity> listarTodos();

    ProductoUtilEntity actualizar(ProductoUtilEntity producto);

    void eliminar(Integer id);

    List<ProductoUtilEntity> listarConStockBajo(int stockMinimo);

    List<ProductoUtilEntity> buscarPorNombre(String nombre);
}