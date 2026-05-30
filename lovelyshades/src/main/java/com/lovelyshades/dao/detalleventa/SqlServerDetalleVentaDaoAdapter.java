package com.lovelyshades.dao.detalleventa;

import com.lovelyshades.dao.base.AbstractSqlServerDaoAdapter;
import com.lovelyshades.dao.base.ConnectionProvider;
import com.lovelyshades.dao.base.DaoException;
import com.lovelyshades.entity.DetalleVenta;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SqlServerDetalleVentaDaoAdapter extends AbstractSqlServerDaoAdapter implements DetalleVentaDao {

    public SqlServerDetalleVentaDaoAdapter(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, detalleVenta.getIdVenta());
            statement.setInt(2, detalleVenta.getIdProducto());
            statement.setInt(3, detalleVenta.getCantidad());
            statement.setBigDecimal(4, detalleVenta.getPrecioUnitario());
            statement.setBigDecimal(5, detalleVenta.getSubtotal());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    detalleVenta.setIdDetalle(keys.getInt(1));
                }
            }
            return detalleVenta;
        } catch (SQLException exception) {
            throw new DaoException("Error al guardar el detalle de venta", exception);
        }
    }

    @Override
    public Optional<DetalleVenta> buscarPorId(Integer id) {
        String sql = "SELECT id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal FROM detalle_venta WHERE id_detalle = ?";

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
            throw new DaoException("Error al buscar el detalle de venta por id", exception);
        }
    }

    @Override
    public List<DetalleVenta> listarTodos() {
        String sql = "SELECT id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal FROM detalle_venta";
        List<DetalleVenta> detalles = new ArrayList<>();

        try (var connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                detalles.add(mapRow(resultSet));
            }
            return detalles;
        } catch (SQLException exception) {
            throw new DaoException("Error al listar el detalle de ventas", exception);
        }
    }

    @Override
    public boolean actualizar(DetalleVenta detalleVenta) {
        String sql = "UPDATE detalle_venta SET id_venta = ?, id_producto = ?, cantidad = ?, precio_unitario = ?, subtotal = ? WHERE id_detalle = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, detalleVenta.getIdVenta());
            statement.setInt(2, detalleVenta.getIdProducto());
            statement.setInt(3, detalleVenta.getCantidad());
            statement.setBigDecimal(4, detalleVenta.getPrecioUnitario());
            statement.setBigDecimal(5, detalleVenta.getSubtotal());
            statement.setInt(6, detalleVenta.getIdDetalle());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al actualizar el detalle de venta", exception);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM detalle_venta WHERE id_detalle = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al eliminar el detalle de venta", exception);
        }
    }

    @Override
    public List<DetalleVenta> listarPorVenta(int idVenta) {
        String sql = "SELECT id_detalle, id_venta, id_producto, cantidad, precio_unitario, subtotal FROM detalle_venta WHERE id_venta = ?";
        List<DetalleVenta> detalles = new ArrayList<>();

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVenta);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    detalles.add(mapRow(resultSet));
                }
            }
            return detalles;
        } catch (SQLException exception) {
            throw new DaoException("Error al listar los detalles por venta", exception);
        }
    }

    private DetalleVenta mapRow(ResultSet resultSet) throws SQLException {
        return new DetalleVenta(
                resultSet.getInt("id_detalle"),
                resultSet.getInt("id_venta"),
                resultSet.getInt("id_producto"),
                resultSet.getInt("cantidad"),
                resultSet.getBigDecimal("precio_unitario"),
                resultSet.getBigDecimal("subtotal")
        );
    }
}
