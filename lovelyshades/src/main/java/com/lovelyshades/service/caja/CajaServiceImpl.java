package com.lovelyshades.service.caja;

import com.lovelyshades.dao.base.AbstractSqlServerDaoAdapter;
import com.lovelyshades.dao.base.ConnectionProvider;
import com.lovelyshades.dao.caja.CajaDao;
import com.lovelyshades.model.Caja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CajaServiceImpl implements CajaService {

    @Autowired
    CajaDao cajaDao;

    @Override
    public Optional<Caja> buscarPorFecha(LocalDate fecha) {
        return Optional.empty();
    }
}
