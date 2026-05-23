package com.lovelyshades.service.producto;

import com.lovelyshades.model.Producto;

import java.util.List;
import java.util.Optional;


public interface ProductoService {

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(Integer id);

    List<Producto> listarTodos();

    boolean actualizar(Producto producto);

    boolean eliminar(Integer id);

    List<Producto> listarConStockBajo(int stockMinimo);

    boolean actualizarStock(int idProducto, int nuevoStock);
}