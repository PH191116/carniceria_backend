package com.example.carniceria.controller;

import com.example.carniceria.Dto.DetalleCompras;
import com.example.carniceria.Dto.DetalleProductos;
import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Detalle;
import com.example.carniceria.model.Producto;
import com.example.carniceria.service.ICompraService;
import com.example.carniceria.service.IDetalleService;
import com.example.carniceria.service.IProductoService;
import com.example.carniceria.util.Utilidades;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/detalle")
public class DetalleController {
    @Autowired
    IDetalleService detalleService;
    @Autowired
    ICompraService compraService;
    @Autowired
    IProductoService productoService;
    @GetMapping("")
    public ResponseEntity<Object> getDetalle(){
        List<Detalle> detalle = detalleService.findAllDetalle();
        log.info("detalle: "+detalle);
        if (!detalle.isEmpty())
            return ResponseEntity.ok(detalle);
        else
            return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity getDetalleById(@PathVariable("id") Integer id){
        Optional<Detalle> detalle = detalleService.findDetalleById(id);
        if (detalle.isPresent())
            return ResponseEntity.ok(detalle.get());
        else
            return ResponseEntity.notFound().build();
    }
    //Obtener los productos de una compra en especifico
    @GetMapping("compra/{id}/productos")
    public ResponseEntity getProductosByCompra(@PathVariable("id") String id){
        Optional<Compra> compra = compraService.findCompraById(id);
        if (compra.isPresent()) {
            List<Detalle> productos = detalleService.findDetalleByCompra(compra.get());

            if (!productos.isEmpty()) {
                List<Producto> productos1 = new ArrayList<>();
                DetalleProductos detalleProductos = new DetalleProductos();
                for (Detalle detalle: productos) {
                    productos1.add(detalle.getProducto());
                }
                detalleProductos.setProductos(productos1);
                return ResponseEntity.ok(detalleProductos);
            }else
                return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.notFound().build();
    }
    //Obtener los compras de un producto en especifico
    @GetMapping("producto/{id}/compras")
    public ResponseEntity getComprasByProducto(@PathVariable("id") String id){
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent()) {
            List<Detalle> compras = detalleService.findDetalleByProducto(producto.get());

            if (!compras.isEmpty()) {
                List<Compra> compras1 = new ArrayList<>();
                DetalleCompras detalleCompras = new DetalleCompras();
                for (Detalle detalle: compras) {
                    compras1.add(detalle.getCompra());
                }
                detalleCompras.setCompras(compras1);
                return ResponseEntity.ok(detalleCompras);
            }else
                return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("")
    public ResponseEntity<Object> createDetalle(@RequestBody Detalle detalle){
        if (detalle!=null) {
            //guardarDetalle
            detalleService.saveDetalle(detalle);
            return Utilidades.generarResponse(HttpStatus.ACCEPTED, "Compra creada con exitó");
        }else{
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Compra no creada, intente mas tárde.");
        }
    }
    /*@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompra(@PathVariable("id") String id){
        Optional<Compra> compra = detalleService.findCompraById(id);
        if (compra.isPresent()){
            detalleService.deleteCompraById(id);
            return Utilidades.generarResponse(HttpStatus.OK, "Compra eliminada con éxito");
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Compra no eliminada, intente más tarde");
    }*/
}
