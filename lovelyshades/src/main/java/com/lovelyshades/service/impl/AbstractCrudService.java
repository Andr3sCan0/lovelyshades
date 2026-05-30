package com.lovelyshades.service.impl;

import com.lovelyshades.exception.ResourceNotFoundException;
import com.lovelyshades.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class AbstractCrudService<T> implements CrudService<T> {
    protected final JpaRepository<T, Integer> repository;
    protected final String resourceName;

    protected AbstractCrudService(JpaRepository<T, Integer> repository, String resourceName) {
        this.repository = repository;
        this.resourceName = resourceName;
    }

    public List<T> listar() { return repository.findAll(); }

    public T obtenerPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(resourceName + " no encontrado con id " + id));
    }

    public T crear(T entity) { return repository.save(entity); }

    public void eliminar(Integer id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException(resourceName + " no encontrado con id " + id);
        repository.deleteById(id);
    }
}
