package com.lovelyshades.dao.producto;

import com.lovelyshades.dao.base.BaseDao;
import com.lovelyshades.model.Producto;

import java.util.List;

public interface ProductoDao extends BaseDao<Producto, Integer> {

    List<Producto> listarConStockBajo(int stockMinimo);

    boolean actualizarStock(int idProducto, int nuevoStock);
}
