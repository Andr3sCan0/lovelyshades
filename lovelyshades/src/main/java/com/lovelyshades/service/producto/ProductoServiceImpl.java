package com.lovelyshades.service.producto;

import com.lovelyshades.dao.producto.ProductoDao;
import com.lovelyshades.model.Producto;
import com.lovelyshades.utils.DatabaseException;
import com.lovelyshades.utils.ProductoNoEncontradoException;
import org.springframework.dao.DataAccessException;
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
        try {

            productoDao.guardar(producto);

            return producto;

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error creando producto", e);
        }
    }

    @Override
    public Optional<Producto> buscarPorId(Integer id) {
        try {

            Optional<Producto> producto =
                    productoDao.buscarPorId(id);

            if (producto.isEmpty()) {

                throw new ProductoNoEncontradoException(
                        "Producto no encontrado con id: " + id);
            }

            return producto;

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error obteniendo producto", e);
        }
    }

    @Override
    public List<Producto> listarTodos() {
        try {

            return productoDao.listarTodos();

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error listando productos", e);
        }
    }

    @Override
    public boolean actualizar(Producto producto) {
        try {

            boolean actualizado =
                    productoDao.actualizar(producto);

            if (!actualizado) {

                throw new ProductoNoEncontradoException(
                        "No se pudo actualizar el producto");
            }

            return actualizado;

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error actualizando producto", e);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        try {

            boolean eliminado =
                    productoDao.eliminar(id);

            if (!eliminado) {

                throw new ProductoNoEncontradoException(
                        "Producto no encontrado para eliminar");
            }

            return eliminado;

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error eliminando producto", e);
        }
    }

    @Override
    public List<Producto> listarConStockBajo(int stockMinimo) {
        try {

            return productoDao.listarConStockBajo(10);

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error consultando productos con bajo stock",
                    e);
        }
    }

    @Override
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        return productoDao.actualizarStock(idProducto, nuevoStock);
    }
}