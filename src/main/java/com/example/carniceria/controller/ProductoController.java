package com.example.carniceria.controller;

import com.example.carniceria.model.Categoria;
import com.example.carniceria.model.Producto;
import com.example.carniceria.service.IProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    IProductoService productoService;
    @GetMapping("")
    public List<Producto> getProductos(){
        if (!productoService.findAllProductos().isEmpty())
            return productoService.findAllProductos();
        else
            return Arrays.asList();
    }
    @GetMapping("/{id}")
    public Producto getProductosById(@PathVariable("id") String id){
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent())
            return producto.get();
        else
            return null;
    }
    @PostMapping("")
    public ResponseEntity<Object> createProducto(@RequestBody Producto producto){
        return null;
    }
}
