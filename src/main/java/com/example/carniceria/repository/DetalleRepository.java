package com.example.carniceria.repository;

import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Detalle;
import com.example.carniceria.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleRepository extends JpaRepository<Detalle, Integer> {
    List<Detalle> findDetalleByCompra(Compra compra);
    List<Detalle> findDetalleByProducto(Producto producto);
}
