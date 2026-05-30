package com.lovelyshades.dao.venta;

import com.lovelyshades.dao.base.AbstractSqlServerDaoAdapter;
import com.lovelyshades.dao.base.ConnectionProvider;
import com.lovelyshades.dao.base.DaoException;
import com.lovelyshades.entity.Venta;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SqlServerVentaDaoAdapter extends AbstractSqlServerDaoAdapter implements VentaDao {

    public SqlServerVentaDaoAdapter(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public Venta guardar(Venta venta) {
        String sql = "INSERT INTO ventas (fecha, id_cliente, total) VALUES (?, ?, ?)";
        LocalDateTime fecha = venta.getFecha() != null ? venta.getFecha() : LocalDateTime.now();

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(fecha));
            statement.setInt(2, venta.getIdCliente());
            statement.setBigDecimal(3, venta.getTotal());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    venta.setIdVenta(keys.getInt(1));
                }
            }
            venta.setFecha(fecha);
            return venta;
        } catch (SQLException exception) {
            throw new DaoException("Error al guardar la venta", exception);
        }
    }

    @Override
    public Optional<Venta> buscarPorId(Integer id) {
        String sql = "SELECT id_venta, fecha, id_cliente, total FROM ventas WHERE id_venta = ?";

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
            throw new DaoException("Error al buscar la venta por id", exception);
        }
    }

    @Override
    public List<Venta> listarTodos() {
        String sql = "SELECT id_venta, fecha, id_cliente, total FROM ventas";
        List<Venta> ventas = new ArrayList<>();

        try (var connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ventas.add(mapRow(resultSet));
            }
            return ventas;
        } catch (SQLException exception) {
            throw new DaoException("Error al listar las ventas", exception);
        }
    }

    @Override
    public boolean actualizar(Venta venta) {
        String sql = "UPDATE ventas SET fecha = ?, id_cliente = ?, total = ? WHERE id_venta = ?";
        LocalDateTime fecha = venta.getFecha() != null ? venta.getFecha() : LocalDateTime.now();

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(fecha));
            statement.setInt(2, venta.getIdCliente());
            statement.setBigDecimal(3, venta.getTotal());
            statement.setInt(4, venta.getIdVenta());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al actualizar la venta", exception);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM ventas WHERE id_venta = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al eliminar la venta", exception);
        }
    }

    @Override
    public List<Venta> listarPorCliente(int idCliente) {
        String sql = "SELECT id_venta, fecha, id_cliente, total FROM ventas WHERE id_cliente = ?";
        List<Venta> ventas = new ArrayList<>();

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCliente);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ventas.add(mapRow(resultSet));
                }
            }
            return ventas;
        } catch (SQLException exception) {
            throw new DaoException("Error al listar ventas por cliente", exception);
        }
    }

    @Override
    public BigDecimal obtenerTotalVentasPorFecha(LocalDate fecha) {
        String sql = "SELECT ISNULL(SUM(total), 0) AS total_dia FROM ventas WHERE CAST(fecha AS DATE) = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(fecha));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("total_dia");
                }
                return BigDecimal.ZERO;
            }
        } catch (SQLException exception) {
            throw new DaoException("Error al calcular el total de ventas por fecha", exception);
        }
    }

    private Venta mapRow(ResultSet resultSet) throws SQLException {
        return new Venta(
                resultSet.getInt("id_venta"),
                resultSet.getTimestamp("fecha").toLocalDateTime(),
                resultSet.getInt("id_cliente"),
                resultSet.getBigDecimal("total")
        );
    }
}
