package com.example.carniceria.Dto;

import com.example.carniceria.model.Compra;
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
public class DetalleCompras {
    private List<Compra> compras = new ArrayList<>();

    @Override
    public String toString() {
        return "DetalleCompras{" +
                "compras=" + compras +
                '}';
    }
}
