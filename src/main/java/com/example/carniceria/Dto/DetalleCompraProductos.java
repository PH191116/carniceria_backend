package com.example.carniceria.Dto;

import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompraProductos {
    Compra compra;
    List<Producto> productos = new ArrayList<>();
    double total;

    @Override
    public String toString() {
        return "DetalleCompraProductos{" +
                ", compra=" + compra +
                ", productos=" + productos +
                ", total=" + total +
                '}';
    }
}
