package com.example.carniceria.controller;

import com.example.carniceria.Dto.DetalleCompraProductos;
import com.example.carniceria.Dto.DetalleCompras;
import com.example.carniceria.Dto.DetalleList;
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
    List<Detalle> detalleList= new ArrayList<>();
    @GetMapping("")
    public ResponseEntity<Object> getDetalle(){
        List<Detalle> detalle = detalleService.findAllDetalle();
        DetalleCompraProductos detalleCompraProducto = new DetalleCompraProductos();
        DetalleList detalleList = new DetalleList();
        List<Compra> compras = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        List<DetalleCompraProductos> detalleCompraProductos = new ArrayList<>();
        log.info("detalle: "+detalle);
        if (!detalle.isEmpty()) {
            for (Detalle detalle1 : detalle) {
                Optional<Compra> compra = compraService.findCompraById(detalle1.getCompra().getId_compra());
                detalleCompraProducto.setCompra(compra.get());
                productos.add(detalle1.getProducto());
                detalleCompraProducto.setProductos(productos);
                detalleCompraProducto.setTotal(detalle1.getTotal());
                detalleCompraProductos.add(detalleCompraProducto);
                detalleList.setDetalleList(detalleCompraProductos);
            }
            return ResponseEntity.ok(detalleList);
        }else
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
                    detalleProductos.setTotal(detalle.getTotal());
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
    public ResponseEntity<Object> createDetalle(@RequestBody List<Detalle> compras){
        Detalle detalleCalcular = new Detalle();
        List<Detalle> detalleTemporal = new ArrayList<>();
        if (!compras.isEmpty()) {
            //**** guardarCompras ***
            //recuperando el precio del producto para asignarlo al campo precio de la compra y poder calcular el total
            double precio;
            for (Detalle producto: compras) {
                precio = producto.getProducto().getPrecio();
                producto.setPrecio(precio);
                detalleTemporal.add(producto);
            }
           double totalCompra = detalleCalcular.calcularCompra(compras);
           log.info("total de la compra: "+totalCompra);
            detalleTemporal.forEach(compra->{
                compra.setTotal(totalCompra);
                detalleService.saveDetalle(compra);
            });
            return Utilidades.generarResponse(HttpStatus.ACCEPTED, "Compra realizada con exitó");
        }else{
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Compra no realizada, intente mas tárde.");
        }
    }
    @PostMapping("/calcular/{total}/{recibido}")
    public ResponseEntity calcularCambio(@PathVariable("total") double total,@PathVariable("recibido") double cantidadRecibida){
        if (total!= 0 && cantidadRecibida != 0) {
            double cambio = cantidadRecibida - total;
            return  ResponseEntity.ok(cambio);
        }else
            return ResponseEntity.notFound().build();
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
