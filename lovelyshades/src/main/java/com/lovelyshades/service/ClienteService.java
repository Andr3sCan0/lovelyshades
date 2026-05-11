package com.lovelyshades.service;

import com.lovelyshades.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Cliente guardar(Cliente cliente);

    Optional<Cliente> buscarPorId(Integer id);

    List<Cliente> listarTodos();

    boolean actualizar(Cliente cliente);

    boolean eliminar(Integer id);

    Optional<Cliente> buscarPorEmail(String email);
}