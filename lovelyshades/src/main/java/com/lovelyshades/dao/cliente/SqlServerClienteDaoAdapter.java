package com.lovelyshades.dao.cliente;

import com.lovelyshades.dao.base.AbstractSqlServerDaoAdapter;
import com.lovelyshades.dao.base.ConnectionProvider;
import com.lovelyshades.dao.base.DaoException;
import com.lovelyshades.model.Cliente;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SqlServerClienteDaoAdapter extends AbstractSqlServerDaoAdapter implements ClienteDao {

    public SqlServerClienteDaoAdapter(ConnectionProvider connectionProvider) {
        super(connectionProvider);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, telefono, email) VALUES (?, ?, ?)";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.setString(3, cliente.getEmail());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    cliente.setIdCliente(keys.getInt(1));
                }
            }
            return cliente;
        } catch (SQLException exception) {
            throw new DaoException("Error al guardar el cliente", exception);
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(Integer id) {
        String sql = "SELECT id_cliente, nombre, telefono, email FROM clientes WHERE id_cliente = ?";

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
            throw new DaoException("Error al buscar el cliente por id", exception);
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        String sql = "SELECT id_cliente, nombre, telefono, email FROM clientes";
        List<Cliente> clientes = new ArrayList<>();

        try (var connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                clientes.add(mapRow(resultSet));
            }
            return clientes;
        } catch (SQLException exception) {
            throw new DaoException("Error al listar los clientes", exception);
        }
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, email = ? WHERE id_cliente = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.setString(3, cliente.getEmail());
            statement.setInt(4, cliente.getIdCliente());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al actualizar el cliente", exception);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException("Error al eliminar el cliente", exception);
        }
    }

    @Override
    public Optional<Cliente> buscarPorEmail(String email) {
        String sql = "SELECT id_cliente, nombre, telefono, email FROM clientes WHERE email = ?";

        try (var connection = getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DaoException("Error al buscar el cliente por email", exception);
        }
    }

    private Cliente mapRow(ResultSet resultSet) throws SQLException {
        return new Cliente(
                resultSet.getInt("id_cliente"),
                resultSet.getString("nombre"),
                resultSet.getString("telefono"),
                resultSet.getString("email")
        );
    }
}
