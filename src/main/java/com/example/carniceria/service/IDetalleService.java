package com.example.carniceria.service;

import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Detalle;
import com.example.carniceria.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IDetalleService {
    void saveDetalle(Detalle detalle);
    List<Detalle> findAllDetalle();
    Optional<Detalle> findDetalleById(Integer id);
    List<Detalle> findDetalleByCompra(Compra compra);
    List<Detalle> findDetalleByProducto(Producto producto);
}
