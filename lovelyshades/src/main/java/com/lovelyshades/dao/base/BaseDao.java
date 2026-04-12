package com.lovelyshades.dao.base;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T, ID> {

    T guardar(T entidad);

    Optional<T> buscarPorId(ID id);

    List<T> listarTodos();

    boolean actualizar(T entidad);

    boolean eliminar(ID id);
}
