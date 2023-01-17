package com.example.carniceria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_detalle;
    @ManyToOne
    @JoinColumn(name = "id_compra")
    Compra compra;
    @ManyToOne
    @JoinColumn(name = "id_producto")
    Producto producto;
    private int cantidad;
    private double precio;
    @Override
    public String toString() {
        return "Detalle{" +
                "id_detalle=" + id_detalle +
                ", compra=" + compra +
                ", producto=" + producto +
                '}';
    }
}
