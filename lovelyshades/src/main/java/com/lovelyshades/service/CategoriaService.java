package com.lovelyshades.service;

import com.lovelyshades.entity.Categoria;
import java.util.List;

public interface CategoriaService {
    Categoria crear(Categoria categoria);
    List<Categoria> listar();
    Categoria obtenerPorId(Integer id);
    Categoria actualizar(Integer id, Categoria categoria);
    void eliminar(Integer id);
}
