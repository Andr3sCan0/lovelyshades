package com.lovelyshades.service.impl;

import com.lovelyshades.entity.Factura;
import com.lovelyshades.repository.FacturaRepository;
import com.lovelyshades.service.FacturaService;
import org.springframework.stereotype.Service;

@Service
public class FacturaServiceImpl extends AbstractCrudService<Factura> implements FacturaService {

    public FacturaServiceImpl(FacturaRepository repository) {
        super(repository, "Factura");
    }

    @Override
    public Factura actualizar(Integer id, Factura source) {
        Factura target = obtenerPorId(id);

        target.setFecha(source.getFecha());
        target.setSubtotal(source.getSubtotal());
        target.setImpuesto(source.getImpuesto());
        target.setTotal(source.getTotal());
        target.setEstado(source.getEstado());
        target.setCliente(source.getCliente());
        target.setIdUsuario(source.getIdUsuario());

        return repository.save(target);
    }
}