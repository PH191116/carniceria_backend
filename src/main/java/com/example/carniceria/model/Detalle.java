package com.example.carniceria.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "detalle_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detalle {
    @Id
    private Integer id_detalle;
    @ManyToOne
    @JoinColumn(name = "id_compra")
    Compra compra;
    @ManyToOne
    @JoinColumn(name = "id_producto")
    Producto producto;
    private double cantidad;
    private double precio;
    private BigDecimal total;

    public double calcularCompra(Detalle detalle){
        double total=0.00, precio = 0.00, cantidad;
        int  cantidadChorizoTusa = 7, cantidadChorizoEspecial =3;

            cantidad = detalle.getCantidad();
            precio = detalle.getPrecio();
           String producto = detalle.getProducto().getNombre();
            if (producto.equals("CHORIZOS DE TUSA") || producto.equals("CHORIZO DE TUSA")){
                double chorizoTusa = (cantidad/cantidadChorizoTusa)*precio;
                total = chorizoTusa;
                return total;
            } else if (producto.equals("CHORIZOS ESPECIAL") || producto.equals("CHORIZO ESPECIAL")) {
                double chorizoEspecial = (cantidad/cantidadChorizoEspecial)*precio;
                total = chorizoEspecial;
                return total;
            }
            total = (cantidad*precio);
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
