package com.lovelyshades.dao.detalleventa;

import com.lovelyshades.dao.base.BaseDao;
import com.lovelyshades.model.DetalleVenta;

import java.util.List;

public interface DetalleVentaDao extends BaseDao<DetalleVenta, Integer> {

    List<DetalleVenta> listarPorVenta(int idVenta);
}
