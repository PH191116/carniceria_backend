package com.example.carniceria.service;

import com.example.carniceria.model.Categoria;
import com.example.carniceria.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements ICategoriaService{
    @Autowired
    CategoriaRepository categoriaRepository;
    @Override
    public Optional<Categoria> findCategoriaById(Integer id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent())
            return categoria;
        return Optional.empty();
    }

    @Override
    public List<Categoria> findAllCategorias() {
        if(!categoriaRepository.findAll().isEmpty())
            categoriaRepository.findAll();
        return Arrays.asList();
    }

    @Override
    public void saveCategoria(Categoria categoria) {
        categoriaRepository.save(categoria);
    }
}
