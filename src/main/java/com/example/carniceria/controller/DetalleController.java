package com.example.carniceria.controller;

import com.example.carniceria.Dto.DetalleCompraProductos;
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
    List<Detalle> detalleList= new ArrayList<>();
    @GetMapping("")
    public ResponseEntity<Object> getDetalle(){
//        List<Detalle> detalle = detalleService.findAllDetalle();
        List<DetalleCompraProductos> detalleCompraProductos = new ArrayList<>();
        List<Compra> compras = compraService.findAllCompras();
        log.info("detalle: "+compras);
        if (!compras.isEmpty()) {
            for (Compra compra: compras) {
                List<Producto> productos = new ArrayList<>();
                DetalleCompraProductos detalleCompraProducto = new DetalleCompraProductos();
                List<Detalle> productosList = detalleService.findDetalleByCompra(compra);
                log.info("findDetalleByCompra: "+productosList);
                if (!productosList.isEmpty()){
                    for (Detalle products: productosList) {
                        detalleCompraProducto.setCompra(compra);
                        log.info("Compra con productos insert: "+detalleCompraProducto.getCompra());
                        productos.add(products.getProducto());
                        detalleCompraProducto.setTotal(products.getTotal());
                    }
                    log.info("productos agregados: "+productos);
                    detalleCompraProducto.setProductos(productos);
                    detalleCompraProductos.add(detalleCompraProducto);
                }
            }
            log.info("Lista detalleCompraProductos: "+detalleCompraProductos);
            return ResponseEntity.ok(detalleCompraProductos);
            }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
    }
    @GetMapping("/todos")
    public ResponseEntity<Object> getDetalles(){
        List<Detalle> detalle = detalleService.findAllDetalle();
        log.info("detalle: "+detalle);
        if (!detalle.isEmpty()) {
            return ResponseEntity.ok(detalle);
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
    }
    @GetMapping("/{id}")
    public ResponseEntity getDetalleById(@PathVariable("id") Integer id){
        Optional<Detalle> detalle = detalleService.findDetalleById(id);
        if (detalle.isPresent())
            return ResponseEntity.ok(detalle.get());
        else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
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
                return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
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
                return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
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
            return Utilidades.generarResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Compra no realizada, intente mas tárde.");
        }
    }
    @PostMapping("/calcular/{total}/{recibido}")
    public ResponseEntity calcularCambio(@PathVariable("total") double total,@PathVariable("recibido") double cantidadRecibida){
        if (total!= 0 && cantidadRecibida != 0) {
            double cambio = cantidadRecibida - total;
            return  ResponseEntity.ok(cambio);
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudo calcular la devolucion, intente más tarde");
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
