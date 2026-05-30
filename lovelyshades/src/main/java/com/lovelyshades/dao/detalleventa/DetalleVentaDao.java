package com.lovelyshades.dao.detalleventa;

import com.lovelyshades.dao.base.BaseDao;
import com.lovelyshades.entity.DetalleVenta;

import java.util.List;

public interface DetalleVentaDao extends BaseDao<DetalleVenta, Integer> {

    List<DetalleVenta> listarPorVenta(int idVenta);
}
