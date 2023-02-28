package com.example.carniceria.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {
    private String id_producto;
    private String nombre;
    private double cantidad;

    @Override
    public String toString() {
        return "ProductoDto{" +
                "id_producto='" + id_producto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
