package com.lovelyshades.service;

import com.lovelyshades.dao.cliente.ClienteDao;
import com.lovelyshades.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClienteServiceImpl implements ClienteService {

    
    private final ClienteDao clienteDao;

    public ClienteServiceImpl(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteDao.guardar(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteDao.buscarPorId(id);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteDao.listarTodos();
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        return clienteDao.actualizar(cliente);
    }

    @Override
    public boolean eliminar(Integer id) {
        return clienteDao.eliminar(id);
    }

    @Override
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteDao.buscarPorEmail(email);
    }
}