package com.example.carniceria.service;

import com.example.carniceria.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService{
    @Autowired
    IProductoService productoService;
    @Override
    public Optional<Producto> findProductoById(String id) {
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent())
            return producto;
        return Optional.empty();
    }

    @Override
    public List<Producto> findAllProductos() {
        if (!productoService.findAllProductos().isEmpty())
           return productoService.findAllProductos();
        return Arrays.asList();
    }

    @Override
    public void saveProducto(Producto producto) {
        productoService.saveProducto(producto);
    }

    @Override
    public void deleteProductoById(String id) {
        productoService.deleteProductoById(id);
    }

    @Override
    public boolean existeProductoById(String id) {
        return productoService.existeProductoById(id);
    }
}
