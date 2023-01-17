package com.example.carniceria.service;

import com.example.carniceria.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    Optional<Producto> findProductoById(String id);
    List<Producto> findAllProductos();
    void saveProducto(Producto producto);
    void deleteProductoById(String id);
    boolean existeProductoById(String id);
}
