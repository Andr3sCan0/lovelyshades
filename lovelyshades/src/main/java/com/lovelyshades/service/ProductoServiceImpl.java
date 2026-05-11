package com.lovelyshades.service;

import com.lovelyshades.dao.producto.ProductoDao;
import com.lovelyshades.model.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductoServiceImpl implements ProductoService {

   
    private final ProductoDao productoDao;

    public ProductoServiceImpl(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoDao.guardar(producto);
    }

    @Override
    public Optional<Producto> buscarPorId(Integer id) {
        return productoDao.buscarPorId(id);
    }

    @Override
    public List<Producto> listarTodos() {
        return productoDao.listarTodos();
    }

    @Override
    public boolean actualizar(Producto producto) {
        return productoDao.actualizar(producto);
    }

    @Override
    public boolean eliminar(Integer id) {
        return productoDao.eliminar(id);
    }

    @Override
    public List<Producto> listarConStockBajo(int stockMinimo) {
        return productoDao.listarConStockBajo(stockMinimo);
    }

    @Override
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        return productoDao.actualizarStock(idProducto, nuevoStock);
    }
}