package com.lovelyshades.dao.caja;

import com.lovelyshades.dao.base.AbstractSqlServerDaoAdapter;
import com.lovelyshades.dao.base.ConnectionProvider;
import com.lovelyshades.dao.base.DaoException;
import com.lovelyshades.model.Caja;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SqlServerCajaDaoAdapter extends AbstractSqlServerDaoAdapter implements CajaDao {

    public SqlServerCajaDaoAdapter(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public Caja guardar(Caja caja) {
        String sql = "INSERT INTO caja (fecha, total_ventas, monto_inicial, monto_final, diferencia) VALUES (?, ?, ?, ?, ?)";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, java.sql.Date.valueOf(caja.getFecha()));
            statement.setBigDecimal(2, caja.getTotalVentas());
            statement.setBigDecimal(3, caja.getMontoInicial());
            statement.setBigDecimal(4, caja.getMontoFinal());
            statement.setBigDecimal(5, caja.getDiferencia());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    caja.setIdCaja(keys.getInt(1));
                }
            }
            return caja;
        } catch (SQLException exception) {
            throw new DaoException("Error al guardar el cuadre de caja", exception);
        }
    }

    @Override
    public Optional<Caja> buscarPorId(Integer id) {
        String sql = "SELECT id_caja, fecha, total_ventas, monto_inicial, monto_final, diferencia FROM caja WHERE id_caja = ?";

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
            throw new DaoException("Error al buscar el cuadre de caja por id", exception);
        }
    }

    @Override
    public List<Caja> listarTodos() {
        String sql = "SELECT id_caja, fecha, total_ventas, monto_inicial, monto_final, diferencia FROM caja";
        List<Caja> cajas = new ArrayList<>();

        try (var connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cajas.add(mapRow(resultSet));
            }
            return cajas;
        } catch (SQLException exception) {
            throw new DaoException("Error al listar los cuadres de caja", exception);
        }
    }

    @Override
    public boolean actualizar(Caja caja) {
        String sql = "UPDATE caja SET fecha = ?, total_ventas = ?, monto_inicial = ?, monto_final = ?, diferencia = ? WHERE id_caja = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(caja.getFecha()));
            statement.setBigDecimal(2, caja.getTotalVentas());
            statement.setBigDecimal(3, caja.getMontoInicial());
            statement.setBigDecimal(4, caja.getMontoFinal());
            statement.setBigDecimal(5, caja.getDiferencia());
            statement.setInt(6, caja.getIdCaja());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al actualizar el cuadre de caja", exception);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM caja WHERE id_caja = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al eliminar el cuadre de caja", exception);
        }
    }

    @Override
    public Optional<Caja> buscarPorFecha(LocalDate fecha) {
        String sql = "SELECT id_caja, fecha, total_ventas, monto_inicial, monto_final, diferencia FROM caja WHERE fecha = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(fecha));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DaoException("Error al buscar el cuadre de caja por fecha", exception);
        }
    }

    private Caja mapRow(ResultSet resultSet) throws SQLException {
        return new Caja(
                resultSet.getInt("id_caja"),
                resultSet.getDate("fecha").toLocalDate(),
                resultSet.getBigDecimal("total_ventas"),
                resultSet.getBigDecimal("monto_inicial"),
                resultSet.getBigDecimal("monto_final"),
                resultSet.getBigDecimal("diferencia")
        );
    }
}
