package com.lovelyshades.service.impl;

import com.lovelyshades.entity.Inventario;
import com.lovelyshades.repository.InventarioRepository;
import com.lovelyshades.service.InventarioService;
import org.springframework.stereotype.Service;

@Service
public class InventarioServiceImpl extends AbstractCrudService<Inventario> implements InventarioService {

    public InventarioServiceImpl(InventarioRepository repository) {
        super(repository, "Inventario");
    }

    @Override
    public Inventario actualizar(Long id, Inventario source) {
        Inventario target = obtenerPorId(id);

        target.setProducto(source.getProducto());
        target.setIdUsuario(source.getIdUsuario());
        target.setFactura(source.getFactura());
        target.setTipoMovimiento(source.getTipoMovimiento());
        target.setCantidad(source.getCantidad());
        target.setFecha(source.getFecha());
        target.setObservacion(source.getObservacion());

        return repository.save(target);
    }
}