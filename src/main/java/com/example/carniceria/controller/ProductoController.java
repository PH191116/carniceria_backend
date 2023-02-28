package com.example.carniceria.controller;

import com.example.carniceria.model.Categoria;
import com.example.carniceria.model.Producto;
import com.example.carniceria.service.IProductoService;
import com.example.carniceria.util.Utilidades;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    IProductoService productoService;
    @GetMapping("")
    public ResponseEntity getProductos(){
        if (!productoService.findAllProductos().isEmpty()) {
            List<Producto> productos = productoService.findAllProductos();
            return ResponseEntity.ok(productos);
        }
        else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");
    }
    @GetMapping("/{id}")
    public ResponseEntity getProductoById(@PathVariable("id") String id){
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent())
            return ResponseEntity.ok(producto.get());
        else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "No se pudieron obtener datos, intente más tarde");

    }
    @PostMapping("")
    public ResponseEntity<Object> createProducto(@RequestBody Producto producto){
        String codigo="";
        if (producto!=null) {
            //crear un id aleatorio, si id existe se genera uno nuevo
                do {
                    int aleatorio = (int) (Math.random()*(9999-1)) + 1;
                    log.info("aleatorio: "+aleatorio);
                    if (aleatorio>=1 && aleatorio <100){
                        codigo= "PRD00"+aleatorio;
                    } else if (aleatorio>=100 && aleatorio <1000) {
                        codigo= "PRD0"+aleatorio;
                    }else{
                        codigo= "PRD"+aleatorio;
                    }
                    log.info("codigo: "+codigo);
                    log.info("Existe codigo prod: "+productoService.existeProductoById(codigo));
                }while (productoService.existeProductoById(codigo));
                producto.setId_producto(codigo);
                producto.setNombre(producto.getNombre().toUpperCase());
                productoService.saveProducto(producto);
                return Utilidades.generarResponse(HttpStatus.ACCEPTED, "Producto creado con exitó");
        }else{
            return Utilidades.generarResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Producto no creado, intente mas tárde.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProducto(@PathVariable("id") String id, @RequestBody Producto producto){
        if (producto != null){
            Optional<Producto> producto1 = productoService.findProductoById(id);
            if (producto1.isPresent()) {
                Producto producto2 = producto1.get();
                producto2.setId_producto(producto.getId_producto());
                producto2.setNombre(producto.getNombre());
                producto2.setId_categoria(producto.getId_categoria());
                producto2.setPrecio(producto.getPrecio());
                productoService.saveProducto(producto2);
                return Utilidades.generarResponse(HttpStatus.OK, "Producto actualizado con exito");
            }else
                return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Producto no existe");
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Producto no se actualizó, intente más tarde");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProducto(@PathVariable("id") String id){
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent()){
            productoService.deleteProductoById(id);
            return Utilidades.generarResponse(HttpStatus.OK, "Producto eliminado con éxito");
        }else
            return Utilidades.generarResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Producto no eliminado, intente más tarde");
    }
}
