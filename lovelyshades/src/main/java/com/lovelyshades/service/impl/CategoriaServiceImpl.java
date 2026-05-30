package com.lovelyshades.service.impl;

import com.lovelyshades.entity.Categoria;
import com.lovelyshades.repository.CategoriaRepository;
import com.lovelyshades.service.CategoriaService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria crear(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria obtenerPorId(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Categoria actualizar(Integer id, Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.findById(id).orElse(null);
        if (categoriaExistente != null) {
            categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
            categoriaExistente.setDescripcion(categoria.getDescripcion());
            categoriaExistente.setEstado(categoria.getEstado());
            return categoriaRepository.save(categoriaExistente);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
