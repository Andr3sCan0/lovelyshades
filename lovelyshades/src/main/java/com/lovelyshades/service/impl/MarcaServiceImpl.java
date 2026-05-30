package com.lovelyshades.service.impl;

import com.lovelyshades.entity.Marca;
import com.lovelyshades.repository.MarcaRepository;
import com.lovelyshades.service.MarcaService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaServiceImpl(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public Marca crear(Marca marca) {
        return marcaRepository.save(marca);
    }

    @Override
    public List<Marca> listar() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca obtenerPorId(Integer id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public Marca actualizar(Integer id, Marca marca) {
        Marca marcaExistente = marcaRepository.findById(id).orElse(null);
        if (marcaExistente != null) {
            marcaExistente.setNombreMarca(marca.getNombreMarca());
            marcaExistente.setEstado(marca.getEstado());
            return marcaRepository.save(marcaExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        marcaRepository.deleteById(id);
    }
}
