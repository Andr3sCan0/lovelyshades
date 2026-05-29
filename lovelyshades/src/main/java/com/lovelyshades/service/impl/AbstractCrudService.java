package com.lovelyshades.service.impl;

import com.lovelyshades.exception.ResourceNotFoundException;
import com.lovelyshades.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class AbstractCrudService<T> implements CrudService<T> {
    protected final JpaRepository<T, Long> repository;
    protected final String resourceName;

    protected AbstractCrudService(JpaRepository<T, Long> repository, String resourceName) {
        this.repository = repository;
        this.resourceName = resourceName;
    }

    public List<T> listar() { return repository.findAll(); }

    public T obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(resourceName + " no encontrado con id " + id));
    }

    public T crear(T entity) { return repository.save(entity); }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException(resourceName + " no encontrado con id " + id);
        repository.deleteById(id);
    }
}
