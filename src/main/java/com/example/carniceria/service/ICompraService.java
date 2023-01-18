package com.example.carniceria.service;

import com.example.carniceria.model.Compra;

import java.util.List;
import java.util.Optional;

public interface ICompraService {
void insertCompra(Compra compra);
List<Compra> findAllCompras();
Optional<Compra> findCompraById(String id);
boolean existeCompraById(String id);
void deleteCompraById(String id);
}
