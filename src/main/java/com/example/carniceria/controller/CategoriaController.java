package com.example.carniceria.controller;

import com.example.carniceria.model.Categoria;
import com.example.carniceria.model.Producto;
import com.example.carniceria.repository.CategoriaRepository;
import com.example.carniceria.service.ICategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    ICategoriaService categoriaService;
    @GetMapping("")
    public List<Categoria> getCategorias(){
            if (!categoriaService.findAllCategorias().isEmpty())
                return categoriaService.findAllCategorias();
            else
                return Arrays.asList();
    }
    @GetMapping("/{id}")
    public Categoria getCategoriasById(@PathVariable("id") Integer id){
        Optional<Categoria> categoria = categoriaService.findCategoriaById(id);
        if (categoria.isPresent())
            return categoria.get();
        else
            return null;
    }
}
