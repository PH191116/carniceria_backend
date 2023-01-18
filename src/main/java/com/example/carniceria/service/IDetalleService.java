package com.example.carniceria.service;

import com.example.carniceria.model.Detalle;

import java.util.List;
import java.util.Optional;

public interface IDetalleService {
    void saveDetalle(Detalle detalle);
    List<Detalle> findAllDetalle();
    Optional<Detalle> findDetalleById(Integer id);
    List<Detalle> findDetalleByCompra(Integer id);
}
