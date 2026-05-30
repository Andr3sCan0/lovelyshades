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
    public Inventario actualizar(Integer id, Inventario source) {
        Inventario target = obtenerPorId(id);

        target.setProducto(source.getProducto());
        target.setStockAntes(source.getStockAntes());
        target.setStockDespues(source.getStockDespues());
        target.setFechaMovimiento(source.getFechaMovimiento());
        target.setIdUsuario(source.getIdUsuario());
        target.setFactura(source.getFactura());
        target.setServicio(source.getServicio());
        target.setTipoMovimiento(source.getTipoMovimiento());
        target.setCantidad(source.getCantidad());
        target.setMotivo(source.getMotivo());
        target.setObservaciones(source.getObservaciones());

        return repository.save(target);
    }
}