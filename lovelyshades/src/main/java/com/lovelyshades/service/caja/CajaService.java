package com.lovelyshades.service.caja;

import com.lovelyshades.model.Caja;

import java.time.LocalDate;
import java.util.Optional;

public interface CajaService {
    Optional<Caja> buscarPorFecha(LocalDate fecha);
}
