package com.lovelyshades.service;

import java.util.List;

public interface CrudService<T> {
    List<T> listar();
    T obtenerPorId(Long id);
    T crear(T entity);
    T actualizar(Long id, T entity);
    void eliminar(Long id);
}
