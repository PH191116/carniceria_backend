package com.example.carniceria.service;

import com.example.carniceria.model.Categoria;
import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Detalle;
import com.example.carniceria.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    Optional<Categoria> findCategoriaById(Integer id);
    List<Categoria> findAllCategorias();
    void saveCategoria(Categoria categoria);
}
