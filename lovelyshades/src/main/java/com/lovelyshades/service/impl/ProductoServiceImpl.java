package com.lovelyshades.service.impl;

import com.lovelyshades.entity.Producto;
import com.lovelyshades.repository.ProductoRepository;
import com.lovelyshades.service.ProductoService;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl extends AbstractCrudService<Producto> implements ProductoService {
    public ProductoServiceImpl(ProductoRepository repository) {
        super(repository, "Producto");
    }

    @Override
    public Producto actualizar(Integer id, Producto source) {
        Producto target = obtenerPorId(id);
        target.setNombre(source.getNombre()); target.setDescripcion(source.getDescripcion()); target.setPrecio(source.getPrecio()); target.setStock(source.getStock()); target.setEstado(source.getEstado());
        return repository.save(target);
    }
}
