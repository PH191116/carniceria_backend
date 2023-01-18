package com.example.carniceria.service.impl;

import com.example.carniceria.controller.ProductoController;
import com.example.carniceria.model.Producto;
import com.example.carniceria.repository.ProductoRepository;
import com.example.carniceria.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {
    @Autowired
    ProductoRepository productoRepository;
    @Override
    public Optional<Producto> findProductoById(String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent())
            return producto;
        return Optional.empty();
    }

    @Override
    public List<Producto> findAllProductos() {
        if (!productoRepository.findAll().isEmpty())
           return productoRepository.findAll();
        return Arrays.asList();
    }

    @Override
    public void saveProducto(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void deleteProductoById(String id) {
        productoRepository.deleteById(id);
    }

    @Override
    public boolean existeProductoById(String id) {
        boolean exists = productoRepository.existsById(id);
        if (exists)
            return exists;
        return false;
    }

}
