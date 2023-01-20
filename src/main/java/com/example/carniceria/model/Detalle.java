package com.example.carniceria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

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
    private double total;

    public double calcularCompra(List<Detalle> detalleList){
        double total=0.00, precio = 0.00;
        int cantidad;
        for (Detalle detalle:detalleList) {
            cantidad = detalle.getCantidad();
            precio = detalle.getPrecio();
            total += (cantidad*precio);
        }
        return total;
    }
    @Override
    public String toString() {
        return "Detalle{" +
                "id_detalle=" + id_detalle +
                ", compra=" + compra +
                ", producto=" + producto +
                '}';
    }
}
