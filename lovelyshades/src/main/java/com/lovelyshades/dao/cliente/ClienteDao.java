package com.lovelyshades.dao.cliente;

import com.lovelyshades.dao.base.BaseDao;
import com.lovelyshades.model.Cliente;

import java.util.Optional;

public interface ClienteDao extends BaseDao<Cliente, Integer> {

    Optional<Cliente> buscarPorEmail(String email);
}
