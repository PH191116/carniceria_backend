package com.example.carniceria.Dto;

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
public class DetalleList {
    List<DetalleCompraProductos> detalleList = new ArrayList<>();

    @Override
    public String toString() {
        return "DetalleList{" +
                "detalleList=" + detalleList +
                '}';
    }
}
