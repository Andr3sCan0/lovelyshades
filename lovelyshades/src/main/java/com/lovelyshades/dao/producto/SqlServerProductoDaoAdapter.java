package com.lovelyshades.dao.producto;

import com.lovelyshades.dao.base.AbstractSqlServerDaoAdapter;
import com.lovelyshades.dao.base.ConnectionProvider;
import com.lovelyshades.dao.base.DaoException;
import com.lovelyshades.model.Producto;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SqlServerProductoDaoAdapter extends AbstractSqlServerDaoAdapter implements ProductoDao {

    public SqlServerProductoDaoAdapter(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public Producto guardar(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    producto.setIdProducto(keys.getInt(1));
                }
            }
            return producto;
        } catch (SQLException exception) {
            throw new DaoException("Error al guardar el producto", exception);
        }
    }

    @Override
    public Optional<Producto> buscarPorId(Integer id) {
        System.out.println("asd");
        String sql = "SELECT id_producto, nombre, descripcion, precio, stock FROM productos WHERE id_producto = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DaoException("Error al buscar el producto por id", exception);
        }
    }

    @Override
    public List<Producto> listarTodos() {
        String sql = "SELECT id_producto, nombre, descripcion, precio, stock FROM productos";
        List<Producto> productos = new ArrayList<>();

        try (var connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                productos.add(mapRow(resultSet));
            }
            return productos;
        } catch (SQLException exception) {
            throw new DaoException("Error al listar los productos", exception);
        }
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, stock = ? WHERE id_producto = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.setInt(5, producto.getIdProducto());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al actualizar el producto", exception);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al eliminar el producto", exception);
        }
    }

    @Override
    public List<Producto> listarConStockBajo(Integer stockMinimo) {
        String sql = "SELECT id_producto, nombre, descripcion, precio, stock FROM productos WHERE stock < ?";
        List<Producto> productos = new ArrayList<>();

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, stockMinimo);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    productos.add(mapRow(resultSet));
                }
            }
            return productos;
        } catch (SQLException exception) {
            throw new DaoException("Error al listar productos con stock bajo", exception);
        }
    }

    @Override
    public boolean actualizarStock(Integer idProducto, Integer nuevoStock) {
        String sql = "UPDATE productos SET stock = ? WHERE id_producto = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nuevoStock);
            statement.setInt(2, idProducto);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al actualizar el stock del producto", exception);
        }
    }

    private Producto mapRow(ResultSet resultSet) throws SQLException {
        return new Producto(
                resultSet.getInt("id_producto"),
                resultSet.getString("nombre"),
                resultSet.getString("descripcion"),
                resultSet.getBigDecimal("precio"),
                resultSet.getInt("stock")
        );
    }
}
