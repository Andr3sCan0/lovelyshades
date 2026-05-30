package com.lovelyshades.dao.caja;

import com.lovelyshades.dao.base.BaseDao;
import com.lovelyshades.entity.Caja;

import java.time.LocalDate;
import java.util.Optional;

public interface CajaDao extends BaseDao<Caja, Integer> {

    Optional<Caja> buscarPorFecha(LocalDate fecha);
}
