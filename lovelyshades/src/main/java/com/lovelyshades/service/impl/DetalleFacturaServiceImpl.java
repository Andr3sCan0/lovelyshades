package com.lovelyshades.service.impl;

import com.lovelyshades.entity.DetalleFactura;
import com.lovelyshades.entity.Factura;
import com.lovelyshades.entity.Inventario;
import com.lovelyshades.entity.Producto;
import com.lovelyshades.repository.DetalleFacturaRepository;
import com.lovelyshades.repository.FacturaRepository;
import com.lovelyshades.repository.InventarioRepository;
import com.lovelyshades.repository.ProductoRepository;
import com.lovelyshades.service.DetalleFacturaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DetalleFacturaServiceImpl extends AbstractCrudService<DetalleFactura> implements DetalleFacturaService {

    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final FacturaRepository facturaRepository;

    public DetalleFacturaServiceImpl(DetalleFacturaRepository repository,
                                     ProductoRepository productoRepository,
                                     InventarioRepository inventarioRepository,
                                     FacturaRepository facturaRepository) {
        super(repository, "DetalleFactura");
        this.productoRepository = productoRepository;
        this.inventarioRepository = inventarioRepository;
        this.facturaRepository = facturaRepository;
    }

    @Override
    @Transactional
    public DetalleFactura crear(DetalleFactura entity) {
        DetalleFactura detalle = super.crear(entity);

        if (detalle.getProducto() == null || detalle.getProducto().getIdProducto() == null) {
            return detalle;
        }

        Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id " + detalle.getProducto().getIdProducto()));

        int stockAntes = producto.getStock() != null ? producto.getStock() : 0;
        int cantidad = detalle.getCantidad() != null ? detalle.getCantidad() : 0;

        if (cantidad > stockAntes) {
            throw new IllegalArgumentException("Stock insuficiente para el producto " + producto.getNombre());
        }

        producto.setStock(stockAntes - cantidad);
        productoRepository.save(producto);

        Inventario movimiento = new Inventario();
        movimiento.setProducto(producto);
        movimiento.setStockAntes(stockAntes);
        movimiento.setCantidad(cantidad);
        movimiento.setStockDespues(producto.getStock());
        movimiento.setTipoMovimiento("SALIDA");
        movimiento.setMotivo("Venta factura");
        movimiento.setObservaciones("Salida generada por detalle de factura");
        movimiento.setIdUsuario(detalle.getFactura() != null && detalle.getFactura().getIdUsuario() != null
                ? detalle.getFactura().getIdUsuario() : 0);

        if (detalle.getFactura() != null && detalle.getFactura().getIdFactura() != null) {
            Factura facturaRef = facturaRepository.getReferenceById(detalle.getFactura().getIdFactura());
            movimiento.setFactura(facturaRef);
        }

        inventarioRepository.save(movimiento);

        return detalle;
    }

    @Override
    public DetalleFactura actualizar(Integer id, DetalleFactura source) {
        DetalleFactura target = obtenerPorId(id);
        target.setFactura(source.getFactura()); target.setProducto(source.getProducto()); target.setCantidad(source.getCantidad()); target.setPrecioUnitario(source.getPrecioUnitario()); target.setSubtotal(source.getSubtotal());
        return repository.save(target);
    }
}
