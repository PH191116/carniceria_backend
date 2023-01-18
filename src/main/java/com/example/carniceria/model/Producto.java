package com.example.carniceria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    private String id_producto;
    private String nombre;
    private double precio;
    @OneToOne
    @JoinColumn(name="id_categoria")
    private Categoria id_categoria;

    @Override
    public String toString() {
        return "Producto{" +
                "id_producto='" + id_producto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoria=" + id_categoria +
                '}';
    }
}
