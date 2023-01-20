package com.example.carniceria;

import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Detalle;
import com.example.carniceria.model.Producto;
import com.example.carniceria.service.ICategoriaService;
import com.example.carniceria.service.ICompraService;
import com.example.carniceria.service.IDetalleService;
import com.example.carniceria.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CarniceriaApplication implements CommandLineRunner {
    @Autowired
    ICategoriaService categoriaService;
    @Autowired
    ICompraService  compraService;
    @Autowired
    IProductoService productoService;
    @Autowired
    IDetalleService detalleService;

    public static void main(String[] args) {
        SpringApplication.run(CarniceriaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Detalle> detalleList = new ArrayList<>();
//        //Agregando el primer detalle
//        Optional<Producto> productoOp= productoService.findProductoById("PRD6301");
//        Producto producto1 = productoOp.get();
//        Detalle detalle1 = new Detalle();
//        detalle1.setProducto(producto1);
//        Optional<Compra> compraOp= compraService.findCompraById("CM15009");
//        Compra compra1 = compraOp.get();
//        detalle1.setCompra(compra1);
//        detalle1.setCantidad(2);
//        detalle1.setPrecio(producto1.getPrecio());
//        Detalle detalle2 = new Detalle();
//        Optional<Producto> productoOpDos= productoService.findProductoById("PRD6302");
//        Producto producto2 = productoOpDos.get();
//        detalle2.setProducto(producto2);
//        detalle2.setCompra(compra1);
//        detalle2.setCantidad(3);
//        detalle2.setPrecio(producto2.getPrecio());
//        detalleList.add(detalle1);
//        detalleList.add(detalle2);
//        double total = detalle1.calcularCompra(detalleList);
//        detalle2.setTotal(total);
//        detalleService.saveDetalle(detalle1);
//        detalleService.saveDetalle(detalle2);
//        System.out.println(total);
    }
}
