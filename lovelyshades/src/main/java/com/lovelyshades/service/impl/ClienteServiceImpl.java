package com.lovelyshades.service.impl;

import com.lovelyshades.entity.Cliente;
import com.lovelyshades.repository.ClienteRepository;
import com.lovelyshades.service.ClienteService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends AbstractCrudService<Cliente> implements ClienteService {

    public ClienteServiceImpl(ClienteRepository repository) {
        super(repository, "Cliente");
    }

    @Override
    public Cliente actualizar(Integer id, Cliente source) {
        Cliente target = obtenerPorId(id);

        target.setNombreCompleto(source.getNombreCompleto());
        target.setTipoIdentificacion(source.getTipoIdentificacion());
        target.setNumeroIdentificacion(source.getNumeroIdentificacion());
        target.setTelefono(source.getTelefono());
        target.setEmail(source.getEmail());
        target.setDireccion(source.getDireccion());
        target.setFechaRegistro(source.getFechaRegistro());
        target.setEstado(source.getEstado());

        return repository.save(target);
    }
}