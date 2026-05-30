package com.lovelyshades.service;

import java.util.List;

public interface CrudService<T> {
    List<T> listar();
    T obtenerPorId(Integer id);
    T crear(T entity);
    T actualizar(Integer id, T entity);
    void eliminar(Integer id);
}
