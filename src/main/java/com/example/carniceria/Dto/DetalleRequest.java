package com.example.carniceria.Dto;

import com.example.carniceria.model.Compra;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleRequest {
   private Compra compra;
    private List<ProductoDto> productos;
    @Override
    public String toString() {
        return "DetalleCompraProductosRequest{" +
                "compra=" + compra +
                ", id_producto='" + productos + '\''
                +'}';
    }
}
