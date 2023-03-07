package com.example.carniceria.controller;

import com.example.carniceria.Dto.*;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
//@CrossOrigin("http://localhost:4200")
@RequestMapping("/detalle")
public class DetalleController {
    @Autowired
    IDetalleService detalleService;
    @Autowired
    ICompraService compraService;
    @Autowired
    IProductoService productoService;
    List<Detalle> detalleList= new ArrayList<>();
    @GetMapping("/todos")
    public ResponseEntity<Object> getDetalles(){
        List<Detalle> detalle = detalleService.findAllDetalle();
        log.info("detalle: "+detalle);
        if (!detalle.isEmpty()) {
            return ResponseEntity.ok(detalle);
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
    }
    @GetMapping("/compra/fecha")
    public ResponseEntity<Object> getDetallesCompraByFecha() throws ParseException {
        //formato de fecha recibida por el objeto Date
        SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        //Recibiendo fecha del objeto Date
        Date date = format.parse(new Date().toString());
        //Convertir fecha del objeto Date al formato deseado
        String formatFecha = new SimpleDateFormat("yyyy-MM-dd").format(date);
        //formato de fecha recibida de la conversion anterior
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        //Convertir la fecha recibida en el formate deseado
        Date finalDate = format2.parse(formatFecha);
        List<Detalle> detalle = detalleService.findDetalleByCompraFecha(finalDate);
        log.info("detalle: "+detalle+"date: "+finalDate);
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
    public ResponseEntity<Object> createDetalle(@RequestBody DetalleRequest compraRequest){
        Detalle detalleCalcular = new Detalle();
        Detalle detalle = new Detalle();
        int ultimoId = 0;
        if (compraRequest!=null) {
            //**** guardarCompras ***
            double precio, totalCompra = 0.00;
            log.info("Insertando compra....");
            List<Detalle> detalles = detalleService.findAllDetalle();
            for (Detalle detalle1: detalles) {
                ultimoId = detalle1.getId_detalle();
            }
            log.info("Obteniendo objeto de peticion.... "+compraRequest);
            for (ProductoDto productoDto: compraRequest.getProductos()) {
                detalle.setCompra(compraRequest.getCompra());
                Optional<Producto> productoOptional = productoService.findProductoById(productoDto.getId_producto());
                detalle.setCantidad(productoDto.getCantidad());
                detalle.setPrecio(productoOptional.get().getPrecio());
                detalle.setProducto(productoOptional.get());
                totalCompra = detalleCalcular.calcularCompra(detalle);
                log.info("total de la compra... "+redondearDecimales(totalCompra,2));
                detalle.setTotal(redondearDecimales(totalCompra,2).setScale(2, RoundingMode.HALF_UP));
                //detalleTemporal.add(detalle);
                if (detalle.getId_detalle()==null){
                    log.info("Entra a IF");
                    detalle.setId_detalle(ultimoId+1);
                    detalleService.saveDetalle(detalle);
                }else {
                    detalle.setId_detalle(detalle.getId_detalle()+1);
                    detalleService.saveDetalle(detalle);
                }
            }
            log.info("Insertando compra... OK");
            return Utilidades.generarResponse(HttpStatus.ACCEPTED, "Compra realizada con exitó");
        }else{
            return Utilidades.generarResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Compra no realizada, intente mas tárde.");
        }
    }
    @GetMapping("/calcular/{total}/{recibido}")
    public ResponseEntity calcularCambio(@PathVariable("total") double total,@PathVariable("recibido") double cantidadRecibida){
        if (total!= 0 && cantidadRecibida != 0) {
            double cambio = cantidadRecibida - total;
            return  ResponseEntity.ok(cambio);
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudo calcular la devolucion, intente más tarde");
    }
    public BigDecimal redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return BigDecimal.valueOf(resultado).setScale(2, RoundingMode.HALF_UP);
    }
}
