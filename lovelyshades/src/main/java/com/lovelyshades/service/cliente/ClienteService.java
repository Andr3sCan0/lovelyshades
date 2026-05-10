package com.lovelyshades.service.cliente;

import com.lovelyshades.dao.base.BaseDao;
import com.lovelyshades.model.Cliente;
import com.lovelyshades.service.base.BaseService;

import java.util.Optional;

public interface ClienteService extends BaseService<Cliente, Integer> {

    Optional<Cliente> buscarPorEmail(String email);

    Cliente guardarJpa(Cliente cliente);
}
