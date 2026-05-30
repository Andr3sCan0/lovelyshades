package com.lovelyshades.service;

import com.lovelyshades.entity.Marca;
import java.util.List;

public interface MarcaService {
    Marca crear(Marca marca);
    List<Marca> listar();
    Marca obtenerPorId(Integer id);
    Marca actualizar(Integer id, Marca marca);
    void eliminar(Integer id);
}
