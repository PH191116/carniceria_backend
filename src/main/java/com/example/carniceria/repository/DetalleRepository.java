package com.example.carniceria.repository;

import com.example.carniceria.model.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleRepository extends JpaRepository<Detalle, Integer> {
}
