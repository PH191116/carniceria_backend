package com.example.carniceria.service.impl;

import com.example.carniceria.model.Compra;
import com.example.carniceria.repository.CompraRepository;
import com.example.carniceria.service.ICompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraServiceImpl implements ICompraService {
    @Autowired
    CompraRepository compraRepository;
    @Override
    public void insertCompra(Compra compra) {
     compraRepository.save(compra);
    }

    @Override
    public List<Compra> findAllCompras() {
        return compraRepository.findAll();
    }

    @Override
    public Optional<Compra> findCompraById(String id) {
        return compraRepository.findById(id);
    }

    @Override
    public boolean existeCompraById(String id) {
        boolean exists = compraRepository.existsById(id);
        if (exists)
            return exists;
        return false;
    }

    @Override
    public void deleteCompraById(String id) {
        compraRepository.deleteById(id);
    }
}
