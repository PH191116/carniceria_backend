package com.example.carniceria.Dto;

import com.example.carniceria.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProductos {
    private List<Producto> productos = new ArrayList<>();
    BigDecimal total;

    @Override
    public String toString() {
        return "DetalleProductos{" +
                "productos=" + productos +
                ", total=" + total +
                '}';
    }
}
