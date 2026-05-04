package com.lovelyshades.dao.venta;

import com.lovelyshades.dao.base.BaseDao;
import com.lovelyshades.model.Venta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VentaDao extends BaseDao<Venta, Integer> {

    List<Venta> listarPorCliente(int idCliente);

    BigDecimal obtenerTotalVentasPorFecha(LocalDate fecha);
}
