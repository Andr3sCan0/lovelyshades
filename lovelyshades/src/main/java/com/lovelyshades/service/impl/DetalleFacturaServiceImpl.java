package com.lovelyshades.service.impl;

import com.lovelyshades.entity.DetalleFactura;
import com.lovelyshades.repository.DetalleFacturaRepository;
import com.lovelyshades.service.DetalleFacturaService;
import org.springframework.stereotype.Service;

@Service
public class DetalleFacturaServiceImpl extends AbstractCrudService<DetalleFactura> implements DetalleFacturaService {
    public DetalleFacturaServiceImpl(DetalleFacturaRepository repository) {
        super(repository, "DetalleFactura");
    }

    @Override
    public DetalleFactura actualizar(Long id, DetalleFactura source) {
        DetalleFactura target = obtenerPorId(id);
        target.setFactura(source.getFactura()); target.setProducto(source.getProducto()); target.setCantidad(source.getCantidad()); target.setPrecioUnitario(source.getPrecioUnitario()); target.setSubtotal(source.getSubtotal());
        return repository.save(target);
    }
}
