package com.example.carniceria.service.impl;

import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Detalle;
import com.example.carniceria.model.Producto;
import com.example.carniceria.repository.DetalleRepository;
import com.example.carniceria.service.IDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DetallServiceImpl implements IDetalleService {
    @Autowired
    DetalleRepository detalleRepository;
    @Override
    public void saveDetalle(Detalle detalle) {
    detalleRepository.save(detalle);
    }

    @Override
    public List<Detalle> findAllDetalle() {
        return detalleRepository.findAll();
    }

    @Override
    public Optional<Detalle> findDetalleById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Detalle> findDetalleByCompra(Compra compra) {
        return detalleRepository.findDetalleByCompra(compra);
    }

    @Override
    public List<Detalle> findDetalleByProducto(Producto producto) {
        return detalleRepository.findDetalleByProducto(producto);
    }

    @Override
    public List<Detalle> findDetalleByCompraFecha(Date fecha) {
        return detalleRepository.findDetalleByCompra_Fecha(fecha);
    }

}
