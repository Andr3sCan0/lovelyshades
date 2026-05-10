package com.lovelyshades.service.cliente;

import com.lovelyshades.dao.cliente.ClienteDao;
import com.lovelyshades.dao.cliente.ClienteRepository;
import com.lovelyshades.dao.producto.ProductoDao;
import com.lovelyshades.model.Cliente;
import com.lovelyshades.utils.ClienteNoEncontradoException;
import com.lovelyshades.utils.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private ClienteRepository clienteRepositoryRepository;

    @Override
    public Optional<Cliente> buscarPorEmail(String email) {

        try {

            return clienteRepositoryRepository.findByEmail(email);

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error consultando cliente por email", e);
        }
    }

    @Override
    public Cliente guardarJpa(Cliente cliente) {

        try {

            return clienteRepositoryRepository.save(cliente);

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error guardando cliente en SQL Server", e);
        }
    }

    @Override
    public Cliente guardar(Cliente cliente) {

        try {

            clienteDao.guardar(cliente);

            return cliente;

        } catch (Exception e) {

            throw new DatabaseException(
                    "Error guardando cliente", e);
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(Integer id) {

        try {

            Optional<Cliente> cliente =
                    clienteDao.buscarPorId(id);

            if (cliente.isEmpty()) {

                throw new ClienteNoEncontradoException(
                        "Cliente no encontrado con id: " + id);
            }

            return cliente;

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error consultando cliente", e);
        }
    }

    @Override
    public List<Cliente> listarTodos() {

        try {

            return clienteDao.listarTodos();

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error listando clientes", e);
        }
    }

    @Override
    public boolean actualizar(Cliente cliente) {

        try {

            return clienteDao.actualizar(cliente);

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error actualizando cliente", e);
        }
    }

    @Override
    public boolean eliminar(Integer id) {

        try {

            return clienteDao.eliminar(id);

        } catch (DataAccessException e) {

            throw new DatabaseException(
                    "Error eliminando cliente", e);
        }
    }
}
